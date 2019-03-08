package ru.santaev.refillpoints.data.factory

import ru.santaev.refillpoints.data.database.IRefillPointsDatabase

interface IDatabaseFactory {

    fun getRefillPointsDatabase(): IRefillPointsDatabase
}