package com.example.geoff.whatstheweather.data.local.room.city

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.geoff.whatstheweather.data.model.city.City
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CityRepository(private val cityDao: CityDao) :
    ICityRepository {

    private var allCities: LiveData<List<City>> = cityDao.getAllCities()

    override fun addCity(city: City) {
        GlobalScope.launch {
            cityDao.insertCity(city)
        }
//        cityDao.insertCity(city)
    }

    override fun deleteCity(city: City) {
        GlobalScope.launch {
            cityDao.deleteCity(city)
        }
//        DeleteAsyncTask(cityDao).execute(city)
    }

    override fun deleteAllCities() {
        val cityArray = allCities.value?.toTypedArray()
        if (cityArray != null) {
            /*
            The * operator is known as the Spread Operator in Kotlin.
            When we call a vararg-function, we can pass arguments one-by-one, e.g. asList(1, 2, 3), or,
            if we already have an array and want to pass its contents to the function, we use the spread operator (prefix the array with *):
             */
            DeleteAsyncTask(
                cityDao
            ).execute(*cityArray)
        }
    }

    override fun getAllCities(): LiveData<List<City>> = allCities

//
//    inner class GetCitiesAsyncTask internal constructor(private val dao: CityDao) : AsyncTask<Void, Void, List<City>>() {
//        override fun doInBackground(vararg p0: Void?): List<City> {
//             return dao.getAllCities()
//        }
//
//        override fun onPostExecute(result: List<City>?) {
//            if (result != null) {
//                updateCities(result)
//            }
//        }
//
//    }

    private class InsertAsyncTask internal constructor(private val dao: CityDao) : AsyncTask<City, String, String>() {
        override fun doInBackground(vararg params: City): String {
            dao.insertCity(params[0])
            return "City ${params[0]} successfully added!"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result != null)
                Log.d("Weather", result)
        }
    }

    private class DeleteAsyncTask internal constructor(private val dao: CityDao) : AsyncTask<City, String, String>() {
        override fun doInBackground(vararg params: City): String {
            return if (params.size == 1) {
                dao.deleteCity(params[0])
                "City ${params[0]} successfully deleted!"
            } else {
                dao.deleteAllCities(*params)
                "All cities have been successfully deleted!"
            }
        }
    }

    private fun updateCities(cities: List<City>) {
//        allCities.addAll(cities)
    }

}