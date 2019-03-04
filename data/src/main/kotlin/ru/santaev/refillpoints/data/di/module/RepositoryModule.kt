package ru.santaev.refillpoints.data.di.module

import dagger.Module
import dagger.Provides
import ru.santaev.refillpoints.data.api.IRefillPointsApi
import ru.santaev.refillpoints.data.database.IRefillPointsDatabase
import ru.santaev.refillpoints.data.database.RefillPointsDatabase
import ru.santaev.refillpoints.data.repository.IRefillPointsCacheValidator
import ru.santaev.refillpoints.data.repository.RefillPointsCacheValidator
import ru.santaev.refillpoints.data.repository.RefillPointsRepository
import ru.santaev.refillpoints.domain.repository.IRefillPointsRepository

@Module(includes = [ApiModule::class])
class RepositoryModule {

    @Provides
    internal fun provideRefillPointsRepository(
        refillPointsApi: IRefillPointsApi,
        refillPointsDatabase: IRefillPointsDatabase,
        cacheValidator :IRefillPointsCacheValidator
    ): IRefillPointsRepository {
        return RefillPointsRepository(
            refillPointsApi = refillPointsApi,
            refillPointsDatabase = refillPointsDatabase,
            cacheValidator = cacheValidator
        )
    }

    @Provides
    internal fun provideRefillPointsCacheValidator(): IRefillPointsCacheValidator {
        return RefillPointsCacheValidator()
    }

    @Provides
    internal fun provideRefillPointsDatabase(): IRefillPointsDatabase {
        return RefillPointsDatabase()
    }
}