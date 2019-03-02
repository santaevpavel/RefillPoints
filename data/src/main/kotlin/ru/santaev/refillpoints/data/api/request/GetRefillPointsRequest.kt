package ru.santaev.refillpoints.data.api.request


internal class GetRefillPointsRequest(
    val latitude: Double,
    val longitude: Double,
    val radius: Int
)