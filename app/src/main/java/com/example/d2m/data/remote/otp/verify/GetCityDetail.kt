package com.example.d2m.data.remote.otp.verify

import com.google.gson.annotations.SerializedName

data class GetCityDetail(
    @SerializedName("city_name") val cityName: String,
    @SerializedName("country_id") val countryId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("state_id") val stateId: Int
)