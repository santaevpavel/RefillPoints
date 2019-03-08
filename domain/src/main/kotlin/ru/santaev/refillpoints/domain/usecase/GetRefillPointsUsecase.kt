package ru.santaev.refillpoints.domain.usecase

import io.reactivex.Single
import ru.santaev.refillpoints.domain.dto.RefillPointDto
import ru.santaev.refillpoints.domain.repository.IRefillPointsRepository
import ru.santaev.refillpoints.domain.repository.request.GetRefillPointsRequest


class GetRefillPointsUsecase(
    private val refillPointsRepository: IRefillPointsRepository
) : IUsecase<Single<List<RefillPointDto>>, GetRefillPointsUsecase.Param> {

    override fun execute(param: Param): Single<List<RefillPointDto>> {
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

    private fun IRefillPointsRepository.RefillPointDto.toDto(): RefillPointDto {
        return RefillPointDto(
            id = this.id,
            partnerName = this.partnerName,
            location = this.location,
            workHours = this.workHours,
            phones = this.phones,
            addressInfo = this.addressInfo,
            fullAddress = this.fullAddress,
            isViewed = this.isViewed
        )
    }
}