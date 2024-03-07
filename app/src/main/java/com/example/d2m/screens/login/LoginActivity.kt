package com.example.d2m.screens.login

import com.example.d2m.databinding.ActivityLoginBinding
import com.example.d2m.screens.utils.BaseActivity

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>(
    ActivityLoginBinding::inflate, LoginViewModel::class.java
)