package com.example.d2m.screens.addcar

import android.os.Bundle
import com.example.d2m.R
import com.example.d2m.databinding.ActivityAddCarBinding
import com.example.d2m.screens.utils.BaseActivity

class AddCarActivity : BaseActivity<ActivityAddCarBinding, AddCarViewModel>(
    R.layout.activity_add_car, AddCarViewModel::class.java
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpActionBarNavigation()
    }
}