package ru.santaev.refillpoints.di.module

import dagger.Module
import dagger.Provides
import ru.santaev.refillpoints.domain.factory.IRepositoryFactory
import ru.santaev.refillpoints.domain.factory.IUsecaseFactory
import ru.santaev.refillpoints.domain.factory.getUsecaseFactory

@Module(includes = [RepositoryModule::class])
class UsecaseModule {

    @Provides
    fun provideUsecaseFactory(repositoryFactory: IRepositoryFactory): IUsecaseFactory {
        return getUsecaseFactory(repositoryFactory)
    }
}