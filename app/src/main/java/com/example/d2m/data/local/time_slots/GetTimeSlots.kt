package com.example.d2m.data.local.time_slots

import com.google.gson.annotations.SerializedName

data class GetTimeSlots(
    @SerializedName("data") val timeSlots: List<TimeSlots>? = null,
    @SerializedName("message") val message: String,
    @SerializedName("success") val success: Boolean
)