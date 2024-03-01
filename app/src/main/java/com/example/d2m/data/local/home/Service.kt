package com.example.d2m.data.local.home

import com.google.gson.annotations.SerializedName

data class Service(
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("service_category_name") val serviceCategoryName: String,
    @SerializedName("services") val services: List<ServiceX>
)