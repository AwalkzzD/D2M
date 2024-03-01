package com.example.d2m.data.local.home

import com.google.gson.annotations.SerializedName

data class DefaultCar(
    @SerializedName("brand_model") val brandModel: Any,
    @SerializedName("brand_name") val brandName: String,
    @SerializedName("color") val color: String,
    @SerializedName("fuel_type") val fuelType: String,
    @SerializedName("id") val id: Int,
    @SerializedName("is_default") val isDefault: Int,
    @SerializedName("owner_name") val ownerName: String,
    @SerializedName("platform_type") val platformType: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("vehicle_number") val vehicleNumber: String
)