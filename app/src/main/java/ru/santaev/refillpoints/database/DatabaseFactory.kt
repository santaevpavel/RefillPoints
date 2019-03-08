package ru.santaev.refillpoints.database

import ru.santaev.refillpoints.data.database.IRefillPointsDatabase
import ru.santaev.refillpoints.data.factory.IDatabaseFactory

class DatabaseFactory(
    private val refillPointsDao: IRefillPointsDao
) : IDatabaseFactory {

    override fun getRefillPointsDatabase(): IRefillPointsDatabase {
        return RefillPointsDatabaseAdapter(refillPointsDao)
    }
}