package ru.santaev.refillpoints.domain.di.component

import dagger.Component
import ru.santaev.refillpoints.domain.di.module.UsecaseModule
import ru.santaev.refillpoints.domain.usecase.GetRefillPointsUsecase

@Component(modules = [UsecaseModule::class])
interface DomainComponent {

    fun getGetRefillPointsUsecase(): GetRefillPointsUsecase
}

class DomainComponentImpl(
    private val usecaseModule: UsecaseModule
): DomainComponent{

    override fun getGetRefillPointsUsecase(): GetRefillPointsUsecase {
        return usecaseModule.provideGetRefillPointsUsecase()
    }
}
