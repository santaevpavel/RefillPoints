package ru.santaev.refillpoints.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.santaev.refillpoints.data.api.IRefillPointsApi
import ru.santaev.refillpoints.data.api.request.GetRefillPointsApiRequest
import ru.santaev.refillpoints.data.database.IRefillPointQueriesDatabase
import ru.santaev.refillpoints.data.database.IRefillPointQueriesDatabase.RefillPointQueryDto
import ru.santaev.refillpoints.data.database.IRefillPointsDatabase
import ru.santaev.refillpoints.data.dto.LocationDto
import ru.santaev.refillpoints.data.utils.distanceBetween
import ru.santaev.refillpoints.domain.repository.IRefillPointsRepository
import ru.santaev.refillpoints.domain.repository.request.GetRefillPointsRequest
import java.util.concurrent.TimeUnit

internal class RefillPointsRepository(
    private val refillPointsApi: IRefillPointsApi,
    private val refillPointsDatabase: IRefillPointsDatabase,
    private val refillPointQueriesDatabase: IRefillPointQueriesDatabase,
    private val cacheParams: CacheParams
) : IRefillPointsRepository {

    override fun getRefillPoints(
        request: GetRefillPointsRequest
    ): Flowable<List<IRefillPointsRepository.RefillPointDto>> {
        return isDatabaseEntitiesActual(request)
            .flatMapPublisher { isActual ->
                if (isActual) {
                    getFromDatabase(request)
                } else {
                    getTransformedApiRefillPoints(request)
                }
            }
            .subscribeOn(Schedulers.io())
    }

    override fun markRefillPointAsViewed(refillPointId: Long): Completable {
        return refillPointsDatabase
            .getRefillPoint(refillPointId)
            .flatMapCompletable { point ->
                updateEntity(
                    point.copy(
                        isViewed = true
                    )
                )
            }
            .subscribeOn(Schedulers.io())
    }

    override fun getRefillPoint(refillPointId: Long): Single<IRefillPointsRepository.RefillPointDto> {
        return refillPointsDatabase
            .getRefillPoint(refillPointId)
            .map { it.toRepositoryDto() }
            .subscribeOn(Schedulers.io())
    }

    private fun List<IRefillPointsDatabase.RefillPointDto>.filterInRadius(
        latitude: Double,
        longitude: Double,
        radius: Int
    ): List<IRefillPointsDatabase.RefillPointDto> {
        return filter { point ->
            distanceBetween(
                lat1 = latitude,
                lon1 = longitude,
                lat2 = point.location.lat,
                lon2 = point.location.lng
            ) < radius
        }
    }

    private fun getFromDatabase(
        request: GetRefillPointsRequest
    ): Flowable<List<IRefillPointsRepository.RefillPointDto>> {
        return refillPointsDatabase
            .getRefillPoints()
            .map { list ->
                list.filterInRadius(
                    latitude = request.latitude,
                    longitude = request.longitude,
                    radius = request.radius
                )
            }
            .map { list -> list.map { it.toRepositoryDto() } }
    }

    private fun GetRefillPointsRequest.toApiRequest(): GetRefillPointsApiRequest {
        return GetRefillPointsApiRequest(
            latitude = latitude,
            longitude = longitude,
            radius = radius
        )
    }

    private fun getTransformedApiRefillPoints(
        request: GetRefillPointsRequest
    ): Flowable<List<IRefillPointsRepository.RefillPointDto>> {
        return refillPointsApi
            .getRefillPoints(request.toApiRequest())
            .flatMapPublisher { list ->
                saveQuery(request)
                    .concatWith(saveToDatabase(list))
                    .toFlowable<List<IRefillPointsRepository.RefillPointDto>>()
                    .concatWith(getFromDatabase(request))
            }
    }

    private fun saveToDatabase(refillPoints: List<IRefillPointsApi.RefillPointDto>): Completable {
        return refillPointsDatabase
            .getRefillPoints()
            .first(emptyList())
            .flatMapCompletable { list ->
                saveRefillPoints(list, refillPoints.map { it.toDatabaseDto(0) })
            }
    }

    private fun saveRefillPoints(
        currentRefillPoints: List<IRefillPointsDatabase.RefillPointDto>,
        refillPoints: List<IRefillPointsDatabase.RefillPointDto>
    ): Completable {
        return refillPoints
            .map { point ->
                insertOrUpdateEntity(currentRefillPoints, point).onErrorComplete()
            }
            .let { Completable.concat(it) }
    }

    private fun insertOrUpdateEntity(
        currentRefillPoints: List<IRefillPointsDatabase.RefillPointDto>,
        refillPoint: IRefillPointsDatabase.RefillPointDto
    ): Completable {
        val entityInDb = currentRefillPoints.firstOrNull { it.externalId == refillPoint.externalId }
        return if (entityInDb == null) {
            refillPointsDatabase.insert(listOf(refillPoint))
        } else {
            if (entityInDb != refillPoint.copy(id = entityInDb.id)) {
                refillPointsDatabase.update(
                    refillPointDto = refillPoint.copy(
                        id = entityInDb.id,
                        externalId = entityInDb.externalId,
                        isViewed = entityInDb.isViewed
                    )
                )
            } else {
                Completable.complete()
            }
        }
    }

    private fun updateEntity(refillPoint: IRefillPointsDatabase.RefillPointDto): Completable {
        return refillPointsDatabase.update(refillPoint)
    }

    private fun saveQuery(request: GetRefillPointsRequest): Completable {
        return refillPointQueriesDatabase
            .insertRefillPointsQuery(
                refillPointQuery = RefillPointQueryDto(
                    id = 0,
                    date = System.currentTimeMillis(),
                    location = LocationDto(request.latitude, request.longitude),
                    radius = request.radius.toLong()
                )
            )
    }

    private fun isDatabaseEntitiesActual(request: GetRefillPointsRequest): Single<Boolean> {
        val earlierActualDate = System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(cacheParams.actualTimeSeconds)
        return refillPointQueriesDatabase
            .getRefillPointsQueries(afterDate = earlierActualDate)
            .first(listOf())
            .map { queries -> queries.any { it.isContain(request) } }
    }

    private fun RefillPointQueryDto.isContain(request: GetRefillPointsRequest): Boolean {
        val distanceBetweenPoints = distanceBetween(
            lat1 = location.lat,
            lon1 = location.lng,
            lat2 = request.latitude,
            lon2 = request.longitude
        )
        return radius > distanceBetweenPoints + request.radius
    }
}

class CacheParams(
    val actualTimeSeconds: Long
)

interface IRefillPointsCacheValidator {

    fun isRefillPointsCacheValid(): Boolean
}

class RefillPointsCacheValidator : IRefillPointsCacheValidator {

    override fun isRefillPointsCacheValid(): Boolean {
        return false
    }
}