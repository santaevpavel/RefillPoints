package ru.santaev.refillpoints.presenter

import ru.santaev.refillpoints.view.IRefillPointsView

class RefillPointsMapFragmentPresenter : BasePresenter<IRefillPointsView>(null) {

    fun setRefillPoints(refillPoints: List<RefillPointsMapPresenter.RefillPointViewModel>) {
        view?.setRefillPoints(refillPoints)
    }
}