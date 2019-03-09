package ru.santaev.refillpoints.domain.usecase

import io.reactivex.Flowable
import ru.santaev.refillpoints.domain.dto.RefillPointDto
import ru.santaev.refillpoints.domain.mappers.toDto
import ru.santaev.refillpoints.domain.repository.IRefillPointsRepository
import ru.santaev.refillpoints.domain.repository.request.GetRefillPointsRequest


class GetRefillPointsUsecase(
    private val refillPointsRepository: IRefillPointsRepository
) : IUsecase<Flowable<List<RefillPointDto>>, GetRefillPointsUsecase.Param> {

    override fun execute(param: Param): Flowable<List<RefillPointDto>> {
        return refillPointsRepository.getRefillPoints(
            request = GetRefillPointsRequest(
                latitude = param.lat,
                longitude = param.lng,
                radius = param.radius
            )
        )
            .map { list -> list.map { it.toDto() } }
    }

    class Param(
        val lat: Double,
        val lng: Double,
        val radius: Int
    )
}