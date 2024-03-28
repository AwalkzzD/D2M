package com.example.d2m.screens.home

import android.content.Context
import android.os.Bundle
import com.example.d2m.R
import com.example.d2m.data.remote.otp.verify.VerifyOtpResponseData
import com.example.d2m.databinding.ActivityHomeBinding
import com.example.d2m.screens.utils.BaseActivity
import com.google.gson.Gson

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeActivityViewModel>(
    R.layout.activity_home, HomeActivityViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSharedPrefs()
    }

    private fun setSharedPrefs() {
        val sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE)
        val userData = Gson().fromJson(
            sharedPreferences.getString("verifiedUser", ""), VerifyOtpResponseData::class.java
        )
        activityViewModel.requestUserData(userData.id.toString())
    }
}