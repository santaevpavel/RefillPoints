package ru.santaev.refillpoints.view

import ru.santaev.refillpoints.presenter.RefillPointsListPresenter
import ru.santaev.refillpoints.presenter.RefillPointsMapPresenter


interface IRefillPointsListView {

    fun showRefillPoints(refillPoints: List<RefillPointsListPresenter.RefillPointViewModel>)

    fun openRefillPointDetails(point: RefillPointsMapPresenter.RefillPointViewModel)
}