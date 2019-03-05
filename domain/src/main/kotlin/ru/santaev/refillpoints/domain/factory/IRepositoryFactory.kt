package ru.santaev.refillpoints.domain.factory

import ru.santaev.refillpoints.domain.repository.IRefillPointsRepository

interface IRepositoryFactory {

    fun getRefillPointsRepository(): IRefillPointsRepository
}