package com.example.d2m.data.local.home

import com.google.gson.annotations.SerializedName

data class WhatsNew(
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("created_by") val createdBy: Int,
    @SerializedName("deleted_at") val deletedAt: Any,
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("status") val status: Int,
    @SerializedName("title") val title: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("updated_by") val updatedBy: Int
)