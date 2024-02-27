package com.example.d2m.screens.login.phone_otp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.d2m.data.models.otp.send.OtpResponse
import com.example.d2m.data.models.otp.verify.OtpVerify
import com.example.d2m.data.services.api.ApiClient
import com.example.d2m.data.services.api.SendOtpService
import com.example.d2m.data.services.api.VerifyOtpService
import com.google.gson.GsonBuilder

class OtpViewModel : ViewModel() {
    var otpSendLiveData: MutableLiveData<OtpResponse> = MutableLiveData<OtpResponse>()
    var otpVerifyLiveData: MutableLiveData<OtpVerify> = MutableLiveData<OtpVerify>()
    private val TAG = "OtpViewModel"

    fun sendOtp(userPhoneNumber: String?, getWhatsappUpdates: String?) {
        val retrofitInstance = ApiClient.createService(SendOtpService::class.java) as SendOtpService
        Log.d(TAG, "sendOtp: 1")
        if (userPhoneNumber != null && getWhatsappUpdates != null) {
            Log.d(TAG, "sendOtp: 2")
            val retrofitData = retrofitInstance.sendOtp(userPhoneNumber, getWhatsappUpdates)

            retrofitData.enqueue(object : retrofit2.Callback<OtpResponse?> {
                override fun onResponse(
                    call: retrofit2.Call<OtpResponse?>, response: retrofit2.Response<OtpResponse?>
                ) {
                    Log.d(TAG, "sendOtp: 3")
                    if (response.body()?.success == true) {
                        Log.d(TAG, "sendOtp: 4")
                        otpSendLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: retrofit2.Call<OtpResponse?>, t: Throwable) {
                    Log.d("TAG", "onFailure: Failed to send OTP -> $t")
                }
            })
            ApiClient.destroyInstance()
        }
    }

    fun verifyOtp(userPhoneNumber: String?, otpCode: String) {
        val retrofitInstance =
            ApiClient.createService(VerifyOtpService::class.java) as VerifyOtpService

        if (userPhoneNumber != null) {
            val retrofitData = retrofitInstance.verifyOtp(
                userPhoneNumber, otpCode, "123ASDFSFFSAFSSSSS", "android"
            )

            retrofitData.enqueue(object : retrofit2.Callback<OtpVerify?> {
                override fun onResponse(
                    call: retrofit2.Call<OtpVerify?>, response: retrofit2.Response<OtpVerify?>
                ) {
                    if (response.body()?.success == true) {
                        otpVerifyLiveData.postValue(response.body())
                    }
                    Log.d(
                        "TAG",
                        "onResponse: " + GsonBuilder().setPrettyPrinting().create()
                            .toJson(response.body())
                    )
                }

                override fun onFailure(call: retrofit2.Call<OtpVerify?>, t: Throwable) {
                    Log.d("TAG", "onFailure: Failed to verify OTP -> $t")
                }
            })
            ApiClient.destroyInstance()
        }
    }
}