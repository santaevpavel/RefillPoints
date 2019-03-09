package ru.santaev.refillpoints.data.database

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.santaev.refillpoints.data.dto.LocationDto

interface IRefillPointQueriesDatabase {

    fun insertRefillPointsQuery(refillPointQuery: RefillPointQueryDto): Completable

    fun getRefillPointsQueries(): Flowable<List<RefillPointQueryDto>>

    fun getRefillPointsQueries(afterDate: Long): Flowable<List<RefillPointQueryDto>>

    data class RefillPointQueryDto(
        val id: Long,
        val date: Long,
        val location: LocationDto,
        val radius: Long
    )
}