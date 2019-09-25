package com.example.geoff.whatstheweather.data.model.forecast

data class HourlyForecastDisplayed(
    val datetime: Long,
    val url: String,
    val temp: Int
)