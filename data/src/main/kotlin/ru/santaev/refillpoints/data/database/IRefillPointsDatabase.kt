package ru.santaev.refillpoints.data.database

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.santaev.refillpoints.data.dto.LocationDto

interface IRefillPointsDatabase {

    fun getRefillPoints(): Flowable<List<RefillPointDto>>

    fun getRefillPoint(externalId: String): Single<RefillPointDto>

    fun insert(entities: List<RefillPointDto>): Completable

    fun update(refillPointDto: RefillPointDto): Completable

    data class RefillPointDto(
        val id: Long,
        val externalId: String,
        val partnerName: String,
        val location: LocationDto,
        val workHours: String?,
        val phones: String?,
        val addressInfo: String?,
        val fullAddress: String,
        val isViewed: Boolean,
        val updateDate: Long
    )
}