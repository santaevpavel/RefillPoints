package ru.santaev.refillpoints.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.santaev.refillpoints.data.dto.LocationDto

interface IRefillPointsRepository {

    fun getRefillPoints(): Flowable<List<RefillPointDto>>

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
}