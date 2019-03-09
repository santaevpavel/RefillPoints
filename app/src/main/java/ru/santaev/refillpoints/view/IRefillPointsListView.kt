package ru.santaev.refillpoints.view

import ru.santaev.refillpoints.presenter.RefillPointsListPresenter


interface IRefillPointsListView {

    fun showRefillPoints(refillPoints: List<RefillPointsListPresenter.RefillPointViewModel>)

    fun openRefillPointDetails(point: RefillPointsListPresenter.RefillPointViewModel)
}