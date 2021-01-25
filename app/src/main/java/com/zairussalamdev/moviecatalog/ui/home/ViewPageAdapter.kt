package com.zairussalamdev.moviecatalog.ui.home

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.zairussalamdev.moviecatalog.R
import com.zairussalamdev.moviecatalog.ui.movies.MoviesFragment
import com.zairussalamdev.moviecatalog.ui.tvshows.TvShowsFragment

class ViewPageAdapter(
    private val context: Context, fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    companion object {
        private val TAB_TITLES = intArrayOf(R.string.movies, R.string.tvShows)
    }

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> MoviesFragment()
            1 -> TvShowsFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence =
        context.resources.getString(TAB_TITLES[position])
}