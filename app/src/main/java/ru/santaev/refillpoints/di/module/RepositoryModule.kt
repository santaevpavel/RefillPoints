package ru.santaev.refillpoints.di.module

import dagger.Module
import dagger.Provides
import ru.santaev.refillpoints.data.factory.getRepositoryFactory
import ru.santaev.refillpoints.domain.factory.IRepositoryFactory

@Module
class RepositoryModule {

    @Provides
    fun provideRepositoryFactory(): IRepositoryFactory {
        return getRepositoryFactory()
    }
}