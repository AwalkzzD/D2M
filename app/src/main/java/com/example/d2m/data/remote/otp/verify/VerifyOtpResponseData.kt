package com.example.d2m.data.remote.otp.verify

import com.google.gson.annotations.SerializedName

data class VerifyOtpResponseData(
    @SerializedName("default_address") val defaultAddress: DefaultAddress?,
    @SerializedName("default_car") val defaultCar: DefaultCar,
    @SerializedName("email") val email: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("id") val id: Int,
    @SerializedName("is_subscribed_user") val isSubscribedUser: Int,
    @SerializedName("phone") val phone: String,
    @SerializedName("subscribe_plan_description") val subscribePlanDescription: String?,
    @SerializedName("subscribe_plan_id") val subscribePlanId: Int?,
    @SerializedName("subscribe_plan_name") val subscribePlanName: String?,
    @SerializedName("subscribe_plan_purchase_date") val subscribePlanPurchaseDate: String?,
    @SerializedName("token") val token: String,
    @SerializedName("total_cars") val totalCars: Int
)