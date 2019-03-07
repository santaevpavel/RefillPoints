package ru.santaev.refillpoints.presenter

import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import ru.santaev.refillpoints.log.ILoggable
import ru.santaev.refillpoints.utils.distanceBetween
import ru.santaev.refillpoints.view.IRefillPointsMapView
import java.util.concurrent.TimeUnit

class RefillPointsMapFragmentPresenter(
    private val parentPresenter: RefillPointsMapPresenter
) : BasePresenter<IRefillPointsMapView>(null), ILoggable {

    private val cameraMoveObject: Subject<MoveCameraEvent> = PublishSubject.create()

    init {
        cameraMoveObject
            .distinctUntilChanged()
            .debounce(cameraMoveEventDebounceTimeoutSeconds.toLong(), TimeUnit.SECONDS)
            .subscribeBy(
                onNext = ::onMoveCameraProcessed
            )
            .also { registerDisposable(it) }
    }

    fun onMoveCamera(location: Location, leftTopPoint: Location) {
        val event = MoveCameraEvent(
            location = location,
            leftTopPoint = leftTopPoint
        )
        cameraMoveObject.onNext(event)
    }

    private fun onMoveCameraProcessed(event: MoveCameraEvent) {
        val radius = distanceBetween(
            lat1 = event.location.lat,
            lon1 = event.location.lng,
            lat2 = event.leftTopPoint.lat,
            lon2 = event.leftTopPoint.lng
        )
        parentPresenter.loadRefillPoints(
            lat = event.location.lat,
            lng = event.location.lng,
            radius = radius.toInt()
        )
    }

    data class Location(
        val lat: Double,
        val lng: Double
    )

    private data class MoveCameraEvent(
        val location: Location,
        val leftTopPoint: Location
    )

    companion object {

        private const val cameraMoveEventDebounceTimeoutSeconds = 1
    }
}