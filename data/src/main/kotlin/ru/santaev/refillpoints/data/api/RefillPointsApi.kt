package ru.santaev.refillpoints.data.api

import io.reactivex.Single
import ru.santaev.refillpoints.data.api.request.GetRefillPointsApiRequest

internal class RefillPointsApi(
    private val apiService: IRefillPointsApiService
): IRefillPointsApi {

    override fun getRefillPoints(
        request: GetRefillPointsApiRequest
    ): Single<List<IRefillPointsApi.RefillPointDto>> {
        return apiService.getRefillPoints(
            latitude = request.latitude,
            longitude = request.longitude,
            radius = request.radius
        )
            .map { it.payload }
    }
}