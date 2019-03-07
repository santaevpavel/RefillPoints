package ru.santaev.refillpoints.view

import ru.santaev.refillpoints.domain.dto.RefillPointDto

interface IRefillPointsView {

    fun passRefillPoints(refillPoints: List<RefillPointDto>)
}