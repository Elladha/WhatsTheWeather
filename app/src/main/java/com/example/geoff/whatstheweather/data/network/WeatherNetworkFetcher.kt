package com.example.geoff.whatstheweather.data.network

import androidx.lifecycle.LiveData
import com.example.geoff.whatstheweather.data.model.city.FindCityResponse
import com.example.geoff.whatstheweather.data.model.current.CurrentWeatherResponse
import com.example.geoff.whatstheweather.data.model.forecast.ForecastWeatherResponse

interface WeatherNetworkFetcher {

    val downloadedCurrentWeather : LiveData<CurrentWeatherResponse>
    val downloadedForecastWeather : LiveData<ForecastWeatherResponse>
    val downloadedCities: LiveData<FindCityResponse>

    suspend fun fetchCurrentWeather(location: String, unit: String)
    suspend fun fetchCurrentWeather(lat: Double, lon: Double, unit: String)
    suspend fun fetchForecastWeather(location: String, unit: String)
    suspend fun fetchForecastWeather(lat: Double, lon: Double, unit: String)
    suspend fun fetchCities(location: String)
}