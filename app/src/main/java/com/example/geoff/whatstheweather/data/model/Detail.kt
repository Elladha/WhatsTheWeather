package com.example.geoff.whatstheweather.data.model

import android.graphics.drawable.Drawable

data class Detail(
    val img: Drawable?,
    val detailTitle: String,
    val detailValue: Any
)