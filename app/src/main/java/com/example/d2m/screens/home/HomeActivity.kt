package com.example.d2m.screens.home

import android.content.Context
import android.os.Bundle
import com.example.d2m.R
import com.example.d2m.databinding.ActivityHomeBinding
import com.example.d2m.screens.utils.BaseActivity

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeActivityViewModel>(
    R.layout.activity_home, HomeActivityViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        setUpActionBarNavigation()
    }

    private fun initViewModel() {
        val userID = getSharedPreferences("userData", Context.MODE_PRIVATE)?.getString("userID", "")
        if (userID != null) {
            activityViewModel.requestUserData(userID)
        }
    }
}