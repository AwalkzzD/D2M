package com.example.d2m.data.remote.otp.request

import com.google.gson.annotations.SerializedName

data class SendOtpResponse(
    @SerializedName("data") val sendOtpResponseData: SendOtpResponseData? = null,
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: Boolean
)