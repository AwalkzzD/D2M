package com.example.d2m.screens.login.phone_otp

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.d2m.R
import com.example.d2m.databinding.FragmentOtpBinding
import com.example.d2m.screens.add_car.AddCarActivity
import com.example.d2m.screens.home.HomeActivity
import com.example.d2m.screens.utils.BaseActivity
import com.example.d2m.screens.utils.BaseFragment
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.concurrent.TimeUnit

private const val TAG = "OtpFragment"

class OtpFragment : BaseFragment<FragmentOtpBinding, OtpViewModel>(
    R.layout.fragment_otp, OtpViewModel::class.java
) {
    private lateinit var timer: CountDownTimer
    private val loginDetailsViewModel: LoginDetailsViewModel by activityViewModels()

    override fun setUpView() {
        setUpToolBar()
        initViewModel()
        sendOtp()
        startResendTimer()
        setupClickListener()
    }

    private fun setUpToolBar() {
        (activity as BaseActivity<*, *>).setupToolbar(fragmentBinding.appBar.toolbar, "", true)
    }

    private fun initViewModel() {

        fragmentViewModel.otpSendLiveData.observe(viewLifecycleOwner) {
            if (it.success) {
                fragmentBinding.otpLayout.isEnabled = true

                it.sendOtpResponseData?.let { otpVerifyObj ->
                    showToast(otpVerifyObj.otp.toString(), Toast.LENGTH_LONG)
                }
            } else {
                showToast(it.message, Toast.LENGTH_LONG)
            }
        }

        fragmentViewModel.verifyOtpResponseLiveData.observe(viewLifecycleOwner) {
            if (it.success) {
                showToast("Verification Successful", Toast.LENGTH_LONG)

                setSharedPrefs(Gson().toJson(it.verifyOtpResponseData))

                if (it.verifyOtpResponseData != null) {
                    if (it.verifyOtpResponseData.totalCars > 0) {
                        startActivity(Intent(activity, HomeActivity::class.java))
                        requireActivity().finish()
                    } else {
                        startActivity(Intent(activity, AddCarActivity::class.java))
                        requireActivity().finish()
                    }
                }

            } else {
                showToast(it.message, Toast.LENGTH_LONG)
            }
        }
    }

    private fun sendOtp() {
        fragmentViewModel.sendOtp(
            loginDetailsViewModel.phoneNum.value.toString(),
            loginDetailsViewModel.getWhatsappUpdates.value.toString()
        )
    }

    private fun startResendTimer() {
        fragmentBinding.resendCodeLink.isEnabled = false

        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                fragmentBinding.resendCodeTimer.text = String.format(
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
                fragmentBinding.resendCodeLink.isEnabled = true
                fragmentBinding.resendCodeLink.setTextColor(Color.BLACK)
                fragmentBinding.resendCodeLink.setTypeface(null, Typeface.ITALIC)
            }
        }.start()
    }

    private fun setupClickListener() {
        fragmentBinding.resendCodeLink.setOnClickListener {
            sendOtp()
        }

        fragmentBinding.signIn.setOnClickListener {
            fragmentViewModel.verifyOtp(
                loginDetailsViewModel.phoneNum.value.toString(), getOtp()
            )
        }
    }

    private fun getOtp(): String {
        var otpCode = ""
        fragmentBinding.apply {
            otpCode = otpCode.plus(otpDigit1.text.toString())
            otpCode = otpCode.plus(otpDigit2.text.toString())
            otpCode = otpCode.plus(otpDigit3.text.toString())
            otpCode = otpCode.plus(otpDigit4.text.toString())
        }
        return otpCode
    }

    private fun setSharedPrefs(
        verifyOtpResponseData: String
    ) {
        val sharedPreferences = activity?.getSharedPreferences("userData", MODE_PRIVATE)
        sharedPreferences?.edit()?.run {
            putString("verifiedUser", verifyOtpResponseData)
            putBoolean("isLoggedIn", true)
            apply()
        }
    }
}