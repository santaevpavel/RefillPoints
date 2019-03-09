package ru.santaev.refillpoints.database

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.santaev.refillpoints.data.database.IRefillPointQueriesDatabase
import ru.santaev.refillpoints.data.database.IRefillPointQueriesDatabase.RefillPointQueryDto
import ru.santaev.refillpoints.data.dto.LocationDto
import ru.santaev.refillpoints.database.dao.IRefillPointQueriesDao
import ru.santaev.refillpoints.database.entity.RefillPointQueryEntity

class RefillPointQueriesDatabaseAdapter(
    private val refillPointQueriesDao: IRefillPointQueriesDao
) : IRefillPointQueriesDatabase {

    override fun insertRefillPointsQuery(refillPointQuery: RefillPointQueryDto): Completable {
        return refillPointQueriesDao.insertRefillPointQuery(
            refillPointQuery = refillPointQuery.toEntity()
        )
    }

    override fun getRefillPointsQueries(): Flowable<List<RefillPointQueryDto>> {
        return refillPointQueriesDao
            .getRefillPointQueries()
            .map { list -> list.map { it.toDto() } }
    }

    override fun getRefillPointsQueries(afterDate: Long): Flowable<List<RefillPointQueryDto>> {
        return refillPointQueriesDao
            .getRefillPointQueries(afterDate)
            .map { list -> list.map { it.toDto() } }
    }

    private fun RefillPointQueryDto.toEntity(): RefillPointQueryEntity {
        return RefillPointQueryEntity(
            id = this.id,
            latitude = this.location.lng,
            longitude = this.location.lat,
            radius = this.radius,
            date = this.date
        )
    }

    private fun RefillPointQueryEntity.toDto(): RefillPointQueryDto {
        return RefillPointQueryDto(
            id = this.id,
            location = LocationDto(this.latitude, this.longitude),
            radius = this.radius,
            date = this.date
        )
    }
}