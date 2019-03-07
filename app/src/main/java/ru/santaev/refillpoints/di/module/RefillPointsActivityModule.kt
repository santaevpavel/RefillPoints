package ru.santaev.refillpoints.di.module

import dagger.Module
import dagger.Provides
import ru.santaev.refillpoints.di.scope.RefillPointsActivityScope
import ru.santaev.refillpoints.domain.factory.IUsecaseFactory
import ru.santaev.refillpoints.presenter.RefillPointsMapFragmentPresenter
import ru.santaev.refillpoints.presenter.RefillPointsMapPresenter

@Module
class RefillPointsActivityModule {

    @RefillPointsActivityScope
    @Provides
    fun provideRefillPointsMapPresenter(
        usecaseFactory: IUsecaseFactory
    ): RefillPointsMapPresenter {
        return RefillPointsMapPresenter(usecaseFactory.getGetRefillPointsUsecase())
    }

    @RefillPointsActivityScope
    @Provides
    fun provideRefillPointsMapFragmentPresenter(
        parentPresenter: RefillPointsMapPresenter
    ): RefillPointsMapFragmentPresenter{
        return RefillPointsMapFragmentPresenter(parentPresenter)
    }
}