package ru.santaev.refillpoints.di.module

import dagger.Module
import dagger.Provides
import ru.santaev.refillpoints.di.scope.RefillPointDetailsActivityScope
import ru.santaev.refillpoints.domain.factory.IUsecaseFactory
import ru.santaev.refillpoints.presenter.RefillPointDetailsPresenter

@Module
class RefillPointDetailsActivityModule {

    @RefillPointDetailsActivityScope
    @Provides
    fun provideRefillPointsDetailsPresenter(
        usecaseFactory: IUsecaseFactory
    ): RefillPointDetailsPresenter {
        return RefillPointDetailsPresenter(
            getRefillPointUsecase = usecaseFactory.getGetRefillPointUsecase(),
            markAsViewedRefillPointUsecase = usecaseFactory.getMarkAsViewedRefillPointUsecase()
        )
    }
}