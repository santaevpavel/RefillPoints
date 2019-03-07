package ru.santaev.refillpoints.di.module

import dagger.Module
import dagger.Provides
import ru.santaev.refillpoints.di.scope.RefillPointsActivityScope
import ru.santaev.refillpoints.domain.factory.IUsecaseFactory
import ru.santaev.refillpoints.presenter.RefillPointsListPresenter
import ru.santaev.refillpoints.presenter.RefillPointsMapPresenter
import ru.santaev.refillpoints.presenter.RefillPointsPresenter

@Module
class RefillPointsActivityModule {

    @RefillPointsActivityScope
    @Provides
    fun provideRefillPointsMapPresenter(): RefillPointsPresenter {
        return RefillPointsPresenter()
    }

    @RefillPointsActivityScope
    @Provides
    fun provideRefillPointsMapFragmentPresenter(
        parentPresenter: RefillPointsPresenter,
        usecaseFactory: IUsecaseFactory
    ): RefillPointsMapPresenter {
        return RefillPointsMapPresenter(
            parentPresenter = parentPresenter,
            getRefillPointsUsecase = usecaseFactory.getGetRefillPointsUsecase()
        )
    }

    @RefillPointsActivityScope
    @Provides
    fun provideRefillPointsListPresenter(
        parentPresenter: RefillPointsPresenter
    ): RefillPointsListPresenter {
        return RefillPointsListPresenter(
            parentPresenter = parentPresenter
        )
    }
}