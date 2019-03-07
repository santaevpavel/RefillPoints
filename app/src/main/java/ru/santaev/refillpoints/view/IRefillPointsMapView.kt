package ru.santaev.refillpoints.view

import ru.santaev.refillpoints.presenter.RefillPointsMapPresenter

interface IRefillPointsMapView {

    fun showRefillPoints(refillPoints: List<RefillPointsMapPresenter.RefillPointViewModel>)
}