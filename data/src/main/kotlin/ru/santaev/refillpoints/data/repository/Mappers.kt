package ru.santaev.refillpoints.data.repository

import ru.santaev.refillpoints.data.api.IRefillPointsApi
import ru.santaev.refillpoints.domain.dto.LocationDto
import ru.santaev.refillpoints.domain.repository.IRefillPointsRepository

internal fun IRefillPointsApi.RefillPointDto.toRepositoryDto(
    id: Long
): IRefillPointsRepository.RefillPointDto {
    return IRefillPointsRepository.RefillPointDto(
        id = id,
        partnerName = this.partnerName,
        location = this.location.toRepositoryDto(),
        workHours = this.workHours.orEmpty() ,
        addressInfo = this.addressInfo.orEmpty(),
        phones = this.phones.orEmpty(),
        fullAddress = this.fullAddress,
        isViewed = false
    )
}

internal fun IRefillPointsApi.LocationDto.toRepositoryDto(): LocationDto {
    return LocationDto(
        lat = this.latitude,
        lng = this.longitude
    )
}