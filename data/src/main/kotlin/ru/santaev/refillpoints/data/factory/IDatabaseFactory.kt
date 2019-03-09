package ru.santaev.refillpoints.data.factory

import ru.santaev.refillpoints.data.database.IRefillPointQueriesDatabase
import ru.santaev.refillpoints.data.database.IRefillPointsDatabase

interface IDatabaseFactory {

    fun getRefillPointsDatabase(): IRefillPointsDatabase

    fun getRefillPointQueriesDatabase(): IRefillPointQueriesDatabase
}