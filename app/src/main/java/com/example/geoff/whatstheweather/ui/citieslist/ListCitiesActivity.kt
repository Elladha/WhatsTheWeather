package com.example.geoff.whatstheweather.ui.citieslist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.geoff.whatstheweather.R
import com.example.geoff.whatstheweather.data.local.room.city.CityDatabase
import com.example.geoff.whatstheweather.data.model.city.City
import com.example.geoff.whatstheweather.ui.addcity.AddCityActivity
import kotlinx.android.synthetic.main.activity_list_cities.*
import kotlinx.android.synthetic.main.content_list_cities_activity.*

class ListCitiesActivity : AppCompatActivity(), ListCitiesContract.View {

    private lateinit var listCitiesPresenter: ListCitiesPresenter
    private lateinit var adapter: CityAdapter
    private lateinit var citiesList: List<City>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_cities)
        setSupportActionBar(toolbar)
        init()
        setupUI()
    }

    private fun init() {
        listCitiesPresenter = ListCitiesPresenter(this, CityDatabase.getDatabase(this).cityDao())
        listCitiesPresenter.getCities().observe(this@ListCitiesActivity, Observer { cities ->
            cities.let {
                adapter.setCities(it)
            }
        })
        fab.setOnClickListener {
            listCitiesPresenter.onAddCityClick()
        }
        citiesList = listCitiesPresenter.getCities().value ?: ArrayList()
    }

    private fun setupUI() {
        val linearLayoutManager = LinearLayoutManager(this)
        listCities.layoutManager = linearLayoutManager
        adapter = CityAdapter(citiesList, listCitiesPresenter)
        listCities.adapter = adapter

        val dividerItemDecoration =
            DividerItemDecoration(listCities.context, linearLayoutManager.orientation)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.line_divider
            )!!
        )
        listCities.addItemDecoration(dividerItemDecoration)
    }

    override fun updateListCities() {
        listCities.adapter?.notifyDataSetChanged()
    }

    override fun navigateToAddCity() {
        intent = Intent(applicationContext, AddCityActivity::class.java)
        startActivity(intent)
    }

    override fun returnIntent(city: City) {
        val intent = Intent()
        intent.putExtra("city", city)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
