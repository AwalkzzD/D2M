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
import com.example.d2m.databinding.FragmentOtpBinding
import com.example.d2m.screens.addcar.AddCarActivity
import java.util.concurrent.TimeUnit

class OtpFragment : Fragment() {
    private lateinit var otpBinding: FragmentOtpBinding
    private lateinit var timer: CountDownTimer
    private val otpViewModel: OtpViewModel by viewModels()
    private val loginDetailsViewModel: LoginDetailsViewModel by activityViewModels()
    private val TAG = "OtpFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        otpBinding = FragmentOtpBinding.inflate(inflater)
        return otpBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        otpViewModel.sendOtp(
            loginDetailsViewModel.phoneNum.value.toString(),
            loginDetailsViewModel.getWhatsappUpdates.value.toString()
        )
        startResendTimer()

        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowHomeEnabled(true)

        otpBinding.resendCodeLink.setOnClickListener {
            otpViewModel.sendOtp(
                loginDetailsViewModel.phoneNum.value.toString(),
                loginDetailsViewModel.getWhatsappUpdates.value.toString()
            )
        }

        otpBinding.signIn.setOnClickListener {
            otpViewModel.verifyOtp(
                loginDetailsViewModel.phoneNum.value.toString(), getOtp()
            )
        }
    }

    private fun initViewModel() {
        otpViewModel.otpSendLiveData.observe(viewLifecycleOwner) {
            if (it.success) {
                otpBinding.otpLayout.isEnabled = true
                Toast.makeText(
                    requireActivity(), it.data.otp.toString(), Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    requireActivity(), it.message, Toast.LENGTH_LONG
                ).show()
            }
        }

        otpViewModel.otpVerifyLiveData.observe(viewLifecycleOwner) {
            if (it.success) {
                Toast.makeText(
                    requireActivity(), "Verification Successful", Toast.LENGTH_LONG
                ).show()

                setSharedPrefs(
                    it.data.id.toString(), it.data.token
                )
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
        startActivity(Intent(activity, AddCarActivity::class.java))
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