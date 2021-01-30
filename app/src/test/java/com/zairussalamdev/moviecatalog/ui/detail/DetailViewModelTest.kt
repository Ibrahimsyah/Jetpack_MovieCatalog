package com.zairussalamdev.moviecatalog.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doNothing
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.entity.DetailEntity
import com.zairussalamdev.moviecatalog.utils.DummyData
import junit.framework.Assert.assertEquals
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
    private val dummyData = DummyData.getDummyListData()

    private val TYPE_MOVIE = 1
    private val TYPE_TV_SHOW = 2

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<DetailEntity>

    @Mock
    private lateinit var booleanObserver: Observer<Boolean>

    @Before
    fun init() {
        detailViewModel = DetailViewModel(movieRepository)
    }

    @Test
    fun getMovieDetail() {
        val movieId = 1

        val movieDetail = MutableLiveData<DetailEntity>()
        movieDetail.value = dummyDetail

        `when`(movieRepository.getMovieDetail(movieId)).thenReturn(movieDetail)
        val movieDetailResult = detailViewModel.getMovieDetail(TYPE_MOVIE, movieId)
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
        val tvShowDetailResult = detailViewModel.getMovieDetail(TYPE_TV_SHOW, tvShowId)
        verify(movieRepository).getTvShowDetail(tvShowId)
        assertNotNull(tvShowDetailResult)

        detailViewModel.getMovieDetail(TYPE_TV_SHOW, tvShowId).observeForever(observer)
        verify(observer).onChanged(dummyDetail)
    }

    @Test
    fun checkIsMovieFavorite() {
        val movieId = 1
        val expected = MutableLiveData<Boolean>()
        expected.value = false
        `when`(movieRepository.checkMovieIsFavourite(movieId)).thenReturn(expected)
        val result = detailViewModel.checkMovieIsFavourite(movieId).value
        verify(movieRepository).checkMovieIsFavourite(movieId)
        assertEquals(expected.value, result)

        detailViewModel.checkMovieIsFavourite(movieId).observeForever(booleanObserver)
        verify(booleanObserver).onChanged(expected.value)
    }

    @Test
    fun addMovieFavorite() {
        val movie = dummyData[0]
        doNothing().`when`(movieRepository).addFavoriteMovie(movie)
        detailViewModel.insertFavoriteMovie(movie)
        verify(movieRepository).addFavoriteMovie(movie)
    }

    @Test
    fun removeMovieFavorite() {
        val movie = dummyData[0]
        doNothing().`when`(movieRepository).removeFavoriteMovie(movie)
        detailViewModel.deleteFavoriteMovie(movie)
        verify(movieRepository).removeFavoriteMovie(movie)
    }
}