package com.example.d2m.data.remote.otp.verify

import com.google.gson.annotations.SerializedName

data class DefaultCar(
    @SerializedName("brand_model") val brandModel: String,
    @SerializedName("brand_name") val brandName: String,
    @SerializedName("color") val color: String,
    @SerializedName("fuel_type") val fuelType: String,
    @SerializedName("id") val id: Int,
    @SerializedName("image_name") val imageName: Any,
    @SerializedName("is_default") val isDefault: Int,
    @SerializedName("owner_name") val ownerName: Any,
    @SerializedName("platform_type") val platformType: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("vehicle_number") val vehicleNumber: String
)