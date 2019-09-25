package com.example.geoff.whatstheweather.ui.citieslist

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.geoff.whatstheweather.data.local.room.city.CityDao
import com.example.geoff.whatstheweather.data.model.city.City
import com.example.geoff.whatstheweather.data.local.room.city.CityRepository

class ListCitiesPresenter(private val listCitiesView: ListCitiesContract.View, dao: CityDao) : ListCitiesContract.Presenter {
    private val repository =
        CityRepository(dao)

    override fun getCities(): LiveData<List<City>> {
        return repository.getAllCities()
    }

    override fun onAddCityClick() {
        listCitiesView.navigateToAddCity()
    }

    override fun start() {
        TODO("not implemented")

    }

    override fun stop() {
        TODO("not implemented")
    }

    override fun onCityClick(city: City) {
        Log.d("Weather", city.name)
        listCitiesView.returnIntent(city)
    }

    override fun removeCity(city: City) {
        repository.deleteCity(city)
    }
}