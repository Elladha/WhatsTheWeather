package com.example.geoff.whatstheweather.ui.main

import android.content.Context
import com.example.geoff.whatstheweather.data.model.Detail
import com.example.geoff.whatstheweather.data.model.Sun
import com.example.geoff.whatstheweather.data.model.WeatherInfo
import com.example.geoff.whatstheweather.data.model.Wind
import com.example.geoff.whatstheweather.data.model.city.City
import com.example.geoff.whatstheweather.data.model.forecast.ForecastWeatherEntry
import com.example.geoff.whatstheweather.data.model.forecast.HourlyForecastDisplayed
import com.example.geoff.whatstheweather.ui.BasePresenter
import com.example.geoff.whatstheweather.ui.BaseView

interface MainContract {

    interface View : BaseView {
        fun addDetailsFragment(details: List<Detail>)
        fun addHourlyFragment(hourlyWeatherList: List<HourlyForecastDisplayed>)
        fun updateToolbar(location: String)
        fun updateTemperatures(currentTemp: Int, minTemp: Int, maxTemp: Int)
        fun updateDescription(description: String)
        fun updateImage(icon: String)
     }

    interface Presenter : BasePresenter {
        fun updateDetails(context: Context, sun: Sun, wind: Wind, weatherInfo: WeatherInfo)
        fun updateHourlyWeather(listForecastWeatherEntry: List<ForecastWeatherEntry>)
        fun chooseUnit(metric: String, imperial: String): String
    }
}