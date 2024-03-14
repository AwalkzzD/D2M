package com.example.d2m.screens.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.d2m.R
import com.example.d2m.screens.home.HomeActivity
import com.example.d2m.screens.intro.IntroActivity
import com.example.d2m.screens.login.LoginActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val sharedPreferences = getSharedPreferences("newUserTrack", Context.MODE_PRIVATE)
        val isNewUser = sharedPreferences.getBoolean("isNewUser", true)

        if (isNewUser) {

            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
            }, 1000)

        } else {

            startActivity(Intent(this, LoginActivity::class.java))
//            startActivity(Intent(this, AddCarActivity::class.java))
//            startActivity(Intent(this, HomeActivity::class.java))
//            startActivity(Intent(this, IntroActivity::class.java))

            finish()

        }

    }
}