package ru.santaev.refillpoints

import android.app.Application
import ru.santaev.refillpoints.di.component.ApplicationComponent
import ru.santaev.refillpoints.di.component.DaggerApplicationComponent
import ru.santaev.refillpoints.di.module.ContextModule

class RefillPointsApplication : Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }
}