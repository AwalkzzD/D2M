package com.example.d2m.data.models.home

data class ServiceX(
    val additional: String,
    val description: String,
    val display_discount: Double,
    val free_pickup_drop: Int,
    val id: Int,
    val image: String,
    val is_25_point_inspection_free: Int,
    val price: Double,
    val service_category_id: Int,
    val service_required_kilometer: Int,
    val service_required_time_month: Int,
    val service_title: String,
    val service_type: String,
    val service_type_name: String,
    val service_warrenty_in_kilometer: Int,
    val service_warrenty_time_in_month: Int,
    val time_taken: String
)