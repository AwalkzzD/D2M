package com.example.d2m.screens.home

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import com.example.d2m.R
import com.example.d2m.databinding.ActivityHomeBinding
import com.example.d2m.screens.utils.BaseActivity

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun inflateBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun getLayoutRes(): Int {
        return R.id.nav_host_fragment_container
    }

    private val homeActivityViewModel: HomeActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        val userID = getSharedPreferences("userData", Context.MODE_PRIVATE)?.getString("userID", "")
        if (userID != null) {
            homeActivityViewModel.requestUserData(userID)
        }
    }
}