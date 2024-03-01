package com.example.d2m.data.local.home

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("banner_name") val bannerName: String,
    @SerializedName("banner_url") val bannerUrl: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("created_by") val createdBy: Int,
    @SerializedName("deleted_at") val deletedAt: Any,
    @SerializedName("id") val id: Int,
    @SerializedName("redirection_url") val redirectionUrl: String,
    @SerializedName("status") val status: Int,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("updated_by") val updatedBy: Int
)