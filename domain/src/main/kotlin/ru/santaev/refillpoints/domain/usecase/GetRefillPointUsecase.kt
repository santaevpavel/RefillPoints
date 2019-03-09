package ru.santaev.refillpoints.domain.usecase

import io.reactivex.Single
import ru.santaev.refillpoints.domain.dto.RefillPointDto
import ru.santaev.refillpoints.domain.mappers.toDto
import ru.santaev.refillpoints.domain.repository.IRefillPointsRepository

class GetRefillPointUsecase(
    private val refillPointsRepository: IRefillPointsRepository
) : IUsecase<Single<RefillPointDto>, GetRefillPointUsecase.Param> {

    override fun execute(param: Param): Single<RefillPointDto> {
        return refillPointsRepository
            .getRefillPoint(param.refillPointId)
            .map { it.toDto() }
    }

    class Param(
        val refillPointId: Long
    )
}