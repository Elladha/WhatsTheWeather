package com.example.geoff.whatstheweather.data.model.forecast

import com.google.gson.annotations.SerializedName

data class ForecastWeatherResponse (
    @SerializedName("cnt")
    val cpt: Int,
    @SerializedName("list")
    val weatherList: List<ForecastWeatherEntry>

)