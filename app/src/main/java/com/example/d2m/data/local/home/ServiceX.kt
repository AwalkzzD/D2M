package com.example.d2m.data.local.home

import com.google.gson.annotations.SerializedName

data class ServiceX(
    @SerializedName("additional") val additional: String,
    @SerializedName("description") val description: String,
    @SerializedName("display_discount") val displayDiscount: Double,
    @SerializedName("free_pickup_drop") val freePickupDrop: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("is_25_point_inspection_free") val is25PointInspectionFree: Int,
    @SerializedName("price") val price: Double,
    @SerializedName("service_category_id") val serviceCategoryId: Int,
    @SerializedName("service_required_kilometer") val serviceRequiredKilometer: Int,
    @SerializedName("service_required_time_month") val serviceRequiredTimeMonth: Int,
    @SerializedName("service_title") val serviceTitle: String,
    @SerializedName("service_type") val serviceType: String,
    @SerializedName("service_type_name") val serviceTypeName: String,
    @SerializedName("service_warrenty_in_kilometer") val serviceWarrantyInKilometer: Int,
    @SerializedName("service_warrenty_time_in_month") val serviceWarrantyTimeInMonth: Int,
    @SerializedName("time_taken") val timeTaken: String
)