package ru.santaev.refillpoints.data.api

import io.reactivex.Single
import ru.santaev.refillpoints.data.dto.LocationDto

interface IRefillPointsApi {

    fun getRefillPoints(): Single<List<RefillPointDto>>

    class RefillPointDto(
        val id: Long,
        val partnerName: String,
        val location: LocationDto,
        val workHours: String,
        val phones: String,
        val addressInfo: String,
        val fullAddress: String
    )
}