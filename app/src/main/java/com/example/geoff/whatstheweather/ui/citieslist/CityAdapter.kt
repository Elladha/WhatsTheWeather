package com.example.geoff.whatstheweather.ui.citieslist

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.geoff.whatstheweather.R
import com.example.geoff.whatstheweather.data.model.city.City
import com.example.geoff.whatstheweather.inflate
import kotlinx.android.synthetic.main.listcities_item_row.view.*

class CityAdapter(private var cities: List<City>, private val presenter: ListCitiesPresenter) : RecyclerView.Adapter<CityAdapter.CityHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val inflatedView = parent.inflate(R.layout.listcities_item_row, false)
        return CityHolder(inflatedView)
    }

    override fun getItemCount(): Int = cities.size

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        val itemCity = cities[position]
        Log.d("Weather", itemCity.name)
        holder.bindCity(itemCity)
    }

    internal fun setCities(cities: List<City>) {
        this.cities = cities
        notifyDataSetChanged()
    }

    inner class CityHolder(v : View) : RecyclerView.ViewHolder(v), View.OnClickListener, View.OnLongClickListener {
        private var view: View = v

        private var city: City? = null

        init {
            v.setOnClickListener(this)
            v.setOnLongClickListener(this)
        }

        override fun onLongClick(p0: View?): Boolean {
            city?.let {
                presenter.removeCity(it)
            }
            // has to be true to not call the onClick function
            return true
        }

        override fun onClick(p0: View?) {
            city?.let { presenter.onCityClick(it) }
        }

        fun bindCity(city: City) {
            this.city = city
            view.tvCityName.text = city.name + ", " + city.sys.country
            view.tvLat.text = city.coord.lat.toString()
            view.tvLon.text = city.coord.lon.toString()
        }

    }
}