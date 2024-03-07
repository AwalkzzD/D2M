package com.example.d2m.screens.addcar

import com.example.d2m.databinding.ActivityAddCarBinding
import com.example.d2m.screens.utils.BaseActivity

class AddCarActivity : BaseActivity<ActivityAddCarBinding, AddCarViewModel>(
    ActivityAddCarBinding::inflate, AddCarViewModel::class.java
)