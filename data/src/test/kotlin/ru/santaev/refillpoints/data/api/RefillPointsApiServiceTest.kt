package ru.santaev.refillpoints.data.api

import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RefillPointsApiServiceTest {

    private val api: IRefillPointsApiService = create()

    @Test
    fun getRefillPoints() {
        api.getRefillPoints(
            latitude = 55.755786,
            longitude = 37.617633,
            radius = 1000
        )
            .test()
            .assertComplete()
            .values()
            .also { println(it.first()) }

    }

    private fun create(): IRefillPointsApiService  {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.tinkoff.ru/")
            .build()
        return retrofit.create(IRefillPointsApiService::class.java)
    }
}