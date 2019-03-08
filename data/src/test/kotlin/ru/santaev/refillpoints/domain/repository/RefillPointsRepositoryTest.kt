package ru.santaev.refillpoints.domain.repository

import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.amshove.kluent.shouldBe
import org.junit.Test
import ru.santaev.refillpoints.data.api.IRefillPointsApi
import ru.santaev.refillpoints.data.database.IRefillPointsDatabase
import ru.santaev.refillpoints.data.dto.LocationDto
import ru.santaev.refillpoints.data.repository.IRefillPointsCacheValidator
import ru.santaev.refillpoints.data.repository.RefillPointsRepository
import ru.santaev.refillpoints.domain.repository.request.GetRefillPointsRequest

class RefillPointsRepositoryTest {

    private val refillPointsFromApi: List<IRefillPointsApi.RefillPointDto> = listOf(
        create(
            externalId = "A",
            partnerName = "Partner1"
        ),
        create(
            externalId = "B",
            partnerName = "Partner2"
        )
    )

    private val refillPointsFromDatabase: List<IRefillPointsDatabase.RefillPointDto> = listOf(
        createEntity(
            id = 1,
            externalId = "A",
            partnerName = "Partner1"
        ),
        createEntity(
            id = 2,
            externalId = "B",
            partnerName = "Partner2"
        )
    )

    @Test
    fun `test get list when cache is valid`() {
        val repository = createRepository()

        val refillPointValues = repository
            .getRefillPoints(request = GetRefillPointsRequest(0.0, 0.0, 10))
            .test()
            .await()
            .values()

        refillPointValues.size shouldBe 1

        val refillPoints = refillPointValues.first()

        refillPoints.size shouldBe 2
        refillPoints.any { it.id == 1L && it.partnerName == "Partner1" } shouldBe true
        refillPoints.any { it.id == 2L && it.partnerName == "Partner2" } shouldBe true
    }

    private fun createRepository(): RefillPointsRepository {
        return RefillPointsRepository(
            refillPointsApi = createApi(),
            refillPointsDatabase = createDatabase()
        )
    }

    private fun createApi(): IRefillPointsApi {
        val api = mockk<IRefillPointsApi>()
        every {
            api.getRefillPoints(any())
        }.returns(
            Single.just(refillPointsFromApi)
        )
        return api
    }

    private fun createDatabase(): IRefillPointsDatabase {
        val database = mockk<IRefillPointsDatabase>()

        every {
            database.insert(any())
        }.returns(
            Completable.complete()
        )
        every {
            database.update(any())
        }.returns(
            Completable.complete()
        )
        every {
            database.getRefillPoints()
        }.returns(
            Flowable
                .just(emptyList<IRefillPointsDatabase.RefillPointDto>())
                .concatWith(Flowable.never())
        ).andThen(
            Flowable
                .just(refillPointsFromDatabase)
                .concatWith(Flowable.never())
        )
        return database
    }

    private fun createCacheValidator(): IRefillPointsCacheValidator {
        val cacheValidator = mockk<IRefillPointsCacheValidator>()
        every {
            cacheValidator.isRefillPointsCacheValid()
        }.returns(true)
        return cacheValidator
    }
}

private fun create(
    externalId: String = "1",
    partnerName: String = "Partner",
    location: IRefillPointsApi.LocationDto = IRefillPointsApi.LocationDto(0.0, 0.0),
    workHours: String = "workHours",
    phones: String = "+100",
    addressInfo: String = "addressInfo",
    fullAddress: String = "fullAddress"
): IRefillPointsApi.RefillPointDto {
    return IRefillPointsApi.RefillPointDto(
        externalId = externalId,
        partnerName = partnerName,
        location = location,
        workHours = workHours,
        phones = phones,
        addressInfo = addressInfo,
        fullAddress = fullAddress
    )
}

private fun createEntity(
    id: Long,
    externalId: String,
    partnerName: String = "Partner",
    location: LocationDto = LocationDto(0.0, 0.0),
    workHours: String = "workHours",
    phones: String = "+100",
    addressInfo: String = "addressInfo",
    fullAddress: String = "fullAddress",
    isViewed: Boolean = false
): IRefillPointsDatabase.RefillPointDto {
    return IRefillPointsDatabase.RefillPointDto(
        id = id,
        externalId = externalId,
        partnerName = partnerName,
        location = location,
        workHours = workHours,
        phones = phones,
        addressInfo = addressInfo,
        fullAddress = fullAddress,
        isViewed = isViewed,
        updateDate = 0
    )
}