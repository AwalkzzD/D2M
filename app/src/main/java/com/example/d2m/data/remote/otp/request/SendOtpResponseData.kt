package com.example.d2m.data.remote.otp.request

import com.google.gson.annotations.SerializedName

data class SendOtpResponseData(
    @SerializedName("mobile_number") val mobileNumber: String,
    @SerializedName("otp") val otp: Int
)