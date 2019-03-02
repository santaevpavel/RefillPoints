package ru.santaev.refillpoints.data.api

import io.reactivex.Single
import ru.santaev.refillpoints.data.api.request.GetRefillPointsRequest

class RefillPointsApi(
    private val apiService: IRefillPointsApiService
): IRefillPointsApi {

    override fun getRefillPoints(
        request: GetRefillPointsRequest
    ): Single<List<IRefillPointsApi.RefillPointDto>> {
        return apiService.getRefillPoints(
            latitude = request.latitude,
            longitude = request.longitude,
            radius = request.radius
        )
            .map { it.payload }
    }
}