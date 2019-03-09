package ru.santaev.refillpoints.presenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import ru.santaev.refillpoints.domain.dto.LocationDto
import ru.santaev.refillpoints.domain.dto.RefillPointDto
import ru.santaev.refillpoints.domain.usecase.GetRefillPointUsecase
import ru.santaev.refillpoints.domain.usecase.GetRefillPointUsecase.Param
import ru.santaev.refillpoints.domain.usecase.MarkAsViewedRefillPointUsecase
import ru.santaev.refillpoints.log.ILoggable
import ru.santaev.refillpoints.view.IRefillPointDetailsView

class RefillPointDetailsPresenter(
    private val getRefillPointUsecase: GetRefillPointUsecase,
    private val markAsViewedRefillPointUsecase: MarkAsViewedRefillPointUsecase
) : BasePresenter<IRefillPointDetailsView>(null), ILoggable {

    private var refillPoint: RefillPointDto? = null

    fun setRefillPointId(id: Long) {
        loadRefillPoint(id)
    }

    private fun loadRefillPoint(refillPointId: Long) {
        getRefillPointUsecase
            .execute(Param(refillPointId))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = this::showRefillPoint
            )
            .also { registerDisposable(it) }
    }

    private fun showRefillPoint(refillPoint: RefillPointDto) {
        this.refillPoint = refillPoint
        view?.showRefillPoint(refillPoint.toViewModel())
        if (!refillPoint.isViewed) {
            markAsViewed(refillPoint.id)
        }
    }

    private fun markAsViewed(refillPointId: Long) {
        markAsViewedRefillPointUsecase
            .execute(MarkAsViewedRefillPointUsecase.Param(refillPointId))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy()
            .also { registerDisposable(it) }
    }

    class RefillPointDetailsViewModel(
        val partnerName: String,
        val location: LocationDto,
        val workHours: String,
        val phones: String,
        val addressInfo: String,
        val fullAddress: String,
        val isViewed: Boolean
    )

    private fun RefillPointDto.toViewModel(): RefillPointDetailsViewModel {
        return RefillPointDetailsViewModel(
            partnerName = this.partnerName,
            location = this.location,
            workHours = this.workHours,
            phones = this.phones,
            addressInfo = this.addressInfo,
            fullAddress = this.fullAddress,
            isViewed = this.isViewed
        )
    }
}