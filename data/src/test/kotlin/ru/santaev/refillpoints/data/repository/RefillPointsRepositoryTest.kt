package ru.santaev.refillpoints.data.repository

import io.mockk.every
import io.mockk.mockk
import io.reactivex.Flowable
import org.amshove.kluent.shouldBe
import org.junit.Test
import ru.santaev.refillpoints.data.api.IRefillPointsApi
import ru.santaev.refillpoints.data.database.IRefillPointsDatabase
import ru.santaev.refillpoints.data.dto.LocationDto

class RefillPointsRepositoryTest {

    private val refillPointsFromDatabase: List<IRefillPointsDatabase.RefillPointDto> = listOf(
        createEntity(
            id = 1,
            partnerName = "Partner1"
        ),
        createEntity(
            id = 2,
            partnerName = "Partner2"
        )
    )

    @Test
    fun `test get list when cache is valid`() {
        val repository = createRepository()

        val refillPointValues = repository
            .getRefillPoints()
            .test()
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
            refillPointsDatabase = createDatabase(),
            cacheValidator = createCacheValidator()
        )
    }

    private fun createApi(): IRefillPointsApi {
        return mockk()
    }

    private fun createDatabase(): IRefillPointsDatabase {
        val database = mockk<IRefillPointsDatabase>()
        every {
            database.getRefillPoints()
        }.returns(
            Flowable.fromCallable { refillPointsFromDatabase }
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
    partnerName: String = "Partner",
    location: IRefillPointsApi.LocationDto = IRefillPointsApi.LocationDto(0.0, 0.0),
    workHours: String = "workHours",
    phones: String = "+100",
    addressInfo: String = "addressInfo",
    fullAddress: String = "fullAddress"
): IRefillPointsApi.RefillPointDto {
    return IRefillPointsApi.RefillPointDto(
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
        partnerName = partnerName,
        location = location,
        workHours = workHours,
        phones = phones,
        addressInfo = addressInfo,
        fullAddress = fullAddress,
        isViewed = isViewed
    )
}