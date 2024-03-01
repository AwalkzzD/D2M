package com.example.d2m.data.local.car

import com.google.gson.annotations.SerializedName

data class CarAdded(
    @SerializedName("data") val carData: CarData? = null,
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: Boolean
)