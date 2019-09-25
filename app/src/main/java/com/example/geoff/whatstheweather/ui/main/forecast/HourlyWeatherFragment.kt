package com.example.geoff.whatstheweather.ui.main.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.geoff.whatstheweather.R
import com.example.geoff.whatstheweather.data.model.forecast.HourlyForecastDisplayed
import com.example.geoff.whatstheweather.ui.main.forecast.dummy.DummyContent
import kotlinx.android.synthetic.main.fragment_hourlyweather_list.*

/**
 * A fragment representing a list of Items.
 */
class HourlyWeatherFragment(private val hourlyWeatherList: List<HourlyForecastDisplayed>): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hourlyweather_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = HourlyWeatherRecyclerViewAdapter(hourlyWeatherList, Glide.with(this@HourlyWeatherFragment))
            }
        }
        return view
    }

    fun updateHourlyWeather(hourlyWeatherList: List<HourlyForecastDisplayed>) {
        (this.hourlyWeatherList as ArrayList).clear()
        this.hourlyWeatherList.addAll(hourlyWeatherList)
        this.hourly_weatherList.adapter?.notifyDataSetChanged()
    }
}
