package com.example.d2m.data.remote.otp.verify

import com.google.gson.annotations.SerializedName

data class DefaultAddress(
    @SerializedName("address_line_1") val addressLine1: String,
    @SerializedName("address_line_2") val addressLine2: String,
    @SerializedName("billing_email") val billingEmail: String,
    @SerializedName("city_area_id") val cityAreaId: Int,
    @SerializedName("city_id") val cityId: Int,
    @SerializedName("country_id") val countryId: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("get_city_area_detail") val getCityAreaDetail: GetCityAreaDetail,
    @SerializedName("get_city_detail") val getCityDetail: GetCityDetail,
    @SerializedName("get_country_detail") val getCountryDetail: GetCountryDetail,
    @SerializedName("get_state_detail") val getStateDetail: GetStateDetail,
    @SerializedName("id") val id: Int,
    @SerializedName("is_default") val isDefault: Int,
    @SerializedName("mobile_number") val mobileNumber: String,
    @SerializedName("pincode") val pincode: Int,
    @SerializedName("state_id") val stateId: Int,
    @SerializedName("status") val status: Int,
    @SerializedName("street") val street: String,
    @SerializedName("user_id") val userId: Int
)