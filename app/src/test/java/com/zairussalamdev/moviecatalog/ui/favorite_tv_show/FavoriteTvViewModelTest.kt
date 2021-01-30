package com.zairussalamdev.moviecatalog.ui.favorite_tv_show

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteTvViewModelTest{
    private lateinit var favoriteTvViewModel: FavoriteTvViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun init(){
        favoriteTvViewModel = FavoriteTvViewModel(movieRepository)
    }

    @Test
    fun getAllFavoriteMovies() {
        val dummyTvShows = pagedList
        Mockito.`when`(dummyTvShows.size).thenReturn(5)

        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyTvShows

        Mockito.`when`(movieRepository.getFavoriteTvShows()).thenReturn(movies)
        val favMovies = favoriteTvViewModel.getAllTvShows().value
        Mockito.verify(movieRepository).getFavoriteTvShows()
        assertNotNull(favMovies)
        assertEquals(5, favMovies?.size)

        favoriteTvViewModel.getAllTvShows().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTvShows)
    }
}