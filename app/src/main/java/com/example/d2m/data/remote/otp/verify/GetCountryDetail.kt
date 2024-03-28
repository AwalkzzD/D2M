package com.example.d2m.data.remote.otp.verify

import com.google.gson.annotations.SerializedName

data class GetCountryDetail(
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("country_name") val countryName: String,
    @SerializedName("id") val id: Int
)