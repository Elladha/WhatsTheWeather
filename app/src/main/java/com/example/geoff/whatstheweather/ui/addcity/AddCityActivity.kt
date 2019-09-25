package com.example.geoff.whatstheweather.ui.addcity

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geoff.whatstheweather.R
import com.example.geoff.whatstheweather.data.local.room.city.CityDatabase
import com.example.geoff.whatstheweather.data.model.city.City
import com.example.geoff.whatstheweather.data.network.APIService
import com.example.geoff.whatstheweather.data.network.ConnectivityInterceptorImpl
import com.example.geoff.whatstheweather.data.network.WeatherNetworkFetcherImpl

import kotlinx.android.synthetic.main.activity_add_city.*
import kotlinx.android.synthetic.main.content_add_city.*

class AddCityActivity : AppCompatActivity(), AddCityContract.View {
    private lateinit var addCityPresenter : AddCityPresenter
    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var adapter: CitySearchAdapter
    private lateinit var cities : ArrayList<City>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)
        setSupportActionBar(toolbar)
        init()
    }

    private fun init() {
        val apiService = APIService(ConnectivityInterceptorImpl(this))
        val weatherNetworkFetcher = WeatherNetworkFetcherImpl(apiService)

        weatherNetworkFetcher.downloadedCities.observe(this, Observer {
            updateListCities(it.citiesFound)
        })

        addCityPresenter = AddCityPresenter(this, CityDatabase.getDatabase(this).cityDao(), weatherNetworkFetcher)
        cities = ArrayList()
        linearLayoutManager = LinearLayoutManager(this)
        citiesSearched.layoutManager = linearLayoutManager
        adapter = CitySearchAdapter(cities, addCityPresenter)
        citiesSearched.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(citiesSearched.context, linearLayoutManager.orientation)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.line_divider)!!)
        citiesSearched.addItemDecoration(dividerItemDecoration)

        tvSearchCity.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                addCityPresenter.search(v.text.toString())
                true
            } else {
                false
            }
        }
    }



    override fun stop() {
        this.finish()
    }

    override fun updateListCities(citiesFound: List<City>) {
        cities.clear()
        cities.addAll(citiesFound)
        citiesSearched.adapter?.notifyDataSetChanged()

    }
}
