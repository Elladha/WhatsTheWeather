package com.example.geoff.whatstheweather.data.model.city

import com.google.gson.annotations.SerializedName

data class FindCityResponse (
    val count: Int,
    @SerializedName("list")
    val citiesFound: List<City>
)