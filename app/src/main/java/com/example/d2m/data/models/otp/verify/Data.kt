package com.example.d2m.data.models.otp.verify

data class Data(
    val email: String,
    val full_name: String,
    val id: Int,
    val is_subscribed_user: Int,
    val phone: String,
    val subscribe_plan_description: Any,
    val subscribe_plan_id: Any,
    val subscribe_plan_name: Any,
    val subscribe_plan_purchase_date: Any,
    val token: String,
    val total_cars: Int
)