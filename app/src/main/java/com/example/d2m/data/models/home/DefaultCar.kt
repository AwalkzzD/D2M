package com.example.d2m.data.models.home

data class DefaultCar(
    val brand_model: Any,
    val brand_name: String,
    val color: String,
    val fuel_type: String,
    val id: Int,
    val is_default: Int,
    val owner_name: String,
    val platform_type: String,
    val user_id: Int,
    val vehicle_number: String
)