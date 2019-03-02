package ru.santaev.refillpoints.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.santaev.refillpoints.data.api.IRefillPointsApi
import ru.santaev.refillpoints.data.api.request.GetRefillPointsApiRequest
import ru.santaev.refillpoints.data.database.IRefillPointsDatabase
import ru.santaev.refillpoints.domain.repository.IRefillPointsRepository
import ru.santaev.refillpoints.domain.repository.request.GetRefillPointsRequest

internal class RefillPointsRepository(
    private val refillPointsApi: IRefillPointsApi,
    private val refillPointsDatabase: IRefillPointsDatabase,
    private val cacheValidator: IRefillPointsCacheValidator
) : IRefillPointsRepository {

    override fun getRefillPoints(
        request: GetRefillPointsRequest
    ): Flowable<List<IRefillPointsRepository.RefillPointDto>> {
        return refillPointsApi
            .getRefillPoints(request.toApiRequest())
            .toFlowable()
            .map { list -> list.map { it.toRepositoryDto(0) } }
    }

    override fun markRefillPointAsViewed(refillPointId: Long): Completable {
        return Completable.complete()
    }

    private fun GetRefillPointsRequest.toApiRequest(): GetRefillPointsApiRequest {
        return GetRefillPointsApiRequest(
            latitude = latitude,
            longitude = longitude,
            radius = radius
        )
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