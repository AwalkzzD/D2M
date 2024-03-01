package com.example.d2m.data.local.home

import com.google.gson.annotations.SerializedName

data class UserHome(
    @SerializedName("data") val userData: UserData? = null,
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: Boolean
)