package com.zairussalamdev.moviecatalog.ui.favorite_movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteMovieViewModelTest{
    private lateinit var favoriteMovieViewModel: FavoriteMovieViewModel

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
        favoriteMovieViewModel = FavoriteMovieViewModel(movieRepository)
    }

    @Test
    fun getAllFavoriteMovies(){
        val dummyMovies = pagedList
        `when`(dummyMovies.size).thenReturn(5)

        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyMovies

        `when`(movieRepository.getFavoriteMovies()).thenReturn(movies)
        val favMovies = movieRepository.getFavoriteMovies().value
        verify(movieRepository).getFavoriteMovies()
        assertNotNull(favMovies)
        assertEquals(5, favMovies?.size)

        favoriteMovieViewModel.getAllMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}