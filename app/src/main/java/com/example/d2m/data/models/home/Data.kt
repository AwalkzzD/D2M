package com.example.d2m.data.models.home

data class Data(
    val banners: List<Banner>,
    val default_car: DefaultCar,
    val is_subscribed_user: Int,
    val ratings: List<Any>,
    val services: List<Service>,
    val subscribe_plan_description: Any,
    val subscribe_plan_id: Any,
    val subscribe_plan_name: Any,
    val subscribe_plan_purchase_date: Any,
    val video_url: String,
    val whatsnew: List<Whatsnew>
)