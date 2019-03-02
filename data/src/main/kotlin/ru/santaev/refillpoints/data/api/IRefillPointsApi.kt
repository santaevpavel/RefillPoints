package ru.santaev.refillpoints.data.api

import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import ru.santaev.refillpoints.data.api.request.GetRefillPointsRequest
import ru.santaev.refillpoints.data.dto.LocationDto

interface IRefillPointsApi {

    fun getRefillPoints(request: GetRefillPointsRequest): Single<List<RefillPointDto>>

    data class RefillPointDto(
        @SerializedName("partnerName")
        val partnerName: String,
        @SerializedName("location")
        val location: LocationDto,
        @SerializedName("workHours")
        val workHours: String,
        @SerializedName("phones")
        val phones: String,
        @SerializedName("addressInfo")
        val addressInfo: String,
        @SerializedName("fullAddress")
        val fullAddress: String
    )

    class LocationDto(
        @SerializedName("latitude")
        val latitude: Double,
        @SerializedName("longitude")
        val longitude: Double
    )
}