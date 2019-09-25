package com.example.geoff.whatstheweather.ui.citieslist

import androidx.lifecycle.LiveData
import com.example.geoff.whatstheweather.data.model.city.City
import com.example.geoff.whatstheweather.ui.BasePresenter
import com.example.geoff.whatstheweather.ui.BaseView

interface ListCitiesContract {

    interface View: BaseView {
        fun updateListCities()
        fun navigateToAddCity()
        fun returnIntent(city: City)
    }

    interface Presenter: BasePresenter {
        fun getCities(): LiveData<List<City>>
        fun onAddCityClick()
        fun onCityClick(city: City)
        fun removeCity(city: City)
    }
}