package com.example.d2m.screens.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.d2m.R
import com.example.d2m.databinding.ActivityIntroBinding
import com.example.d2m.screens.login.LoginActivity
import com.example.d2m.screens.utils.base_classes.BaseActivity
import com.example.d2m.screens.utils.base_classes.BaseViewModel
import com.example.d2m.screens.utils.adapters.IntroPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class IntroActivity : BaseActivity<ActivityIntroBinding, BaseViewModel>(
    R.layout.activity_intro, BaseViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityBinding.apply {

            introViewPager.adapter = IntroPagerAdapter()

            TabLayoutMediator(intoTabLayout, introViewPager) { _, _ -> }.attach()

            nextIntroPage.setOnClickListener { introViewPager.currentItem = 1 }

            introViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == 1) {
                        nextIntroPage.visibility = View.INVISIBLE
                    } else {
                        nextIntroPage.visibility = View.VISIBLE
                    }
                }
            })

        }

        activityBinding.getStartedButton.setOnClickListener {
            setSharedPrefs()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun setSharedPrefs() {
        val sharedPreferences = getSharedPreferences("newUserTrack", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isNewUser", false)
        editor.apply()
    }
}