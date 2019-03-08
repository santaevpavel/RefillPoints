package ru.santaev.refillpoints.database

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.santaev.refillpoints.data.database.IRefillPointsDatabase
import ru.santaev.refillpoints.data.dto.LocationDto
import ru.santaev.refillpoints.database.entity.RefillPointEntity

class RefillPointsDatabaseAdapter(
    private val refillPointsDao: IRefillPointsDao
): IRefillPointsDatabase {

    override fun getRefillPoints(): Flowable<List<IRefillPointsDatabase.RefillPointDto>> {
        return refillPointsDao
            .getRefillPoints()
            .map { list -> list.map { it.toDto() } }
    }

    override fun getRefillPoint(externalId: String): Single<IRefillPointsDatabase.RefillPointDto> {
        return refillPointsDao
            .getRefillPoint(externalId)
            .map { it.toDto() }

    }

    override fun getRefillPoint(id: Long): Single<IRefillPointsDatabase.RefillPointDto> {
        return refillPointsDao
            .getRefillPoint(id)
            .map { it.toDto() }
    }
    override fun insert(entities: List<IRefillPointsDatabase.RefillPointDto>): Completable {
        return refillPointsDao.insert(entities.map { it.toEntity() })
    }

    override fun update(refillPointDto: IRefillPointsDatabase.RefillPointDto): Completable {
        return refillPointsDao.update(refillPointDto.toEntity())
    }

    private fun IRefillPointsDatabase.RefillPointDto.toEntity(): RefillPointEntity {
        return RefillPointEntity(
            id = this.id,
            externalId = this.externalId,
            partnerName = this.partnerName,
            latitude = this.location.lat,
            longitude = this.location.lng,
            workHours = this.workHours,
            phones = this.phones,
            addressInfo = this.addressInfo,
            fullAddress = this.fullAddress,
            isViewed = this.isViewed,
            updateDate = this.updateDate
        )
    }

    private fun RefillPointEntity.toDto(): IRefillPointsDatabase.RefillPointDto {
        return IRefillPointsDatabase.RefillPointDto(
            id = this.id,
            externalId = this.externalId,
            partnerName = this.partnerName,
            location = LocationDto(this.latitude, this.longitude),
            workHours = this.workHours,
            phones = this.phones,
            addressInfo = this.addressInfo,
            fullAddress = this.fullAddress,
            isViewed = this.isViewed,
            updateDate = this.updateDate
        )
    }
}