package ru.santaev.refillpoints.data.api.request


internal class GetRefillPointsApiRequest(
    val latitude: Double,
    val longitude: Double,
    val radius: Int
)