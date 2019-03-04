package ru.santaev.refillpoints.di.module

import dagger.Module
import dagger.Provides
import ru.santaev.refillpoints.domain.di.component.DomainComponent
import ru.santaev.refillpoints.presenter.RefillPointsMapPresenter

@Module
class RefillPointsActivityModule(
    private val domainComponent: DomainComponent
) {

    /*@Provides
    fun provideRefillPointsMapPresenter(
        refillPointsUsecase: GetRefillPointsUsecase
    ): RefillPointsMapPresenter {
        return RefillPointsMapPresenter(refillPointsUsecase)
    }*/

    @Provides
    fun provideRefillPointsMapPresenter(): RefillPointsMapPresenter {
        return RefillPointsMapPresenter(domainComponent.getGetRefillPointsUsecase())
    }
}