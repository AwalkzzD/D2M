package com.example.d2m.screens.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.d2m.databinding.ActivityIntroBinding
import com.example.d2m.screens.login.LoginActivity
import com.example.d2m.screens.utils.IntroPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class IntroActivity : AppCompatActivity() {
    private lateinit var introBinding: ActivityIntroBinding
    private val TAG = "IntroActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        introBinding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(introBinding.root)

        introBinding.apply {
            introViewPager.adapter = IntroPagerAdapter()
            TabLayoutMediator(intoTabLayout, introViewPager) { tab, position -> }.attach()

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

        introBinding.getStartedButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("newUserTrack", Context.MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            editor.putBoolean("isNewUser", false)
            editor.apply()

            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}