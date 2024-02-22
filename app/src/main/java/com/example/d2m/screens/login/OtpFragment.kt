package com.example.d2m.screens.login

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.d2m.data.models.otp.send.OtpResponse
import com.example.d2m.data.models.otp.verify.OtpVerify
import com.example.d2m.data.services.api.ApiClient
import com.example.d2m.data.services.api.SendOtpService
import com.example.d2m.data.services.api.VerifyOtpService
import com.example.d2m.databinding.FragmentOtpBinding
import com.example.d2m.screens.home.HomeActivity
import com.google.gson.GsonBuilder
import java.util.concurrent.TimeUnit


class OtpFragment : Fragment() {
    private lateinit var otpBinding: FragmentOtpBinding
    private lateinit var timer: CountDownTimer
    private val TAG = "OtpFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        otpBinding = FragmentOtpBinding.inflate(inflater)
        return otpBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        otpBinding.otpLayout.isEnabled = false
        sendOtp(arguments?.getString("phoneNum")!!, arguments?.getString("getWhatsappUpdates")!!)
        startResendTimer()

        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayShowHomeEnabled(true)
        otpBinding.resendCodeLink.setOnClickListener {
            sendOtp(
                arguments?.getString("phoneNum")!!, arguments?.getString("getWhatsappUpdates")!!
            )
        }

        otpBinding.signIn.setOnClickListener {
            val otpCode = getOtp()
            Log.d(TAG, "onViewCreated: $otpCode")
            verifyOtp(arguments?.getString("phoneNum")!!, otpCode)
        }
    }

    private fun verifyOtp(userPhoneNumber: String, otpCode: String) {
        val retrofitInstance =
            ApiClient.createService(VerifyOtpService::class.java) as VerifyOtpService
        val retrofitData =
            retrofitInstance.verifyOtp(userPhoneNumber, otpCode, "123ASDFSFFSAFSSSSS", "android")



        retrofitData.enqueue(object : retrofit2.Callback<OtpVerify?> {
            override fun onResponse(
                call: retrofit2.Call<OtpVerify?>, response: retrofit2.Response<OtpVerify?>
            ) {
                if (response.body()!!.success) {
                    Toast.makeText(
                        requireActivity(), "Verification Successful", Toast.LENGTH_LONG
                    ).show()

                    setSharedPrefs(
                        response.body()!!.data.id.toString(),
                        response.body()!!.data.token
                    )
                }
                Log.d(
                    TAG,
                    "onResponse: " + GsonBuilder().setPrettyPrinting().create()
                        .toJson(response.body())
                )
            }

            override fun onFailure(call: retrofit2.Call<OtpVerify?>, t: Throwable) {
                Log.d(TAG, "onFailure: Failed to verify OTP -> $t")
            }
        })


        ApiClient.destroyInstance()
    }

    private fun setSharedPrefs(userID: String, token: String) {

        Log.d(TAG, "setSharedPrefs: $userID")
        val sharedPreferences =
            activity?.getSharedPreferences("userData", MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putString("userID", userID)
        editor.putString("token", token)
        editor.apply()

        startActivity(Intent(activity, HomeActivity::class.java))
    }

    private fun sendOtp(userPhoneNumber: String, getWhatsappUpdates: String) {
        val retrofitInstance = ApiClient.createService(SendOtpService::class.java) as SendOtpService
        val retrofitData = retrofitInstance.sendOtp(userPhoneNumber, getWhatsappUpdates)

        retrofitData.enqueue(object : retrofit2.Callback<OtpResponse?> {
            override fun onResponse(
                call: retrofit2.Call<OtpResponse?>, response: retrofit2.Response<OtpResponse?>
            ) {
                if (response.body()!!.success) {
                    otpBinding.otpLayout.isEnabled = true
                    Toast.makeText(
                        requireActivity(), response.body()!!.data.otp.toString(), Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<OtpResponse?>, t: Throwable) {
                Log.d(TAG, "onFailure: Failed to send OTP -> $t")
            }
        })
        ApiClient.destroyInstance()
    }

    private fun getOtp(): String {
        var otpCode: String = ""
        otpBinding.apply {
            otpCode = otpCode.plus(otpDigit1.text.toString())
            otpCode = otpCode.plus(otpDigit2.text.toString())
            otpCode = otpCode.plus(otpDigit3.text.toString())
            otpCode = otpCode.plus(otpDigit4.text.toString())
        }
        return otpCode
    }

    private fun startResendTimer() {
        otpBinding.resendCodeLink.isEnabled = false

        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                otpBinding.resendCodeTimer.text = String.format(
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                    ),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                    )
                )
            }

            override fun onFinish() {
                otpBinding.resendCodeLink.isEnabled = true
                otpBinding.resendCodeLink.setTextColor(Color.BLACK)
                otpBinding.resendCodeLink.setTypeface(null, Typeface.ITALIC)
            }
        }.start()
    }

}