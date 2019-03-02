package ru.santaev.refillpoints.data.usecase

interface IUsecase<R, Param> {

    fun execute(param: Param): R
}