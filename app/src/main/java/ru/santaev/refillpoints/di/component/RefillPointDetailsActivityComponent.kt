package ru.santaev.refillpoints.di.component

import dagger.Component
import ru.santaev.refillpoints.di.module.RefillPointDetailsActivityModule
import ru.santaev.refillpoints.di.scope.RefillPointDetailsActivityScope
import ru.santaev.refillpoints.view.activity.RefillPointDetailsActivity

@RefillPointDetailsActivityScope
@Component(
    modules = [RefillPointDetailsActivityModule::class],
    dependencies = [ApplicationComponent::class]
)
interface RefillPointDetailsActivityComponent {

    fun inject(activity: RefillPointDetailsActivity)
}