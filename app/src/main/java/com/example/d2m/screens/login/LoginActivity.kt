package com.example.d2m.screens.login

import com.example.d2m.R
import com.example.d2m.databinding.ActivityLoginBinding
import com.example.d2m.screens.utils.base_classes.BaseActivity

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(
    R.layout.activity_login, LoginViewModel::class.java
)