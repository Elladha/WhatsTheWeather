package com.example.geoff.whatstheweather.ui.addcity

import com.example.geoff.whatstheweather.data.model.city.City
import com.example.geoff.whatstheweather.ui.BasePresenter
import com.example.geoff.whatstheweather.ui.BaseView

interface AddCityContract {

    interface View : BaseView {
        fun stop()
        fun updateListCities(citiesFound: List<City>)
    }

    interface Presenter : BasePresenter {
        fun onAddCity(city: City)
        fun search(name: String)
    }
}