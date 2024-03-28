package com.example.d2m.data.remote.otp.verify

import com.google.gson.annotations.SerializedName

data class GetCityAreaDetail(
    @SerializedName("area_name") val areaName: String,
    @SerializedName("id") val id: Int,
    @SerializedName("pincode") val pincode: Int
)