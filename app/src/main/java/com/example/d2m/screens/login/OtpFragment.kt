package com.example.d2m.screens.login

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.d2m.databinding.FragmentOtpBinding
import java.util.concurrent.TimeUnit


class OtpFragment : Fragment() {
    private lateinit var otpBinding: FragmentOtpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        otpBinding = FragmentOtpBinding.inflate(inflater)
        return otpBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startResendTimer()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar!!.setDisplayShowHomeEnabled(true)
        otpBinding.resendCodeLink.setOnClickListener {
//            Toast.makeText(requireActivity(), "OTP sent", Toast.LENGTH_SHORT).show()
            TODO("resend code")
        }

        otpBinding.signIn.setOnClickListener {
            val otpCode = getOtp()
            TODO("sign in")
        }
    }


    private fun getOtp(): String {
        val otpCode: String = ""
        for (i in 0..4) {
            otpBinding.apply {
                otpCode.plus(otpDigit1.text.toString())
                otpCode.plus(otpDigit2.text.toString())
                otpCode.plus(otpDigit3.text.toString())
                otpCode.plus(otpDigit4.text.toString())
            }
        }
        return otpCode
    }

    private fun startResendTimer() {
        otpBinding.resendCodeLink.isEnabled = false
        object : CountDownTimer(60000, 1000) {
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