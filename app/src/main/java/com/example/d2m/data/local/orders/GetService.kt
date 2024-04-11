package com.example.d2m.data.local.orders

data class GetService(
    val description: String,
    val id: Int,
    val price: Double,
    val service_category_id: Int,
    val service_title: String,
    val service_type: String
)