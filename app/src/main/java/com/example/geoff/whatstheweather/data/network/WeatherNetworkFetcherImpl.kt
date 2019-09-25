package com.example.geoff.whatstheweather.data.network

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.geoff.whatstheweather.NoConnectivityException
import com.example.geoff.whatstheweather.data.model.city.FindCityResponse
import com.example.geoff.whatstheweather.data.model.current.CurrentWeatherResponse
import com.example.geoff.whatstheweather.data.model.forecast.ForecastWeatherResponse

class WeatherNetworkFetcherImpl(
    private val apiService: APIService
) : WeatherNetworkFetcher {

    override val downloadedCities: MutableLiveData<FindCityResponse> = MutableLiveData()
    override val downloadedCurrentWeather: MutableLiveData<CurrentWeatherResponse> = MutableLiveData()
    override val downloadedForecastWeather: MutableLiveData<ForecastWeatherResponse> = MutableLiveData()


    override suspend fun fetchCurrentWeather(location: String, unit: String) {
        try {
            val currentWeather = apiService.getCurrentWeatherAsync(location, unit).await()
            downloadedCurrentWeather.postValue(currentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Weather", "No connection")
        }
    }

    override suspend fun fetchCurrentWeather(lat: Double, lon: Double, unit: String) {
        try {
            val currentWeather = apiService.getCurrentWeatherAsync(lat, lon, unit).await()
            downloadedCurrentWeather.postValue(currentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Weather", "No connection")
        }
    }

    override suspend fun fetchForecastWeather(location: String, unit: String) {
        try {
            val forecastWeather = apiService.getForecastWeatherAsync(location, unit).await()
            downloadedForecastWeather.postValue(forecastWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Weather", "No connection")
        }
    }

    override suspend fun fetchForecastWeather(lat: Double, lon: Double, unit: String) {
        try {
            val forecastWeather = apiService.getForecastWeatherAsync(lat, lon, unit).await()
            downloadedForecastWeather.postValue(forecastWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Weather", "No connection")
        }
    }

    override suspend fun fetchCities(location: String) {
        try {
            val cities = apiService.findCityAsync(location).await()
            downloadedCities.postValue(cities)
        } catch (e: NoConnectivityException) {
            Log.e("Weather", "No connection")
        }
    }
}