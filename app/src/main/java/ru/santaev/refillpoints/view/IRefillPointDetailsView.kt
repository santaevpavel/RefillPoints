package ru.santaev.refillpoints.view

import ru.santaev.refillpoints.presenter.RefillPointDetailsPresenter.RefillPointDetailsViewModel


interface IRefillPointDetailsView {

    fun showRefillPoint(refillPoint: RefillPointDetailsViewModel)
}