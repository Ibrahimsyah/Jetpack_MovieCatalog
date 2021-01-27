package com.zairussalamdev.moviecatalog.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zairussalamdev.moviecatalog.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPagerAdapter = FavoriteViewPageAdapter(this, supportFragmentManager)
        binding.viewpager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewpager)
    }
}