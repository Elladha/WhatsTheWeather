package com.example.geoff.whatstheweather.data.local.room.city

import androidx.lifecycle.LiveData
import com.example.geoff.whatstheweather.data.model.city.City

interface ICityRepository {
    fun addCity(city: City)
    fun deleteCity(city: City)
    fun deleteAllCities()
    fun getAllCities(): LiveData<List<City>>
}