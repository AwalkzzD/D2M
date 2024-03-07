package com.example.d2m.screens.login

import com.example.d2m.R
import com.example.d2m.databinding.ActivityLoginBinding
import com.example.d2m.screens.utils.BaseActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    
    override fun inflateBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun getLayoutRes(): Int {
        return R.id.nav_host_fragment_container
    }

}