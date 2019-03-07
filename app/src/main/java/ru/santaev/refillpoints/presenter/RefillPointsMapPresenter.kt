package ru.santaev.refillpoints.presenter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import ru.santaev.refillpoints.domain.dto.LocationDto
import ru.santaev.refillpoints.domain.dto.RefillPointDto
import ru.santaev.refillpoints.domain.usecase.GetRefillPointsUsecase
import ru.santaev.refillpoints.view.IRefillPointsView
import javax.inject.Inject

class RefillPointsMapPresenter(
    private val getRefillPointsUsecase: GetRefillPointsUsecase
) : BasePresenter<IRefillPointsView>(null) {

    @Inject lateinit var presenter: RefillPointsMapFragmentPresenter
    private var refillPoints: List<RefillPointViewModel>? = null

    fun loadRefillPoints(lat: Double, lng: Double, radius: Int) {
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
                onError = { Log.d("RefillPoints", "error", it) }
            )
            .also { registerDisposable(it) }
    }

    private fun onRefillPointsLoaded(list: List<RefillPointDto>) {
        refillPoints = list
            .map { it.toViewModel() }
            .also { view?.setRefillPoints(it) }
    }

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
}