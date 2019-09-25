package com.example.geoff.whatstheweather.ui.addcity

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.geoff.whatstheweather.R
import com.example.geoff.whatstheweather.data.model.city.City
import com.example.geoff.whatstheweather.inflate
import kotlinx.android.synthetic.main.listcities_item_row.view.*

class CitySearchAdapter(private val cities: List<City>, private val presenter: AddCityPresenter) :
    RecyclerView.Adapter<CitySearchAdapter.CityHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CitySearchAdapter.CityHolder {
        val view = parent.inflate(R.layout.listcities_item_row, false)
        return CityHolder(view)
    }

    override fun getItemCount(): Int = cities.size

    override fun onBindViewHolder(holder: CitySearchAdapter.CityHolder, position: Int) {
        val itemCity = cities[position]
        holder.bindCity(itemCity)
    }

    inner class CityHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var view = v
        private var city: City? = null

        init {
            v.setOnClickListener(this)
        }


        override fun onClick(p0: View?) {
            Log.d("Weather", "CLICK")
            city?.let {
                presenter.onAddCity(it)
            }
        }

        fun bindCity(city: City) {
            this.city = city
            view.tvCityName.text = city.name + ", " + city.sys.country
            view.tvLat.text = city.coord.lat.toString()
            view.tvLon.text = city.coord.lon.toString()
        }


    }

}