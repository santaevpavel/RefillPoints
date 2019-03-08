package ru.santaev.refillpoints.di.module

import dagger.Module
import dagger.Provides
import ru.santaev.refillpoints.data.factory.IDatabaseFactory
import ru.santaev.refillpoints.data.factory.getRepositoryFactory
import ru.santaev.refillpoints.domain.factory.IRepositoryFactory

@Module(includes = [DatabaseModule::class])
class RepositoryModule {

    @Provides
    fun provideRepositoryFactory(databaseFactory: IDatabaseFactory): IRepositoryFactory {
        return getRepositoryFactory(databaseFactory)
    }
}