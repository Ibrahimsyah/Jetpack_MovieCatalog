package com.zairussalamdev.moviecatalog.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.zairussalamdev.moviecatalog.R
import com.zairussalamdev.moviecatalog.ui.home.HomeActivity

const val splashScreenTimeoutMillis: Long = 3000

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(mainLooper).postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, splashScreenTimeoutMillis)
    }
}