package com.example.geoff.whatstheweather.data.network

import com.example.geoff.whatstheweather.data.model.city.FindCityResponse
import com.example.geoff.whatstheweather.data.model.current.CurrentWeatherResponse
import com.example.geoff.whatstheweather.data.model.forecast.ForecastWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

const val KEY = "071c8f2b96e53e842e7eb25d35fce189"
const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

interface APIService {

    @GET("weather")
    fun getCurrentWeatherAsync(
        @Query("q") location: String,
        @Query("units") units: String
    ): Deferred<CurrentWeatherResponse>

    @GET("weather")
    fun getCurrentWeatherAsync(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String
    ): Deferred<CurrentWeatherResponse>

    @GET("find")
    fun findCityAsync(
        @Query("q") location: String
    ): Deferred<FindCityResponse>

    @GET("forecast")
    fun getForecastWeatherAsync(
        @Query("q") location: String,
        @Query("units") units: String
    ): Deferred<ForecastWeatherResponse>

    @GET("forecast")
    fun getForecastWeatherAsync(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String
    ): Deferred<ForecastWeatherResponse>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): APIService {
            val interceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("appid", KEY)
                    .addQueryParameter("lang", Locale.getDefault().language)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val http = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(http)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService::class.java)
        }
    }
}