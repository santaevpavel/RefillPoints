package ru.santaev.refillpoints.di.module

import dagger.Module
import dagger.Provides
import ru.santaev.refillpoints.data.factory.IDatabaseFactory
import ru.santaev.refillpoints.data.factory.getRepositoryFactory
import ru.santaev.refillpoints.data.repository.CacheParams
import ru.santaev.refillpoints.domain.factory.IRepositoryFactory
import java.util.concurrent.TimeUnit

@Module(includes = [DatabaseModule::class])
class RepositoryModule {

    @Provides
    fun provideRepositoryFactory(
        databaseFactory: IDatabaseFactory
    ): IRepositoryFactory {
        return getRepositoryFactory(
            databaseFactory = databaseFactory,
            cacheParams = cacheParams
        )
    }

    companion object {

        private const val cacheActualTimeSeconds = 10L
        private val cacheParams = CacheParams(
            actualTimeSeconds = TimeUnit.MINUTES.toSeconds(cacheActualTimeSeconds)
        )
    }
}