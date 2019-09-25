package com.example.geoff.whatstheweather.data.provider

import android.content.Context
import android.util.Log

const val UNIT_SYSTEM = "UNIT"

class UnitProviderImpl(context: Context) : PreferenceProvider(context), UnitProvider {

    override fun getUnit(): String {
        val selectedUnit = preferences.getString(UNIT_SYSTEM, "Metric")
        Log.d("Weather", selectedUnit!!)
        return selectedUnit!!
    }
}