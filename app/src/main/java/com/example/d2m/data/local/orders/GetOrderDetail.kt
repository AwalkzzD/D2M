package com.example.d2m.data.local.orders

data class GetOrderDetail(
    val created_at: String,
    val deleted_at: Any,
    val details: String,
    val get_service: GetService,
    val get_service_category: GetServiceCategory,
    val get_service_type: GetServiceType,
    val id: Int,
    val is_add_on: Int,
    val is_sent_service_reminder: Int,
    val order_id: Int,
    val service_category_id: Int,
    val service_id: Int,
    val service_type_id: Int,
    val updated_at: String
)