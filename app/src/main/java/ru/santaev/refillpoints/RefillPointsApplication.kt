package ru.santaev.refillpoints

import android.app.Application
import ru.santaev.refillpoints.data.di.component.RepositoryComponent
import ru.santaev.refillpoints.data.di.component.RepositoryComponentImpl
import ru.santaev.refillpoints.data.di.module.ApiModule
import ru.santaev.refillpoints.data.di.module.RepositoryModule
import ru.santaev.refillpoints.di.component.ApplicationComponent
import ru.santaev.refillpoints.di.component.DaggerApplicationComponent
import ru.santaev.refillpoints.di.module.ContextModule
import ru.santaev.refillpoints.domain.di.component.DomainComponent
import ru.santaev.refillpoints.domain.di.component.DomainComponentImpl
import ru.santaev.refillpoints.domain.di.module.UsecaseModule

class RefillPointsApplication : Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }
    /*val usecaseModule: UsecaseModule by lazy {
        UsecaseModule(repositoryComponent.getRefillPointsRepository())
    }*/
    val domainComponent: DomainComponent by lazy {
        DomainComponentImpl(UsecaseModule(repositoryComponent.getRefillPointsRepository()))
    }
    private val repositoryComponent: RepositoryComponent by lazy {
        RepositoryComponentImpl(
            repositoryModule = RepositoryModule(),
            apiModule = ApiModule()
        )
    }
}