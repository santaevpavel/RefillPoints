package ru.santaev.refillpoints.di.component

import android.content.Context
import dagger.Component
import ru.santaev.refillpoints.di.module.ContextModule
import ru.santaev.refillpoints.di.module.RepositoryModule
import ru.santaev.refillpoints.di.module.UsecaseModule
import ru.santaev.refillpoints.domain.factory.IUsecaseFactory

@Component(
    modules = [ContextModule::class, UsecaseModule::class, RepositoryModule::class]
)
interface ApplicationComponent {

    fun getApplicationContext(): Context

    fun getUsecaseFactory(): IUsecaseFactory
}