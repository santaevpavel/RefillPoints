package ru.santaev.refillpoints.view

import ru.santaev.refillpoints.presenter.RefillPointsMapFragmentPresenter

interface IRefillPointsMapView {

    fun showRefillPoints(refillPoints: List<RefillPointsMapFragmentPresenter.RefillPointViewModel>)
}