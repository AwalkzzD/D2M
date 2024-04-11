package com.example.d2m.data.local.orders

data class GetVehicleDetail(
    val brand_model: String,
    val brand_name: String,
    val color: String,
    val fuel_type: String,
    val id: Int,
    val image_name: Any,
    val is_default: Int,
    val owner_name: Any,
    val platform_type: String,
    val user_id: Int,
    val vehicle_number: String
)