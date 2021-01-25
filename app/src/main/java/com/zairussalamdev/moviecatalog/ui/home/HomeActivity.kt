package com.zairussalamdev.moviecatalog.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zairussalamdev.moviecatalog.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homeActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(homeActivityBinding.root)

        val viewPagerAdapter = ViewPageAdapter(this, supportFragmentManager)
        homeActivityBinding.viewpager.adapter = viewPagerAdapter
        homeActivityBinding.tabLayout.setupWithViewPager(homeActivityBinding.viewpager)
    }
}