package com.zairussalamdev.moviecatalog.ui.tvshows

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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TvShowsViewModelTest {

    private lateinit var tvShowsViewModel: TvShowsViewModel

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
        tvShowsViewModel = TvShowsViewModel(movieRepository)
    }

    @Test
    fun getAllTvShows() {
        testCoroutineRule.runBlockingTest {
            val dummyTvShows = DummyData.getDummyListData()
            `when`(movieRepository.getTvShowList()).thenReturn(dummyTvShows)
            val movieEntities = tvShowsViewModel.getAllTvShows()
            assertNotNull(movieEntities)
            verify(movieRepository).getTvShowList()
            tvShowsViewModel.getAllTvShows().observeForever(observer)
            verify(observer).onChanged(dummyTvShows)
        }
    }
}