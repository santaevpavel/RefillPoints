package ru.santaev.refillpoints.domain.usecase

import io.reactivex.Flowable
import ru.santaev.refillpoints.domain.dto.RefillPointDto

class GetRefillPointsUsecase : IUsecase<Flowable<List<RefillPointDto>>, GetRefillPointsUsecase.Param> {

    override fun execute(param: Param): Flowable<List<RefillPointDto>> {
        return Flowable.empty()
    }

    class Param
}