package com.example.d2m.data.local.checkout

import androidx.databinding.ObservableBoolean

data class TimeSlots(
    val time_slot: String,
    val isSelected: ObservableBoolean = ObservableBoolean(false)
)