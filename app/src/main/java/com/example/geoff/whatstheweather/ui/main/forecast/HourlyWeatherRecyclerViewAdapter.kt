package com.example.geoff.whatstheweather.ui.main.forecast


import android.content.Context
import android.net.Uri
import android.provider.Settings.Global.getString
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.geoff.whatstheweather.R
import com.example.geoff.whatstheweather.data.model.forecast.HourlyForecastDisplayed
import com.example.geoff.whatstheweather.inflate
import kotlinx.android.synthetic.main.fragment_hourlyweather.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [HourlyForecastDisplayed]
 */
class HourlyWeatherRecyclerViewAdapter(
    private val mValues: List<HourlyForecastDisplayed>,
    private val glide: RequestManager
) : RecyclerView.Adapter<HourlyWeatherRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.fragment_hourlyweather, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        val sdf = SimpleDateFormat("HH:mm", Locale.FRANCE)
        val date = Date(item.datetime * 1000)

        holder.tvHour.text = sdf.format(date)
        holder.tvTemp.text =  holder.context.getString(R.string.text_temp, item.temp)
        glide.load(Uri.parse(item.url)).into(holder.img)

        with(holder.mView) {
            tag = item
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val tvHour: TextView = mView.tvHour
        val img: ImageView = mView.imgHourlyWeather
        val tvTemp: TextView = mView.tvHourlyTemp
        val context: Context = mView.context

        override fun toString(): String {
            return super.toString() + " '" + tvTemp.text + "'"
        }
    }
}
