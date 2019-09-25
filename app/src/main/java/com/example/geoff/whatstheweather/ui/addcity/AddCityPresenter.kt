package com.example.geoff.whatstheweather.ui.addcity

import com.example.geoff.whatstheweather.data.local.room.city.CityDao
import com.example.geoff.whatstheweather.data.local.room.city.CityRepository
import com.example.geoff.whatstheweather.data.model.city.City
import com.example.geoff.whatstheweather.data.network.WeatherNetworkFetcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddCityPresenter(
    private val addCityView: AddCityContract.View,
    dao: CityDao,
    private val weatherNetworkFetcher: WeatherNetworkFetcher
) : AddCityContract.Presenter {

    private val repository = CityRepository(dao)

    override fun onAddCity(city: City) {
        repository.addCity(city)
        addCityView.stop()
    }

    override fun search(name: String) {
        GlobalScope.launch {
            weatherNetworkFetcher.fetchCities(name)
        }
    }

    override fun start() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}