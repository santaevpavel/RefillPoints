package ru.santaev.refillpoints.domain.usecase

interface IUsecase<R, Param> {

    fun execute(param: Param): R
}