package ru.santaev.refillpoints.domain.di.module

import dagger.Module
import dagger.Provides
import ru.santaev.refillpoints.domain.repository.IRefillPointsRepository
import ru.santaev.refillpoints.domain.usecase.GetRefillPointsUsecase

@Module
class UsecaseModule(
    private val refillPointsRepository: IRefillPointsRepository
) {

    @Provides
    fun provideGetRefillPointsUsecase(): GetRefillPointsUsecase {
        return GetRefillPointsUsecase(refillPointsRepository)
    }
}