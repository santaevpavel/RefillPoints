package ru.santaev.refillpoints.view

import ru.santaev.refillpoints.presenter.RefillPointsMapPresenter

interface IRefillPointsView {

    fun setRefillPoints(refillPoints: List<RefillPointsMapPresenter.RefillPointViewModel>)
}