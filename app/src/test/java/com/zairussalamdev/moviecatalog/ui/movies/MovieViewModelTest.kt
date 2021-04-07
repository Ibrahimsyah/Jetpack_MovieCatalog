package com.zairussalamdev.moviecatalog.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import com.zairussalamdev.moviecatalog.utils.DummyData
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var movieViewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<List<MovieEntity>>

    @Before
    fun init() {
        movieViewModel = MovieViewModel(movieRepository)
    }

    @Test
    fun getAllMovies() = runBlocking {
        val dummyMovies = DummyData.getDummyListData()
        `when`(movieRepository.getMovieList()).thenReturn(dummyMovies)
        val movieEntities = movieViewModel.getAllMovies().value
        verify(movieRepository).getMovieList()
        assertNotNull(movieEntities)
        assertEquals(dummyMovies.size, movieEntities?.size)

        movieViewModel.getAllMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}