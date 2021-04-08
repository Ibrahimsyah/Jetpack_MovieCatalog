package com.zairussalamdev.moviecatalog.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import com.zairussalamdev.moviecatalog.utils.DummyData
import com.zairussalamdev.moviecatalog.utils.TestCoroutineRule
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MovieViewModelTest {

    private lateinit var movieViewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<List<MovieEntity>>

    @Before
    fun init() {
        movieViewModel = MovieViewModel(movieRepository)
    }

    @Test
    fun getAllMovies() {
        testCoroutineRule.runBlockingTest {
            val dummyMovies = DummyData.getDummyListData()
            `when`(movieRepository.getMovieList()).thenReturn(dummyMovies)
            val movieEntities = movieViewModel.getAllMovies()
            assertNotNull(movieEntities)
            verify(movieRepository).getMovieList()
            movieViewModel.getAllMovies().observeForever(observer)
            verify(observer).onChanged(dummyMovies)
        }
    }
}