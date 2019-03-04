package ru.santaev.refillpoints.data.di.module

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.santaev.refillpoints.data.api.IRefillPointsApi
import ru.santaev.refillpoints.data.api.IRefillPointsApiService
import ru.santaev.refillpoints.data.api.RefillPointsApi

@Module
class ApiModule {

    @Provides
    internal fun provideRefillPointsApi(apiService: IRefillPointsApiService): IRefillPointsApi {
        return RefillPointsApi(apiService = apiService)
    }

    @Provides
    internal fun provideRefillPointsApiService(): IRefillPointsApiService {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(urlTinkoffApi)
            .build()
        return retrofit.create(IRefillPointsApiService::class.java)
    }

    companion object {
        private const val urlTinkoffApi = "https://api.tinkoff.ru/"
    }
}