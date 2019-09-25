package com.example.geoff.whatstheweather.data.model.city

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.geoff.whatstheweather.data.model.Coord
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "city")
data class City(
    @PrimaryKey @NonNull
    val id: Int,
    val name: String,
    @Embedded
    val sys: Sys,
    @Embedded
    val coord: Coord
    ) : Serializable

data class Sys(
    val country: String
) : Serializable
