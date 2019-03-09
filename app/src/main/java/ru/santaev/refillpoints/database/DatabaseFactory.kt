package ru.santaev.refillpoints.database

import ru.santaev.refillpoints.data.database.IRefillPointQueriesDatabase
import ru.santaev.refillpoints.data.database.IRefillPointsDatabase
import ru.santaev.refillpoints.data.factory.IDatabaseFactory
import ru.santaev.refillpoints.database.dao.IRefillPointQueriesDao
import ru.santaev.refillpoints.database.dao.IRefillPointsDao

class DatabaseFactory(
    private val refillPointsDao: IRefillPointsDao,
    private val refillPointQueriesDao: IRefillPointQueriesDao
) : IDatabaseFactory {

    override fun getRefillPointsDatabase(): IRefillPointsDatabase {
        return RefillPointsDatabaseAdapter(refillPointsDao)
    }

    override fun getRefillPointQueriesDatabase(): IRefillPointQueriesDatabase {
        return RefillPointQueriesDatabaseAdapter(refillPointQueriesDao)
    }
}