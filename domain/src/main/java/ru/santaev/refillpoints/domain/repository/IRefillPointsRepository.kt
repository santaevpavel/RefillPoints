package ru.santaev.refillpoints.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable

interface IRefillPointsRepository {

    fun getRefillPoints(): Flowable<RefillPointDto>

    fun markRefillPointAsViewed(refillPointId: Long): Completable

    class RefillPointDto(
        val id: Long,
        val partnerName: String,
        val location: LocationDto,
        val workHours: String,
        val phones: String,
        val addressInfo: String,
        val fullAddress: String,
        val isViewed: Boolean
    )

    class LocationDto(
        val lat: Double,
        val lng: Double
    )
}