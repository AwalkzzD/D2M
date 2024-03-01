package com.example.d2m.data.local.home

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("banners") val banners: List<Banner>,
    @SerializedName("default_car") val defaultCar: DefaultCar,
    @SerializedName("is_subscribed_user") val isSubscribedUser: Int,
    @SerializedName("ratings") val ratings: List<Any>,
    @SerializedName("services") val services: List<Service>,
    @SerializedName("subscribe_plan_description") val subscribePlanDescription: Any,
    @SerializedName("subscribe_plan_id") val subscribePlanId: Any,
    @SerializedName("subscribe_plan_name") val subscribePlanName: Any,
    @SerializedName("subscribe_plan_purchase_date") val subscribePlanPurchaseDate: Any,
    @SerializedName("video_url") val videoUrl: String,
    @SerializedName("whatsnew") val whatsNewList: List<WhatsNew>
)