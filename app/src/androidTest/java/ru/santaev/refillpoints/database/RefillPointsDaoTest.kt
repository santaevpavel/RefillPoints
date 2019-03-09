package ru.santaev.refillpoints.database

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Test
import ru.santaev.refillpoints.database.entity.RefillPointEntity
import ru.santaev.refillpoints.log.ILoggable


class RefillPointsDaoTest : ILoggable {

    private lateinit var database: IRefillPointsDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            RefillPointsDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
            .refillPointsDao()
    }

    @Test
    fun testGetEmptyList() {
        database
            .getRefillPoints()
            .doOnNext { log("Receive: $it") }
            .test()
            .awaitCount(1)
            .assertValue(listOf())
    }

    @Test
    fun testInsert() {
        database.insert(
            listOf(
                createEntity(
                    id = 0,
                    externalId = "AA"
                )
            )
        ).blockingAwait()
        database.insert(
            listOf(
                createEntity(
                    id = 0,
                    externalId = "BB"
                )
            )
        ).blockingAwait()
        database
            .getRefillPoints()
            .doOnNext { log("Receive: $it") }
            .test()
            .awaitCount(1)
            .assertValue {
                true
            }
    }

    @Test
    fun testInsertNotUnique() {
        database.insert(
            listOf(
                createEntity(
                    id = 0,
                    externalId = "AA"
                )
            )
        ).blockingAwait()
        database.insert(
            listOf(
                createEntity(
                    id = 0,
                    externalId = "AA"
                )
            )
        )
            .test()
            .assertError {
                log("Error: $it")
                true
            }

        database
            .getRefillPoints()
            .doOnNext { log("Receive: $it") }
            .test()
            .awaitCount(1)
            .assertValue { it.size == 1 }
    }

    private fun createEntity(
        id: Long,
        externalId: String,
        partnerName: String = "partnerName",
        latitude: Double = 0.0,
        longitude: Double = 0.0,
        workHours: String? = "",
        phones: String? = "",
        addressInfo: String? = "",
        fullAddress: String = "",
        isViewed: Boolean = true,
        updateDate: Long = 0
    ): RefillPointEntity {
        return RefillPointEntity(
            id = id,
            externalId = externalId,
            partnerName = partnerName,
            latitude = latitude,
            longitude = longitude,
            workHours = workHours,
            phones = phones,
            addressInfo = addressInfo,
            fullAddress = fullAddress,
            isViewed = isViewed,
            updateDate = updateDate
        )
    }
}