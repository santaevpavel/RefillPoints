package ru.santaev.refillpoints.data.usecase

import io.reactivex.Flowable
import ru.santaev.refillpoints.data.dto.RefillPointDto

class GetRefillPointsUsecase : IUsecase<Flowable<List<RefillPointDto>>, GetRefillPointsUsecase.Param> {

    override fun execute(param: Param): Flowable<List<RefillPointDto>> {
        return Flowable.empty()
    }

    class Param
}