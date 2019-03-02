package ru.santaev.refillpoints.data.database

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.santaev.refillpoints.data.dto.LocationDto

internal interface IRefillPointsDatabase {

    fun getRefillPoints(): Flowable<List<RefillPointDto>>

    fun insert(entities: List<RefillPointDto>)

    fun update(refillPointDto: RefillPointDto): Completable

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