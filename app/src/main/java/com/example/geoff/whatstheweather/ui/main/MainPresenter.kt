package com.example.geoff.whatstheweather.ui.main

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.geoff.whatstheweather.R
import com.example.geoff.whatstheweather.data.model.Detail
import com.example.geoff.whatstheweather.data.model.Sun
import com.example.geoff.whatstheweather.data.model.WeatherInfo
import com.example.geoff.whatstheweather.data.model.Wind
import com.example.geoff.whatstheweather.data.model.forecast.ForecastWeatherEntry
import com.example.geoff.whatstheweather.data.model.forecast.HourlyForecastDisplayed
import com.example.geoff.whatstheweather.data.provider.UnitProvider
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainPresenter(
    private val unitProvider: UnitProvider,
    private val mainView: MainContract.View
) : MainContract.Presenter {




    override fun updateDetails(context: Context, sun: Sun, wind: Wind, weatherInfo: WeatherInfo) {
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.FRANCE)
        val hourSunrise = Date(sun.sunrise * 1000)
        val hourSunset = Date(sun.sunset * 1000)
        val decimalFormat = DecimalFormat("#.##")
        decimalFormat.roundingMode = RoundingMode.CEILING
        val details: ArrayList<Detail> = ArrayList()
        details.add(
            Detail(
                ContextCompat.getDrawable(context, R.drawable.sunrise),
                "Sunrise",
                simpleDateFormat.format(hourSunrise)
            )
        )
        details.add(
            Detail(
                ContextCompat.getDrawable(context, R.drawable.sunset),
                "Sunset",
                simpleDateFormat.format(hourSunset)
            )
        )
        details.add(
            Detail(
                ContextCompat.getDrawable(context, R.drawable.wind),
                "Wind Speed",
                decimalFormat.format(windSpeed(wind.speed)) + chooseUnit(" kph", " mph")
            )
        )
        details.add(
            Detail(
                ContextCompat.getDrawable(context, R.drawable.humidity),
                "Humidity",
                weatherInfo.humidity.toString() + "%"
            )
        )
        details.add(
            Detail(
                ContextCompat.getDrawable(context, R.drawable.pressure),
                "Atmospheric Pressure",
                weatherInfo.pressure.toString() + " hPa"
            )
        )

        mainView.addDetailsFragment(details)
    }

    override fun updateHourlyWeather(listForecastWeatherEntry: List<ForecastWeatherEntry>) {
        val hourlyWeatherList = ArrayList<HourlyForecastDisplayed>()

        listForecastWeatherEntry.forEach {
            val url = MainActivity.BASE_URL_ICON + it.weather[0].icon + ".png"
            hourlyWeatherList.add(
                HourlyForecastDisplayed(
                    it.datetime,
                    url,
                    it.weatherInfo.temp.toInt()
                )
            )
        }

        mainView.addHourlyFragment(hourlyWeatherList)
    }

    override fun chooseUnit(metric: String, imperial: String): String {
        val isMetric = unitProvider.getUnit() == "METRIC"
        return if (isMetric) metric else imperial
    }

    fun windSpeed(baseSpeed: Float): Float {
        return if (unitProvider.getUnit() == "METRIC"){
            (baseSpeed * 3.6).toFloat()
        } else {
            baseSpeed
        }
    }
    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}