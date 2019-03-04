package ru.santaev.refillpoints.di.module

import dagger.Module
import dagger.Provides
import ru.santaev.refillpoints.presenter.RefillPointsMapPresenter

@Module
class PresenterModule {

    @Provides
    fun provideRefillPointsMapPresenter(): RefillPointsMapPresenter {
        //return RefillPointsMapPresenter()
        TODO()
    }
}