package com.example.geoff.whatstheweather.data.local.room.weather

import androidx.room.Dao

@Dao
interface CurrentWeatherDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(weatherEntry: CurrentWeatherEntry)
//
//    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
//    fun getWeatherMetric(): LiveData<MetricCurrentWeather>
//
//    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
//    fun getWeatherImperial(): LiveData<ImperialCurrentWeather>
}