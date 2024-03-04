package com.example.d2m.screens.login.phone_otp

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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.d2m.databinding.FragmentOtpBinding
import com.example.d2m.screens.addcar.AddCarActivity
import com.example.d2m.screens.home.HomeActivity
import java.util.concurrent.TimeUnit

private const val TAG = "OtpFragment"

class OtpFragment : Fragment() {

    private lateinit var otpBinding: FragmentOtpBinding
    private lateinit var timer: CountDownTimer

    private val loginDetailsViewModel: LoginDetailsViewModel by activityViewModels()
    private val otpViewModel: OtpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        otpBinding = FragmentOtpBinding.inflate(inflater)
        setupActionBar()
        return otpBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        sendOtp()

        startResendTimer()

        otpBinding.resendCodeLink.setOnClickListener {
            sendOtp()
        }

        otpBinding.signIn.setOnClickListener {
            otpViewModel.verifyOtp(
                loginDetailsViewModel.phoneNum.value.toString(), getOtp()
            )
        }

    }

    private fun setupActionBar() {
        (activity as AppCompatActivity).setSupportActionBar(otpBinding.appBar.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity).setupActionBarWithNavController(
            findNavController(), AppBarConfiguration(findNavController().graph)
        )
    }

    private fun sendOtp() {
        otpViewModel.sendOtp(
            loginDetailsViewModel.phoneNum.value.toString(),
            loginDetailsViewModel.getWhatsappUpdates.value.toString()
        )
    }

    private fun initViewModel() {

        otpViewModel.otpSendLiveData.observe(viewLifecycleOwner) {
            if (it.success) {
                otpBinding.otpLayout.isEnabled = true

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

        otpViewModel.verifyOtpResponseLiveData.observe(viewLifecycleOwner) {
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
        val editor = sharedPreferences?.edit()
        if (editor != null) {
            editor.putString("userID", userID)
            editor.putString("token", token)
            editor.apply()
        }
    }

    private fun getOtp(): String {
        var otpCode = ""
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