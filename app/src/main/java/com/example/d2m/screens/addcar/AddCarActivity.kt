package com.example.d2m.screens.addcar

import com.example.d2m.R
import com.example.d2m.databinding.ActivityAddCarBinding
import com.example.d2m.screens.utils.BaseActivity

class AddCarActivity : BaseActivity<ActivityAddCarBinding>() {

    override fun inflateBinding(): ActivityAddCarBinding {
        return ActivityAddCarBinding.inflate(layoutInflater)
    }

    override fun getLayoutRes(): Int {
        return R.id.nav_host_fragment_container
    }

}