package ru.santaev.refillpoints.domain.factory

import ru.santaev.refillpoints.domain.usecase.GetRefillPointsUsecase
import ru.santaev.refillpoints.domain.usecase.MarkAsViewedRefillPointUsecase

interface IUsecaseFactory {

    fun getGetRefillPointsUsecase(): GetRefillPointsUsecase

    fun getMarkAsViewedRefillPointUsecase(): MarkAsViewedRefillPointUsecase
}

internal class UsecaseFactory(
    private val repositoryFactory: IRepositoryFactory
) : IUsecaseFactory {

    override fun getGetRefillPointsUsecase(): GetRefillPointsUsecase {
        return GetRefillPointsUsecase(repositoryFactory.getRefillPointsRepository())
    }

    override fun getMarkAsViewedRefillPointUsecase(): MarkAsViewedRefillPointUsecase {
        return MarkAsViewedRefillPointUsecase(repositoryFactory.getRefillPointsRepository())
    }
}

fun getUsecaseFactory(
    repositoryFactory: IRepositoryFactory
): IUsecaseFactory {
    return UsecaseFactory(repositoryFactory)
}