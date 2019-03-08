package ru.santaev.refillpoints.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import ru.santaev.refillpoints.data.api.IRefillPointsApi
import ru.santaev.refillpoints.data.api.request.GetRefillPointsApiRequest
import ru.santaev.refillpoints.data.database.IRefillPointsDatabase
import ru.santaev.refillpoints.data.utils.distanceBetween
import ru.santaev.refillpoints.domain.repository.IRefillPointsRepository
import ru.santaev.refillpoints.domain.repository.request.GetRefillPointsRequest

internal class RefillPointsRepository(
    private val refillPointsApi: IRefillPointsApi,
    private val refillPointsDatabase: IRefillPointsDatabase
) : IRefillPointsRepository {

    override fun getRefillPoints(
        request: GetRefillPointsRequest
    ): Single<List<IRefillPointsRepository.RefillPointDto>> {
        return getTransformedApiRefillPoints(request)
    }

    override fun markRefillPointAsViewed(refillPointId: Long): Completable {
        return Completable.complete()
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
    ): Single<List<IRefillPointsRepository.RefillPointDto>> {
        return refillPointsApi
            .getRefillPoints(request.toApiRequest())
            .flatMap { list ->
                saveToDatabase(list)
                    .toFlowable<List<IRefillPointsRepository.RefillPointDto>>()
                    .concatWith(getFromDatabase(request))
                    .first(listOf())
            }
            .subscribeOn(Schedulers.io())
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
            refillPointsDatabase.update(
                refillPointDto = refillPoint.copy(
                    id = entityInDb.id,
                    externalId = entityInDb.externalId
                )
            )
        }
    }
}

interface IRefillPointsCacheValidator {

    fun isRefillPointsCacheValid(): Boolean
}

class RefillPointsCacheValidator : IRefillPointsCacheValidator {

    override fun isRefillPointsCacheValid(): Boolean {
        return false
    }
}