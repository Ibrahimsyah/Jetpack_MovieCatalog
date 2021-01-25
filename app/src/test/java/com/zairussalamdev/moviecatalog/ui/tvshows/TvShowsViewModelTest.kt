package com.zairussalamdev.moviecatalog.ui.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import com.zairussalamdev.moviecatalog.utils.DummyData
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowsViewModelTest {

    private lateinit var tvShowsViewModel: TvShowsViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

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
        val dummyTvShows = DummyData.getDummyListData()
        val tvShow = MutableLiveData<List<MovieEntity>>()
        tvShow.value = dummyTvShows

        `when`(movieRepository.getTvShowList()).thenReturn(tvShow)
        val movieEntities = tvShowsViewModel.getAllTvShows().value
        verify(movieRepository).getTvShowList()
        assertNotNull(movieEntities)
        assertEquals(dummyTvShows.size, movieEntities?.size)

        tvShowsViewModel.getAllTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }
}