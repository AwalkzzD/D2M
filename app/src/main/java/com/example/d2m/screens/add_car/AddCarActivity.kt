package com.example.d2m.screens.add_car

import android.os.Bundle
import com.example.d2m.R
import com.example.d2m.databinding.ActivityAddCarBinding
import com.example.d2m.screens.utils.base_classes.BaseActivity

class AddCarActivity : BaseActivity<ActivityAddCarBinding, AddCarViewModel>(
    R.layout.activity_add_car, AddCarViewModel::class.java
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar(activityBinding.appBar.toolbar, "Add Car", false)
    }
}