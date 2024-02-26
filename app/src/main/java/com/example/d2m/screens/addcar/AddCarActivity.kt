package com.example.d2m.screens.addcar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.d2m.R
import com.example.d2m.databinding.ActivityAddCarBinding

class AddCarActivity : AppCompatActivity() {
    private lateinit var addCarBinding: ActivityAddCarBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addCarBinding = ActivityAddCarBinding.inflate(layoutInflater)
        setContentView(addCarBinding.root)

        navController = findNavController(R.id.navHostFragmentContainer)
        setSupportActionBar(addCarBinding.appBar.toolbar)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}