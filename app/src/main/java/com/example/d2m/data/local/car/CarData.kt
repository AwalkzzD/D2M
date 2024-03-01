package com.example.d2m.data.local.car

import com.google.gson.annotations.SerializedName

data class CarData(
    @SerializedName("id") val id: Int,
    @SerializedName("owner_name") val ownerName: String,
    @SerializedName("vehicle_number") val vehicleNumber: String
)