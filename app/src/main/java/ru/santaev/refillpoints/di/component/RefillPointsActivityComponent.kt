package ru.santaev.refillpoints.di.component

import dagger.Component
import ru.santaev.refillpoints.di.module.RefillPointsActivityModule
import ru.santaev.refillpoints.view.activity.RefillPointsActivity

@Component(
    modules = [RefillPointsActivityModule::class],
    dependencies = [ApplicationComponent::class]
)
interface RefillPointsActivityComponent {

    fun inject(activity: RefillPointsActivity)
}