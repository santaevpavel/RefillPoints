package ru.santaev.refillpoints.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.santaev.refillpoints.data.api.response.GetRefillPointsResponse


interface IRefillPointsApiService {

    @GET("$version/deposition_points")
    fun getRefillPoints(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("radius") radius: Int
    ): Single<GetRefillPointsResponse>

    companion object {
        private const val version = "v1"
    }
}