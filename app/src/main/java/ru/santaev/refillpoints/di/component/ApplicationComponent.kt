package ru.santaev.refillpoints.di.component

import android.content.Context
import dagger.Component
import ru.santaev.refillpoints.di.module.ContextModule
import ru.santaev.refillpoints.domain.di.module.UsecaseModule

@Component(
    modules = [ContextModule::class, UsecaseModule::class]
)
interface ApplicationComponent {

    fun getApplicationContext(): Context
}