package ru.santaev.refillpoints.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.santaev.refillpoints.domain.dto.LocationDto
import ru.santaev.refillpoints.domain.repository.request.GetRefillPointsRequest

interface IRefillPointsRepository {

    fun getRefillPoints(request: GetRefillPointsRequest): Flowable<List<RefillPointDto>>

    fun markRefillPointAsViewed(refillPointId: Long): Completable

    fun getRefillPoint(refillPointId: Long): Single<RefillPointDto>

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