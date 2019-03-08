package ru.santaev.refillpoints.view

import ru.santaev.refillpoints.presenter.RefillPointsMapPresenter

interface IRefillPointsMapView {

    fun showRefillPoints(refillPoints: List<RefillPointsMapPresenter.RefillPointViewModel>)

    fun showBottomSheet(point: RefillPointsMapPresenter.RefillPointViewModel)

    fun openRefillPointDetails(point: RefillPointsMapPresenter.RefillPointViewModel)
}