package com.example.d2m.screens.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.viewbinding.ViewBinding
import com.example.d2m.R

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel>(
    private val inflate: (LayoutInflater) -> VB, private val viewModelClass: Class<VM>
) : AppCompatActivity() {

    private lateinit var binding: VB
    private lateinit var viewModel: VM
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    /**
     * protected variable to use them in subclasses inheriting BaseActivity
     * */
    protected val activityBinding: VB get() = binding
    protected val activityViewModel: VM get() = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[viewModelClass]
    }

    private fun setUpNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)
        appBarConfiguration = AppBarConfiguration(navController.graph)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            navController, appBarConfiguration
        ) || super.onSupportNavigateUp()
    }

    fun showToast(message: String, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }

}