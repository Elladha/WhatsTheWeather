package com.example.geoff.whatstheweather.ui.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.geoff.whatstheweather.R
import com.example.geoff.whatstheweather.data.model.Detail
import com.example.geoff.whatstheweather.data.model.city.City
import com.example.geoff.whatstheweather.data.model.forecast.HourlyForecastDisplayed
import com.example.geoff.whatstheweather.data.network.APIService
import com.example.geoff.whatstheweather.data.network.ConnectivityInterceptorImpl
import com.example.geoff.whatstheweather.data.network.WeatherNetworkFetcher
import com.example.geoff.whatstheweather.data.network.WeatherNetworkFetcherImpl
import com.example.geoff.whatstheweather.data.provider.LocationProvider
import com.example.geoff.whatstheweather.data.provider.LocationProviderImpl
import com.example.geoff.whatstheweather.data.provider.UnitProviderImpl
import com.example.geoff.whatstheweather.di.GlideApp
import com.example.geoff.whatstheweather.inTransaction
import com.example.geoff.whatstheweather.ui.citieslist.ListCitiesActivity
import com.example.geoff.whatstheweather.ui.main.details.DetailsFragment
import com.example.geoff.whatstheweather.ui.main.forecast.HourlyWeatherFragment
import com.example.geoff.whatstheweather.ui.settings.SettingsActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private const val PERMISSION_CODE = 1
const val ADD_CITY_REQUEST = 2

class MainActivity : AppCompatActivity(), MainContract.View {


    private var city: City? = null
    private lateinit var mainPresenter: MainPresenter
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationProviderImpl: LocationProvider
    private lateinit var apiService: APIService
    private lateinit var weatherNetworkFetcher: WeatherNetworkFetcher
    private var detailsFragment: DetailsFragment? = null
    private var hourlyFragment: HourlyWeatherFragment? = null

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
        }
    }


    companion object {
        const val BASE_URL_ICON = "https://openweathermap.org/img/wn/"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        locationProviderImpl = LocationProviderImpl(fusedLocationProviderClient, this)
        apiService = APIService(ConnectivityInterceptorImpl(this))
        weatherNetworkFetcher = WeatherNetworkFetcherImpl(apiService)
        mainPresenter = MainPresenter(UnitProviderImpl(this), this)

        requestLocationPermission()
        if (hasLocationPermission()) {
            bindLocationManager()
        }


        weatherNetworkFetcher.downloadedCurrentWeather.observe(this, Observer {
            updateToolbar(it.cityName)
            updateTemperatures(
                it.weatherInfo.temp.toInt(),
                it.weatherInfo.tempMin.toInt(),
                it.weatherInfo.tempMax.toInt()
            )
            updateDescription(it.weather[0].description)
            updateImage(it.weather[0].icon)
            mainPresenter.updateDetails(this, it.sun, it.wind, it.weatherInfo)
        })

        weatherNetworkFetcher.downloadedForecastWeather.observe(this, Observer {
            mainPresenter.updateHourlyWeather(it.weatherList.subList(0, 8))
        })

        Log.d("Weather", "OnCreate")


    }

    override fun onResume() {
        super.onResume()
        Log.d("Weather", "OnResume")

        val unit = mainPresenter.chooseUnit("metric", "imperial")

        GlobalScope.launch {
            if (city != null) {
                weatherNetworkFetcher.fetchCurrentWeather(city!!.name, unit)
                weatherNetworkFetcher.fetchForecastWeather(city!!.name, unit)
            } else {

                val location = locationProviderImpl.getDeviceLocationString()
                val array = location.split(",")
                if (array.size > 1) {
                    weatherNetworkFetcher.fetchCurrentWeather(
                        array[0].toDouble(),
                        array[1].toDouble(),
                        unit
                    )
                    weatherNetworkFetcher.fetchForecastWeather(
                        array[0].toDouble(),
                        array[1].toDouble(),
                        unit
                    )
                } else {
                    weatherNetworkFetcher.fetchCurrentWeather(array[0], unit)
                    weatherNetworkFetcher.fetchForecastWeather(array[0], unit)
                }
            }
        }
    }


    override fun updateToolbar(location: String) {
        main_toolbar.title = location
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(Date())
        main_toolbar.subtitle = date.toString()
    }

    override fun updateTemperatures(currentTemp: Int, minTemp: Int, maxTemp: Int) {
        tvCurrentTemp.text = getString(R.string.text_temp, currentTemp)
        tvMinMaxTemp.text = getString(R.string.text_min_max, minTemp, maxTemp)

    }

    override fun updateDescription(description: String) {
        tvDescription.text = description
    }

    override fun updateImage(icon: String) {
        val uri = Uri.parse("$BASE_URL_ICON$icon@2x.png")
        GlideApp.with(this@MainActivity)
            .load(uri)
            .into(imgCurrentWeather)
    }

    override fun addDetailsFragment(details: List<Detail>) {
        if (detailsFragment == null) {
            detailsFragment = DetailsFragment(details)
            supportFragmentManager.inTransaction {
                add(R.id.details_fragment, detailsFragment!!)
            }
        } else {
            detailsFragment?.updateDetails(details)
        }
    }

    override fun addHourlyFragment(hourlyWeatherList: List<HourlyForecastDisplayed>) {
        if (hourlyFragment == null) {
            hourlyFragment = HourlyWeatherFragment((hourlyWeatherList))
            supportFragmentManager.inTransaction {
                add(R.id.hourlyList, hourlyFragment!!)
            }
        } else {
            hourlyFragment?.updateHourlyWeather(hourlyWeatherList)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_CITY_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                city = data?.getSerializableExtra("city") as City
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.action_settings -> {
                intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }

            R.id.action_add -> {
                intent = Intent(this, ListCitiesActivity::class.java)
                startActivityForResult(intent, ADD_CITY_REQUEST)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bindLocationManager()
            } else {
                Toast.makeText(this, "Veuillez ajouter une ville dans la liste", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun bindLocationManager() {
        LifecycleBoundLocationManager(
            this,
            fusedLocationProviderClient,
            locationCallback
        )
    }
}
