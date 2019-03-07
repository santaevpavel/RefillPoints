package ru.santaev.refillpoints.presenter

import ru.santaev.refillpoints.domain.dto.RefillPointDto
import ru.santaev.refillpoints.log.ILoggable
import ru.santaev.refillpoints.view.IRefillPointsListView

class RefillPointsListPresenter(
    private val parentPresenter: RefillPointsPresenter
) : BasePresenter<IRefillPointsListView>(null), ILoggable {

    private var refillPoints: List<RefillPointViewModel>? = null

    fun setRefillPoints(list: List<RefillPointDto>) {
        refillPoints = list
            .map { it.toViewModel() }
            .also { view?.showRefillPoints(it) }
    }

    class RefillPointViewModel(
        val id: Long,
        val partnerName: String,
        val addressInfo: String,
        val isViewed: Boolean
    )

    private fun RefillPointDto.toViewModel(): RefillPointViewModel {
        return RefillPointViewModel(
            id = this.id,
            partnerName = this.partnerName,
            addressInfo = this.addressInfo,
            isViewed = this.isViewed
        )
    }

    companion object {

        private const val cameraMoveEventDebounceTimeoutSeconds = 1
    }
}