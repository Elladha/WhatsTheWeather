package com.example.geoff.whatstheweather.data.model.current

import androidx.annotation.ArrayRes
import com.example.geoff.whatstheweather.data.model.*
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("main")
    val weatherInfo: WeatherInfo,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("dt")
    val datetime: Long,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val cityName: String,
    @SerializedName("sys")
    val sun: Sun
)