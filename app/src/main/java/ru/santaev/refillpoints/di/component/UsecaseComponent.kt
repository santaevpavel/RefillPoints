package ru.santaev.refillpoints.di.component

import dagger.Subcomponent
import ru.santaev.refillpoints.domain.di.module.UsecaseModule

@Subcomponent(modules = [UsecaseModule::class])
interface UsecaseComponent {

    // fun createGetRefillPointsUsecase(): GetRefillPointsUsecase
}