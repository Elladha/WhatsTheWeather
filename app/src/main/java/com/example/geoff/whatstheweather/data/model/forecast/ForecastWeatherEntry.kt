package com.example.geoff.whatstheweather.data.model.forecast

import com.example.geoff.whatstheweather.data.model.Weather
import com.example.geoff.whatstheweather.data.model.WeatherInfo
import com.example.geoff.whatstheweather.data.model.Wind
import com.google.gson.annotations.SerializedName

data class ForecastWeatherEntry(
    @SerializedName("dt")
    val datetime: Long,
    @SerializedName("main")
    val weatherInfo: WeatherInfo,
    val weather: List<Weather>,
    val wind: Wind,
    @SerializedName("dt_txt")
    val date: String
)
