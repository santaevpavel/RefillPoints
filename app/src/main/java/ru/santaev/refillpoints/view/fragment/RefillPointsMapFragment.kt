package ru.santaev.refillpoints.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import ru.santaev.refillpoints.R
import ru.santaev.refillpoints.databinding.FragmentRefillPointsMapBinding
import ru.santaev.refillpoints.log.ILoggable
import ru.santaev.refillpoints.presenter.RefillPointsMapPresenter
import ru.santaev.refillpoints.presenter.RefillPointsMapPresenter.RefillPointViewModel
import ru.santaev.refillpoints.view.IRefillPointsMapView
import ru.santaev.refillpoints.view.activity.RefillPointDetailsActivity
import ru.santaev.refillpoints.view.activity.RefillPointsActivity
import javax.inject.Inject

class RefillPointsMapFragment : Fragment(), IRefillPointsMapView, ILoggable {

    @Inject
    lateinit var presenter: RefillPointsMapPresenter
    private lateinit var binding: FragmentRefillPointsMapBinding
    private lateinit var rxPermissions: RxPermissions
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private var googleMap: GoogleMap? = null
    private var permissionDisposable: Disposable? = null
    private var refillPoints: List<RefillPointViewModel>? = null

    override fun showRefillPoints(refillPoints: List<RefillPointViewModel>) {
        this.refillPoints = refillPoints
        if (googleMap != null) {
            addRefillPointsToMap(refillPoints)
        }
    }

    override fun showBottomSheet(point: RefillPointViewModel) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        binding.bottomSheet.point = point
    }

    override fun openRefillPointDetails(point: RefillPointViewModel) {
        val context = context ?: return
        startActivity(RefillPointDetailsActivity.createIntent(context, point.id))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_refill_points_map, container, false)
        initUI()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        initPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rxPermissions = RxPermissions(this)
    }

    override fun onStart() {
        super.onStart()
        checkAndRequestPermissions()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
        permissionDisposable?.dispose()
    }

    private fun initUI() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { initGoogleMap(it) }
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.root)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        binding.bottomSheet.btnDetails.setOnClickListener { onDetailClick() }
    }

    private fun initPresenter() {
        (activity as RefillPointsActivity)
            .component
            .inject(this)
        presenter.view = this
    }

    private fun initGoogleMap(map: GoogleMap) {
        googleMap = map
        map.apply {
            uiSettings.isZoomControlsEnabled = true
            enableGoogleMapMyLocation()
            refillPoints?.let { addRefillPointsToMap(it) }
            setOnCameraMoveListener(this@RefillPointsMapFragment::onCameraMove)
            goToCurrentLocation(animate = false)
            setOnMarkerClickListener(this@RefillPointsMapFragment::onMarkerClick)
        }
    }

    private fun onDetailClick() {
        presenter.onClickOpenDetail()
    }

    private fun onMarkerClick(marker: Marker): Boolean {
        val id = marker.tag as Long? ?: return false
        presenter.onClickRefillPointMarker(id)
        return true
    }

    @SuppressLint("MissingPermission")
    private fun goToCurrentLocation(animate: Boolean) {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager? ?: return
        if (!rxPermissions.isGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            return
        }
        try {
            val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) ?: return
            goToLocation(location, animate)
        } catch (e: SecurityException) {
        }
    }

    private fun goToLocation(location: Location, animate: Boolean) {
        val googleMap = googleMap ?: return
        val cameraParams = CameraPosition.fromLatLngZoom(
            LatLng(
                location.latitude,
                location.longitude
            ),
            defaultMapZoom
        )
        val cameraPosition = CameraUpdateFactory.newCameraPosition(cameraParams)
        if (animate) {
            googleMap.animateCamera(cameraPosition)
        } else {
            googleMap.moveCamera(cameraPosition)
        }
    }

    private fun onCameraMove() {
        val position = googleMap?.cameraPosition?.target ?: return
        val topLeft = googleMap?.projection?.visibleRegion?.farLeft ?: return
        presenter.onMoveCamera(
            location = position.toLocation(),
            leftTopPoint = topLeft.toLocation()
        )
    }

    private fun checkAndRequestPermissions() {
        rxPermissions
            .request(Manifest.permission.ACCESS_FINE_LOCATION)
            .subscribeBy(
                onNext = { isGranted ->
                    if (isGranted) {
                        googleMap?.enableGoogleMapMyLocation()
                        goToCurrentLocation(animate = true)
                    }
                }
            )
            .also { permissionDisposable = it }
    }

    private fun GoogleMap.enableGoogleMapMyLocation() {
        try {
            isMyLocationEnabled = true
        } catch (e: SecurityException) {
        }
    }

    private fun addRefillPointsToMap(list: List<RefillPointViewModel>) {
        googleMap?.apply {
            clear()
            list.forEach { point ->
                val marker = MarkerOptions().apply {
                    position(LatLng(point.location.lat, point.location.lng))
                }
                addMarker(marker).apply {
                    tag = point.id
                }
            }
        }
    }

    private fun LatLng.toLocation(): RefillPointsMapPresenter.Location {
        return RefillPointsMapPresenter.Location(
            lat = this.latitude,
            lng = this.longitude
        )
    }

    companion object {

        private const val defaultMapZoom = 15F

        fun create(): RefillPointsMapFragment = RefillPointsMapFragment()
    }
}