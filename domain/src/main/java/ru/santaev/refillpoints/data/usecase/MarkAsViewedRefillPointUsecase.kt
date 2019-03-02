package ru.santaev.refillpoints.data.usecase

import io.reactivex.Completable

class MarkAsViewedRefillPointUsecase : IUsecase<Completable, MarkAsViewedRefillPointUsecase.Param> {

    override fun execute(param: Param): Completable {
        return Completable.complete()
    }

    class Param(
        val refillPointId: Long
    )
}