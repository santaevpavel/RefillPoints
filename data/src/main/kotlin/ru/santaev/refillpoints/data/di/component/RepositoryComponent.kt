package ru.santaev.refillpoints.data.di.component

import dagger.Component
import ru.santaev.refillpoints.data.di.module.ApiModule
import ru.santaev.refillpoints.data.di.module.RepositoryModule
import ru.santaev.refillpoints.domain.repository.IRefillPointsRepository


@Component(modules = [RepositoryModule::class])
interface RepositoryComponent {

    fun getRefillPointsRepository(): IRefillPointsRepository
}

class RepositoryComponentImpl(
    private val repositoryModule: RepositoryModule,
    private val apiModule: ApiModule
) : RepositoryComponent {

    override fun getRefillPointsRepository(): IRefillPointsRepository {
        return repositoryModule.provideRefillPointsRepository(
            refillPointsApi = apiModule.provideRefillPointsApi(apiModule.provideRefillPointsApiService()),
            cacheValidator = repositoryModule.provideRefillPointsCacheValidator(),
            refillPointsDatabase = repositoryModule.provideRefillPointsDatabase()
        )
    }
}
