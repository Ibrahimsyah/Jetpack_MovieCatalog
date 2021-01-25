package com.zairussalamdev.moviecatalog.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.entity.DetailEntity
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import com.zairussalamdev.moviecatalog.utils.DummyData
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    private lateinit var detailViewModel: DetailViewModel

    private val dummyDetail = DummyData.getDummyDetailData()
    private val TYPE_MOVIE = 1
    private val TYPE_TV_SHOW = 2

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<DetailEntity>

    @Before
    fun init() {
        detailViewModel = DetailViewModel(movieRepository)
    }

    @Test
    fun getMovieDetail(){
        val movieId = 1

        val movieDetail = MutableLiveData<DetailEntity>()
        movieDetail.value = dummyDetail

        `when`(movieRepository.getMovieDetail(movieId)).thenReturn(movieDetail)
        val movieDetailResult = movieRepository.getMovieDetail(movieId)
        verify(movieRepository).getMovieDetail(movieId)
        assertNotNull(movieDetailResult)

        detailViewModel.getMovieDetail(TYPE_MOVIE, movieId).observeForever(observer)
        verify(observer).onChanged(dummyDetail)
    }

    @Test
    fun getTvShowDetail(){
        val tvShowId = 1

        val tvShowDetail = MutableLiveData<DetailEntity>()
        tvShowDetail.value = dummyDetail

        `when`(movieRepository.getTvShowDetail(tvShowId)).thenReturn(tvShowDetail)
        val tvShowDetailResult = movieRepository.getTvShowDetail(tvShowId)
        verify(movieRepository).getTvShowDetail(tvShowId)
        assertNotNull(tvShowDetailResult)

        detailViewModel.getMovieDetail(TYPE_TV_SHOW, tvShowId).observeForever(observer)
        verify(observer).onChanged(dummyDetail)
    }
}