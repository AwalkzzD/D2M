package com.example.d2m.data.models.otp.send

data class OtpResponse(
    val `data`: Data,
    val message: String,
    val success: Boolean
)