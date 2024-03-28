package com.example.d2m.data.remote.otp.verify

import com.google.gson.annotations.SerializedName

data class GetStateDetail(
    @SerializedName("country_id") val countryId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("state_name") val stateName: String
)