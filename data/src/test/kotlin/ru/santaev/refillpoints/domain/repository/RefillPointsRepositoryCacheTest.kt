package ru.santaev.refillpoints.domain.repository

import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.amshove.kluent.shouldBe
import org.junit.Test
import ru.santaev.refillpoints.data.api.IRefillPointsApi
import ru.santaev.refillpoints.data.database.IRefillPointQueriesDatabase
import ru.santaev.refillpoints.data.database.IRefillPointQueriesDatabase.RefillPointQueryDto
import ru.santaev.refillpoints.data.database.IRefillPointsDatabase
import ru.santaev.refillpoints.data.dto.LocationDto
import ru.santaev.refillpoints.data.repository.CacheParams
import ru.santaev.refillpoints.data.repository.RefillPointsRepository
import ru.santaev.refillpoints.domain.repository.request.GetRefillPointsRequest

class RefillPointsRepositoryCacheTest {

    private val queries: List<RefillPointQueryDto> = listOf(
        RefillPointQueryDto(
            id = 0,
            date = System.currentTimeMillis(),
            location = LocationDto(0.0, 0.0),
            radius = 100
        ),
        RefillPointQueryDto(
            id = 1,
            date = 0,
            location = LocationDto(0.0, 0.0),
            radius = 100000000
        ),
        RefillPointQueryDto(
            id = 2,
            date = 0,
            location = LocationDto(90.0, 90.0),
            radius = 1000
        )
    )

    private val refillPointsFromDatabase: List<IRefillPointsDatabase.RefillPointDto> = listOf(
        createEntity(
            id = 1,
            externalId = "A",
            partnerName = "Partner1",
            location = LocationDto(0.0, 0.0)
        ),
        createEntity(
            id = 2,
            externalId = "B",
            partnerName = "Partner2",
            location = LocationDto(90.0, 90.0)
        )
    )

    @Test
    fun `test get list when with queries`() {
        val repository = createRepository()

        val refillPointValues = repository
            .getRefillPoints(request = GetRefillPointsRequest(0.0, 0.0, 10))
            .test()
            .awaitCount(1)
            .values()

        refillPointValues.size shouldBe 1

        val refillPoints = refillPointValues.first()

        refillPoints.size shouldBe 1
        refillPoints.any { it.id == 1L && it.partnerName == "Partner1" } shouldBe true
    }

    @Test
    fun `test get list when with queries 2`() {
        val repository = createRepository()

        val refillPointValues = repository
            .getRefillPoints(request = GetRefillPointsRequest(90.0, 90.0, 10))
            .test()
            .awaitCount(1)
            .values()

        refillPointValues.size shouldBe 1
        val refillPoints = refillPointValues.first()

        refillPoints.size shouldBe 1
        refillPoints.any { it.id == 2L && it.partnerName == "Partner2" } shouldBe true
    }

    private fun createRepository(): RefillPointsRepository {
        return RefillPointsRepository(
            refillPointsApi = createApi(),
            refillPointsDatabase = createDatabase(),
            refillPointQueriesDatabase = createQueriesDatabase(),
            cacheParams = CacheParams(1)
        )
    }

    private fun createApi(): IRefillPointsApi {
        val api = mockk<IRefillPointsApi>()
        every {
            api.getRefillPoints(any())
        }.returns(
            Single.just(emptyList())
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
                .just(refillPointsFromDatabase)
                .concatWith(Flowable.never())
        )
        every {
            database.getRefillPoint(any<String>())
        }.returns(
            Single.just(
                createEntity(
                    id = 0,
                    externalId = "XX"
                )
            )
        )
        return database
    }

    private fun createQueriesDatabase(): IRefillPointQueriesDatabase {
        val database = mockk<IRefillPointQueriesDatabase>()
        every {
            database.insertRefillPointsQuery(any())
        }.returns(Completable.complete())
        every {
            database.getRefillPointsQueries(any())
        }.answers {
            queries
                .filter { it.date >= arg<Long>(0) }
                .let { Flowable.just(it) }
        }
        return database
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