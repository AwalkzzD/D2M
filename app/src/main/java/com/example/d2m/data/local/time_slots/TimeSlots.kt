package com.example.d2m.data.local.time_slots

import androidx.databinding.ObservableBoolean
import com.google.gson.annotations.SerializedName

data class TimeSlots(
    @SerializedName("id") val id: Int,
    @SerializedName("time_slot") val timeSlot: String,
    var isSelected: ObservableBoolean
)