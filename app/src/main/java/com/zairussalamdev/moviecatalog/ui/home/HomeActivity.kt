package com.zairussalamdev.moviecatalog.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.zairussalamdev.moviecatalog.R
import com.zairussalamdev.moviecatalog.databinding.ActivityMainBinding
import com.zairussalamdev.moviecatalog.ui.favorite.FavoriteActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homeActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(homeActivityBinding.root)

        val viewPagerAdapter = ViewPageAdapter(this, supportFragmentManager)
        homeActivityBinding.viewpager.adapter = viewPagerAdapter
        homeActivityBinding.tabLayout.setupWithViewPager(homeActivityBinding.viewpager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
}