package com.example.geoff.whatstheweather.data.local.room.city

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.geoff.whatstheweather.data.model.city.City

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: City)

    @Delete
    fun deleteAllCities(vararg city: City)

    @Delete
    fun deleteCity(city: City)

    @Query("SELECT * FROM city order by name asc")
    fun getAllCities(): LiveData<List<City>>
}