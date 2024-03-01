package com.example.d2m.data.remote.otp.verify

import com.google.gson.annotations.SerializedName

data class VerifyOtpResponseData(
    @SerializedName("email") val email: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("id") val id: Int,
    @SerializedName("is_subscribed_user") val isSubscribedUser: Int,
    @SerializedName("phone") val phone: String,
    @SerializedName("subscribe_plan_description") val subscribePlanDescription: Any,
    @SerializedName("subscribe_plan_id") val subscribePlanId: Any,
    @SerializedName("subscribe_plan_name") val subscribePlanName: Any,
    @SerializedName("subscribe_plan_purchase_date") val subscribePlanPurchaseDate: Any,
    @SerializedName("token") val token: String,
    @SerializedName("total_cars") val totalCars: Int
)