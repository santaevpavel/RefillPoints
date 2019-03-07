package ru.santaev.refillpoints.presenter

import ru.santaev.refillpoints.domain.dto.RefillPointDto
import ru.santaev.refillpoints.view.IRefillPointsView

class RefillPointsMapPresenter: BasePresenter<IRefillPointsView>(null) {

    private var refillPoints: List<RefillPointDto>? = null

    fun onLoadedRefillPoints(refillPoints: List<RefillPointDto>) {
        this.refillPoints = refillPoints
    }
}