package ru.santaev.refillpoints.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import ru.santaev.refillpoints.domain.dto.LocationDto
import ru.santaev.refillpoints.domain.dto.RefillPointDto
import ru.santaev.refillpoints.domain.usecase.GetRefillPointsUsecase
import ru.santaev.refillpoints.log.ILoggable
import ru.santaev.refillpoints.utils.distanceBetween
import ru.santaev.refillpoints.view.IRefillPointsMapView
import java.util.concurrent.TimeUnit

class RefillPointsMapFragmentPresenter(
    private val parentPresenter: RefillPointsMapPresenter,
    private val getRefillPointsUsecase: GetRefillPointsUsecase
) : BasePresenter<IRefillPointsMapView>(null), ILoggable {

    private val cameraMoveObject: Subject<MoveCameraEvent> = PublishSubject.create()
    private var refillPoints: List<RefillPointViewModel>? = null

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
        loadRefillPoints(
            lat = event.location.lat,
            lng = event.location.lng,
            radius = radius.toInt()
        )
    }

    private fun loadRefillPoints(lat: Double, lng: Double, radius: Int) {
        getRefillPointsUsecase
            .execute(
                param = GetRefillPointsUsecase.Param(
                    lat = lat,
                    lng = lng,
                    radius = radius
                )
            )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = ::onRefillPointsLoaded,
                onError = { log("Error while loading points: $it") }
            )
            .also { registerDisposable(it) }
    }

    private fun onRefillPointsLoaded(list: List<RefillPointDto>) {
        parentPresenter.onLoadedRefillPoints(list)
        refillPoints = list
            .map { it.toViewModel() }
            .also { view?.showRefillPoints(it) }
    }

    data class Location(
        val lat: Double,
        val lng: Double
    )

    class RefillPointViewModel(
        val id: Long,
        val partnerName: String,
        val location: LocationDto,
        val workHours: String,
        val phones: String,
        val addressInfo: String,
        val fullAddress: String,
        val isViewed: Boolean
    )

    private data class MoveCameraEvent(
        val location: Location,
        val leftTopPoint: Location
    )

    private fun RefillPointDto.toViewModel(): RefillPointViewModel {
        return RefillPointViewModel(
            id = this.id,
            partnerName = this.partnerName,
            location = this.location,
            workHours = this.workHours,
            phones = this.phones,
            addressInfo = this.addressInfo,
            fullAddress = this.fullAddress,
            isViewed = this.isViewed
        )
    }

    companion object {

        private const val cameraMoveEventDebounceTimeoutSeconds = 1
    }
}