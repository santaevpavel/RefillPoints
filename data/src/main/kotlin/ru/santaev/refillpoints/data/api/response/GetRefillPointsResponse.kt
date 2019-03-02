package ru.santaev.refillpoints.data.api.response

import com.google.gson.annotations.SerializedName
import ru.santaev.refillpoints.data.api.IRefillPointsApi

data class GetRefillPointsResponse(
    @SerializedName("resultCode") val resultCode: String,
    @SerializedName("payload") val payload: List<IRefillPointsApi.RefillPointDto>
)