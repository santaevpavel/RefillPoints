package ru.santaev.refillpoints.domain.repository.request


class GetRefillPointsRequest(
    val latitude: Double,
    val longitude: Double,
    val radius: Int
)