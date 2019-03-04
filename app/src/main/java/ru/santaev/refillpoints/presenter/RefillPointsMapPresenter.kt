package ru.santaev.refillpoints.presenter

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import ru.santaev.refillpoints.domain.dto.LocationDto
import ru.santaev.refillpoints.domain.usecase.GetRefillPointsUsecase
import ru.santaev.refillpoints.view.IRefillPointsView

class RefillPointsMapPresenter(
    private val getRefillPointsUsecase: GetRefillPointsUsecase
) : BasePresenter<IRefillPointsView>(null) {

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
                onNext = { list ->
                    Log.d("RefillPoints", list.joinToString())
                },
                onError = { Log.d("RefillPoints", "error", it) }
            )
            .also { registerDisposable(it) }
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