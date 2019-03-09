package ru.santaev.refillpoints.domain.mappers

import ru.santaev.refillpoints.domain.dto.RefillPointDto
import ru.santaev.refillpoints.domain.repository.IRefillPointsRepository

internal fun IRefillPointsRepository.RefillPointDto.toDto(): RefillPointDto {
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