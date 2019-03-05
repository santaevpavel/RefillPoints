package ru.santaev.refillpoints.domain.factory

import ru.santaev.refillpoints.domain.usecase.GetRefillPointsUsecase

interface IUsecaseFactory {

    fun getGetRefillPointsUsecase(): GetRefillPointsUsecase
}

internal class UsecaseFactory(
    private val repositoryFactory: IRepositoryFactory
) : IUsecaseFactory {

    override fun getGetRefillPointsUsecase(): GetRefillPointsUsecase {
        return GetRefillPointsUsecase(repositoryFactory.getRefillPointsRepository())
    }
}

fun getUsecaseFactory(
    repositoryFactory: IRepositoryFactory
): IUsecaseFactory {
    return UsecaseFactory(repositoryFactory)
}