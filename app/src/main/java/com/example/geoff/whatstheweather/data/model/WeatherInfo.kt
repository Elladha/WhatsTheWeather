package com.example.geoff.whatstheweather.data.model

import com.google.gson.annotations.SerializedName

data class WeatherInfo (
    val temp: Float,
    val humidity: Int,
    val pressure: Float,
    @SerializedName("temp_min")
    val tempMin: Float,
    @SerializedName("temp_max")
    val tempMax: Float
)