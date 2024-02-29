package com.example.d2m.screens.home

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.d2m.R
import com.example.d2m.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var homeBinding: ActivityHomeBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val homeActivityViewModel: HomeActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        navController = findNavController(R.id.navHostFragmentContainer)
        setSupportActionBar(homeBinding.appBar.toolbar)

        appBarConfiguration = AppBarConfiguration(navController.graph)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        homeBinding.bottomNav.setupWithNavController(navController)
    }

    private fun initViewModel() {
        val userID = getSharedPreferences("userData", Context.MODE_PRIVATE)
            ?.getString("userID", "")
        if (userID != null) {
            homeActivityViewModel.requestUserData(userID)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}