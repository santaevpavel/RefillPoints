package ru.santaev.refillpoints.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import ru.santaev.refillpoints.data.api.IRefillPointsApi
import ru.santaev.refillpoints.data.database.IRefillPointsDatabase
import ru.santaev.refillpoints.domain.repository.IRefillPointsRepository

class RefillPointsRepository(
    private val refillPointsApi: IRefillPointsApi,
    private val refillPointsDatabase: IRefillPointsDatabase,
    private val cacheValidator: IRefillPointsCacheValidator
) : IRefillPointsRepository {

    override fun getRefillPoints(): Flowable<List<IRefillPointsRepository.RefillPointDto>> {
        return Flowable.empty()
    }

    override fun markRefillPointAsViewed(refillPointId: Long): Completable {
        return Completable.complete()
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