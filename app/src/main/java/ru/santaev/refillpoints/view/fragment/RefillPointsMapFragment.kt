package ru.santaev.refillpoints.view.fragment

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import ru.santaev.refillpoints.R
import ru.santaev.refillpoints.databinding.FragmentRefillPointsMapBinding
import ru.santaev.refillpoints.log.ILoggable
import ru.santaev.refillpoints.presenter.RefillPointsMapFragmentPresenter
import ru.santaev.refillpoints.presenter.RefillPointsMapFragmentPresenter.RefillPointViewModel
import ru.santaev.refillpoints.view.IRefillPointsMapView
import ru.santaev.refillpoints.view.activity.RefillPointsActivity
import javax.inject.Inject

class RefillPointsMapFragment : Fragment(), IRefillPointsMapView, ILoggable {

    @Inject lateinit var presenter: RefillPointsMapFragmentPresenter
    private lateinit var binding: FragmentRefillPointsMapBinding
    private lateinit var rxPermissions: RxPermissions
    private var googleMap: GoogleMap? = null
    private var permissionDisposable: Disposable? = null
    private var refillPoints: List<RefillPointViewModel>? = null

    override fun showRefillPoints(refillPoints: List<RefillPointViewModel>) {
        this.refillPoints = refillPoints
        if (googleMap != null) {
            addRefillPointsToMap(refillPoints)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_refill_points_map, container, false)
        initUI()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        rxPermissions = RxPermissions(this)
    }

    override fun onStart() {
        super.onStart()
        checkAndRequestPermissions()
    }

    override fun onDestroy() {
        super.onDestroy()
        permissionDisposable?.dispose()
    }

    private fun initUI() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { initGoogleMap(it) }
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
                addMarker(MarkerOptions().position(LatLng(point.location.lat, point.location.lng)))
            }
        }
    }

    private fun LatLng.toLocation(): RefillPointsMapFragmentPresenter.Location {
        return RefillPointsMapFragmentPresenter.Location(
            lat = this.latitude,
            lng = this.longitude
        )
    }

    companion object {

        fun create(): RefillPointsMapFragment = RefillPointsMapFragment()
    }
}