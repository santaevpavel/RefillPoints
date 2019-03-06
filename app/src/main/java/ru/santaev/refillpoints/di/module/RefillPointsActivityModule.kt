package ru.santaev.refillpoints.di.module

import dagger.Module
import dagger.Provides
import ru.santaev.refillpoints.domain.factory.IUsecaseFactory
import ru.santaev.refillpoints.presenter.RefillPointsMapPresenter

@Module
class RefillPointsActivityModule {

    @Provides
    fun provideRefillPointsMapPresenter(
        usecaseFactory: IUsecaseFactory
    ): RefillPointsMapPresenter {
        return RefillPointsMapPresenter(usecaseFactory.getGetRefillPointsUsecase())
    }

    /*@Singleton
    @Provides
    fun provideRefillPointsMapFragmentPresenter(): RefillPointsMapPresenter {
        return provideRefillPointsMapFragmentPresenter()
    }*/
}