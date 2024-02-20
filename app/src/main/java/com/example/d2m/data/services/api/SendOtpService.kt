package com.example.d2m.data.services.api

import com.example.d2m.data.models.otp.send.OtpResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface SendOtpService {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("api/v1/user/send-otp")
    fun sendOtp(
        @Field("phone") userPhoneNum: String,
        @Field("whatsapp_updates") whatsappUpdates: String
    ): Call<OtpResponse>
}