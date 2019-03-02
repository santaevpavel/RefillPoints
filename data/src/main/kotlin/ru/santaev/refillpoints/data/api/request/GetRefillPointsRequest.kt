package ru.santaev.refillpoints.data.api.request


class GetRefillPointsRequest(
    val latitude: Double,
    val longitude: Double,
    val radius: Int
)