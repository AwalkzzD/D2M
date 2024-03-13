package com.example.d2m.screens.login.phone_otp

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.d2m.R
import com.example.d2m.databinding.FragmentOtpBinding
import com.example.d2m.screens.add_car.AddCarActivity
import com.example.d2m.screens.home.HomeActivity
import com.example.d2m.screens.utils.BaseFragment
import java.util.concurrent.TimeUnit

private const val TAG = "OtpFragment"

class OtpFragment : BaseFragment<FragmentOtpBinding, OtpViewModel>(
    R.layout.fragment_otp, OtpViewModel::class.java
) {
    private lateinit var timer: CountDownTimer
    private val loginDetailsViewModel: LoginDetailsViewModel by activityViewModels()

    override fun onCreateSetup() {
        super.onCreateSetup()
        setupActionBar()
    }

    override fun setUpView() {
        initViewModel()
        sendOtp()
        startResendTimer()
        setupClickListener()
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

    private fun setupActionBar() {
        (activity as AppCompatActivity).apply {
            setSupportActionBar(fragmentBinding.appBar.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            setupActionBarWithNavController(
                findNavController(), AppBarConfiguration(findNavController().graph)
            )
        }
    }

    private fun sendOtp() {
        fragmentViewModel.sendOtp(
            loginDetailsViewModel.phoneNum.value.toString(),
            loginDetailsViewModel.getWhatsappUpdates.value.toString()
        )
    }

    private fun initViewModel() {

        fragmentViewModel.otpSendLiveData.observe(viewLifecycleOwner) {
            if (it.success) {
                fragmentBinding.otpLayout.isEnabled = true

                it.sendOtpResponseData?.let { otpVerifyObj ->
                    Toast.makeText(
                        requireActivity(), otpVerifyObj.otp.toString(), Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireActivity(), it.message, Toast.LENGTH_LONG
                ).show()
            }
        }

        fragmentViewModel.verifyOtpResponseLiveData.observe(viewLifecycleOwner) {
            if (it.success) {

                Toast.makeText(
                    requireActivity(), "Verification Successful", Toast.LENGTH_LONG
                ).show()

                setSharedPrefs(
                    it.verifyOtpResponseData?.id.toString(), it.verifyOtpResponseData?.token ?: ""
                )

                if (it.verifyOtpResponseData != null) {
                    if (it.verifyOtpResponseData.totalCars > 0) {
                        startActivity(Intent(activity, HomeActivity::class.java))
                    } else {
                        startActivity(Intent(activity, AddCarActivity::class.java))
                    }
                }

            } else {
                Toast.makeText(
                    requireActivity(), it.message, Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setSharedPrefs(userID: String, token: String) {
        Log.d(TAG, "setSharedPrefs: $userID")
        val sharedPreferences = activity?.getSharedPreferences("userData", MODE_PRIVATE)
        sharedPreferences?.edit()?.run {
            putString("userID", userID)
            putString("token", token)
            apply()
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
}