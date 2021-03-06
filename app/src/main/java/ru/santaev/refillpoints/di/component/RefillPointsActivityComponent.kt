package ru.santaev.refillpoints.di.component

import dagger.Component
import ru.santaev.refillpoints.di.module.RefillPointsActivityModule
import ru.santaev.refillpoints.di.scope.RefillPointsActivityScope
import ru.santaev.refillpoints.view.activity.RefillPointsActivity
import ru.santaev.refillpoints.view.fragment.RefillPointsListFragment
import ru.santaev.refillpoints.view.fragment.RefillPointsMapFragment

@RefillPointsActivityScope
@Component(
    modules = [RefillPointsActivityModule::class],
    dependencies = [ApplicationComponent::class]
)
interface RefillPointsActivityComponent {

    fun inject(activity: RefillPointsActivity)

    fun inject(fragment: RefillPointsMapFragment)

    fun inject(fragment: RefillPointsListFragment)
}