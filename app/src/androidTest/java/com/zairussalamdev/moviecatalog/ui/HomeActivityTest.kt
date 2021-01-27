package com.zairussalamdev.moviecatalog.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.zairussalamdev.moviecatalog.R
import com.zairussalamdev.moviecatalog.ui.home.HomeActivity
import com.zairussalamdev.moviecatalog.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)

    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
    }

    @Test
    fun loadMovieDetail() {
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.rv_movies))
        onView(withId(R.id.movieDetailTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.movieDetailDescription)).check(matches(isDisplayed()))
        onView(withId(R.id.movieDetailPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.movieDetailTagLine)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvShows() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rvTvShows)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTvShowDetail() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rvTvShows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.movieDetailTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.movieDetailDescription)).check(matches(isDisplayed()))
        onView(withId(R.id.movieDetailPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.movieDetailTagLine)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavoriteMovies() {
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
    }

    @Test
    fun loadFavoriteTvShows() {
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rvTvShows)).check(matches(isDisplayed()))
    }
}