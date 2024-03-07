package com.example.d2m.screens.utils

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    private lateinit var binding: VB
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    abstract fun inflateBinding(): VB

    abstract fun getLayoutRes(): Int

    fun showToast(message: String, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateBinding()
        setContentView(binding.root)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        navController = Navigation.findNavController(this, getLayoutRes())
        appBarConfiguration = AppBarConfiguration(navController.graph)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            navController,
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }

}