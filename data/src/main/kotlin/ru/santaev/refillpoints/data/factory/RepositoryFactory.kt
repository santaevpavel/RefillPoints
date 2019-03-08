package ru.santaev.refillpoints.data.factory

import ru.santaev.refillpoints.data.repository.IRefillPointsCacheValidator
import ru.santaev.refillpoints.data.repository.RefillPointsCacheValidator
import ru.santaev.refillpoints.data.repository.RefillPointsRepository
import ru.santaev.refillpoints.domain.factory.IRepositoryFactory
import ru.santaev.refillpoints.domain.repository.IRefillPointsRepository

internal class RepositoryFactory(
    private val apiFactory: IApiFactory,
    private val databaseFactory: IDatabaseFactory
) : IRepositoryFactory {

    override fun getRefillPointsRepository(): IRefillPointsRepository {
        val instance = refillPointsRepositoryInstance
        return instance ?: createRefillPointsRepository().also { newInstance ->
            refillPointsRepositoryInstance = newInstance
        }
    }

    private fun createRefillPointsRepository(): IRefillPointsRepository {
        return RefillPointsRepository(
            refillPointsApi = apiFactory.getRefillPointsApi(),
            refillPointsDatabase = databaseFactory.getRefillPointsDatabase()
        )
    }

    private fun getRefillPointsCacheValidator(): IRefillPointsCacheValidator {
        return RefillPointsCacheValidator()
    }

    companion object {

        private var refillPointsRepositoryInstance: IRefillPointsRepository? = null
    }
}

fun getRepositoryFactory(databaseFactory: IDatabaseFactory): IRepositoryFactory {
    return RepositoryFactory(
        apiFactory = ApiFactory(),
        databaseFactory = databaseFactory
    )
}