package com.example.d2m.data.local.orders

data class DataX(
    val address: String,
    val address_id: Int,
    val booking_amount: Double,
    val booking_date_time: String,
    val city_id: Int,
    val completed_date_time: String,
    val created_at: String,
    val discount: Int,
    val get_address_detail: GetAddressDetail,
    val get_city_detail: GetCityDetail,
    val get_order_details: List<GetOrderDetail>,
    val get_service_provider_detail: GetServiceProviderDetail,
    val get_slot_detail: GetSlotDetail,
    val get_status_name: GetStatusName,
    val get_technician_detail: GetTechnicianDetail,
    val get_user_detail: GetUserDetail,
    val get_vehicle_detail: GetVehicleDetail,
    val get_zone_detail: Any,
    val id: Int,
    val invoice_file: String,
    val is_car_picked_up: Int,
    val is_order_type: Int,
    val is_payment_done: Int,
    val order_payload: String,
    val payment_id: Int,
    val pincode: String,
    val promo_amount: Double,
    val promocode_id: Int,
    val promocode_name: Any,
    val remarks: Any,
    val service_provider_id: Int,
    val slot_id: Int,
    val status: Int,
    val technician_id: Int,
    val unique_order_id: String,
    val updated_at: String,
    val user_id: Int,
    val vehicle_id: Int,
    val zone_id: Any
)