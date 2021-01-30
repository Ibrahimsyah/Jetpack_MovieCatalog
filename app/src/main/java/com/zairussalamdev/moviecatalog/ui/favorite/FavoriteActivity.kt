package com.zairussalamdev.moviecatalog.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zairussalamdev.moviecatalog.R
import com.zairussalamdev.moviecatalog.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private var binding: ActivityFavoriteBinding? = null
    private lateinit var viewPagerAdapter: FavoriteViewPageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = resources.getString(R.string.favorite_activity_title)
        viewPagerAdapter = FavoriteViewPageAdapter(this, supportFragmentManager)
        binding?.viewpager?.adapter = viewPagerAdapter
        binding?.tabLayout?.setupWithViewPager(binding?.viewpager)
    }


    override fun onResume() {
        super.onResume()
        viewPagerAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}