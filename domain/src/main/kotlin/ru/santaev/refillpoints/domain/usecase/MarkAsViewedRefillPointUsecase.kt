package ru.santaev.refillpoints.domain.usecase

import io.reactivex.Completable
import ru.santaev.refillpoints.domain.repository.IRefillPointsRepository

class MarkAsViewedRefillPointUsecase(
    private val refillPointsRepository: IRefillPointsRepository
) : IUsecase<Completable, MarkAsViewedRefillPointUsecase.Param> {

    override fun execute(param: Param): Completable {
        return refillPointsRepository.markRefillPointAsViewed(param.refillPointId)
    }

    class Param(
        val refillPointId: Long
    )
}