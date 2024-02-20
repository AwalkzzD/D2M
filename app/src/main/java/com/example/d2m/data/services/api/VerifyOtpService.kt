package com.example.d2m.data.services.api

import com.example.d2m.data.models.otp.verify.OtpVerify
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface VerifyOtpService {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("api/v1/user/verify-otp")
    fun verifyOtp(
        @Field("phone") userPhoneNum: String,
        @Field("otp") otpCode: String,
        @Field("device_token") deviceToken: String,
        @Field("device_type") deviceType: String,
    ): Call<OtpVerify>
}