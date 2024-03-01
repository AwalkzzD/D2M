package com.example.d2m.data.remote.otp.verify

import com.google.gson.annotations.SerializedName

data class VerifyOtpResponse(
    @SerializedName("data") val verifyOtpResponseData: VerifyOtpResponseData? = null,
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: Boolean
)