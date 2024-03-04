package com.example.d2m.screens.login.phone_otp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.d2m.data.remote.otp.request.SendOtpResponse
import com.example.d2m.data.remote.otp.verify.VerifyOtpResponse
import com.example.d2m.network_utils.ApiClient
import com.example.d2m.network_utils.api_services.SendOtpService
import com.example.d2m.network_utils.api_services.VerifyOtpService
import com.google.gson.GsonBuilder

class OtpViewModel : ViewModel() {

    var otpSendLiveData: MutableLiveData<SendOtpResponse> = MutableLiveData<SendOtpResponse>()
    var verifyOtpResponseLiveData: MutableLiveData<VerifyOtpResponse> =
        MutableLiveData<VerifyOtpResponse>()

    fun sendOtp(userPhoneNumber: String?, getWhatsappUpdates: String?) {
        val retrofitInstance = ApiClient.createService(SendOtpService::class.java) as SendOtpService

        if (userPhoneNumber != null && getWhatsappUpdates != null) {

            val retrofitData = retrofitInstance.sendOtp(userPhoneNumber, getWhatsappUpdates)

            retrofitData.enqueue(object : retrofit2.Callback<SendOtpResponse?> {
                override fun onResponse(
                    call: retrofit2.Call<SendOtpResponse?>,
                    response: retrofit2.Response<SendOtpResponse?>
                ) {

                    if (response.body()?.success == true) {
                        otpSendLiveData.postValue(response.body())
                    }

                }

                override fun onFailure(call: retrofit2.Call<SendOtpResponse?>, t: Throwable) {
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

            retrofitData.enqueue(object : retrofit2.Callback<VerifyOtpResponse?> {
                override fun onResponse(
                    call: retrofit2.Call<VerifyOtpResponse?>,
                    response: retrofit2.Response<VerifyOtpResponse?>
                ) {

                    if (response.body()?.success == true) {
                        verifyOtpResponseLiveData.postValue(response.body())
                    }

                }

                override fun onFailure(call: retrofit2.Call<VerifyOtpResponse?>, t: Throwable) {
                    Log.d("TAG", "onFailure: Failed to verify OTP -> $t")
                }
            })
            ApiClient.destroyInstance()
        }

    }
}