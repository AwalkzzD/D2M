package com.example.d2m.data.local.orders

data class GetSlotDetail(
    val id: Int,
    val slot_end_time: String,
    val slot_start_time: String
)