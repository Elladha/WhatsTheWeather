package com.example.geoff.whatstheweather.data.local.room.weather

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.geoff.whatstheweather.data.local.room.weather.CurrentWeatherDao
import com.example.geoff.whatstheweather.data.model.current.CurrentWeatherEntry

@Database(entities = [CurrentWeatherEntry::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
}