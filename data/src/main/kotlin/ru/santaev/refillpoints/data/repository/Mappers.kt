package ru.santaev.refillpoints.data.repository

import ru.santaev.refillpoints.data.api.IRefillPointsApi
import ru.santaev.refillpoints.data.database.IRefillPointsDatabase
import ru.santaev.refillpoints.domain.dto.LocationDto
import ru.santaev.refillpoints.domain.repository.IRefillPointsRepository

internal fun IRefillPointsApi.RefillPointDto.toRepositoryDto(
    id: Long
): IRefillPointsRepository.RefillPointDto {
    return IRefillPointsRepository.RefillPointDto(
        id = id,
        partnerName = this.partnerName,
        location = this.location.toRepositoryDto(),
        workHours = this.workHours.orEmpty(),
        addressInfo = this.addressInfo.orEmpty(),
        phones = this.phones.orEmpty(),
        fullAddress = this.fullAddress,
        isViewed = false
    )
}

internal fun IRefillPointsDatabase.RefillPointDto.toRepositoryDto(
): IRefillPointsRepository.RefillPointDto {
    return IRefillPointsRepository.RefillPointDto(
        id = this.id,
        partnerName = this.partnerName,
        location = this.location.toRepositoryDto(),
        workHours = this.workHours.orEmpty(),
        addressInfo = this.addressInfo.orEmpty(),
        phones = this.phones.orEmpty(),
        fullAddress = this.fullAddress,
        isViewed = this.isViewed
    )
}

internal fun IRefillPointsApi.RefillPointDto.toDatabaseDto(
    id: Long
): IRefillPointsDatabase.RefillPointDto {
    return IRefillPointsDatabase.RefillPointDto(
        id = id,
        externalId = this.externalId,
        partnerName = this.partnerName,
        location = this.location.toDatabaseDto(),
        workHours = this.workHours.orEmpty(),
        addressInfo = this.addressInfo.orEmpty(),
        phones = this.phones.orEmpty(),
        fullAddress = this.fullAddress,
        isViewed = false,
        updateDate = 0
    )
}

internal fun IRefillPointsApi.LocationDto.toRepositoryDto(): LocationDto {
    return LocationDto(
        lat = this.latitude,
        lng = this.longitude
    )
}

internal fun ru.santaev.refillpoints.data.dto.LocationDto.toRepositoryDto(): LocationDto {
    return LocationDto(
        lat = this.lat,
        lng = this.lng
    )
}

internal fun IRefillPointsApi.LocationDto.toDatabaseDto(): ru.santaev.refillpoints.data.dto.LocationDto {
    return ru.santaev.refillpoints.data.dto.LocationDto(
        lat = this.latitude,
        lng = this.longitude
    )
}