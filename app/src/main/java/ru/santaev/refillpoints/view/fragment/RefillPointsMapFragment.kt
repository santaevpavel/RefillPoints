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
import ru.santaev.refillpoints.presenter.RefillPointsMapPresenter

class RefillPointsMapFragment : Fragment() {

    private lateinit var binding: FragmentRefillPointsMapBinding
    private lateinit var rxPermissions: RxPermissions
    private var googleMap: GoogleMap? = null
    private var permissionDisposable: Disposable? = null
    private var refillPoints: List<RefillPointsMapPresenter.RefillPointViewModel>? = null

    fun setRefillPoints(refillPoints: List<RefillPointsMapPresenter.RefillPointViewModel>) {
        this.refillPoints = refillPoints
        if (googleMap != null) {
            addRefillPointsToMap(refillPoints)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, ru.santaev.refillpoints.R.layout.fragment_refill_points_map, container, false)
        initUI()
        return binding.root
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
        permissionDisposable?.dispose()
    }

    private fun initUI() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { initGoogleMap(it) }
    }

    private fun initGoogleMap(map: GoogleMap) {
        googleMap = map
        map.apply {
            uiSettings.isZoomControlsEnabled = true
            enableGoogleMapMyLocation()
            refillPoints?.let { addRefillPointsToMap(it) }
        }
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

    private fun addRefillPointsToMap(list: List<RefillPointsMapPresenter.RefillPointViewModel>) {
        googleMap?.apply {
            list.forEach { point ->
                addMarker(MarkerOptions().position(LatLng(point.location.lat, point.location.lng)))
            }
        }
    }

    companion object {

        fun create(): RefillPointsMapFragment = RefillPointsMapFragment()
    }
}