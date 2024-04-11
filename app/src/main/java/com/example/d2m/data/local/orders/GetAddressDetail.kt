package com.example.d2m.data.local.orders

data class GetAddressDetail(
    val address_line_1: String,
    val address_line_2: String,
    val billing_email: String,
    val city_area_id: Int,
    val city_id: Int,
    val country_id: Int,
    val created_at: String,
    val created_by: Int,
    val deleted_at: Any,
    val full_name: String,
    val id: Int,
    val is_default: Int,
    val mobile_number: String,
    val pincode: Int,
    val same_as_billing: Int,
    val state_id: Int,
    val status: Int,
    val street: String,
    val updated_at: String,
    val updated_by: Int,
    val user_id: Int
)