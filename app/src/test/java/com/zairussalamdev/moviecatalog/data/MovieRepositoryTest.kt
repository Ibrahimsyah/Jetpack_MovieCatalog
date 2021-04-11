package com.zairussalamdev.moviecatalog.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.zairussalamdev.moviecatalog.data.source.local.LocalDataSource
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import com.zairussalamdev.moviecatalog.data.source.remote.RemoteDataSource
import com.zairussalamdev.moviecatalog.utils.AppExecutors
import com.zairussalamdev.moviecatalog.utils.DummyData
import com.zairussalamdev.moviecatalog.utils.LiveDataTestUtil
import com.zairussalamdev.moviecatalog.utils.PagedDataTestUtil
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@Suppress("UNCHECKED_CAST")
class MovieRepositoryTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val movieRepository = FakeMovieRepository(remote, local, appExecutors)

    private val movieResponse = DummyData.getDummyMovieResponse()
    private val movieDetailResponse = DummyData.getDummyMovieDetailResponse()
    private val tvShowResponse = DummyData.getDummyTvShowResponse()
    private val tvShowDetailResponse = DummyData.getDummyTvShowDetailResponse()

    @Test
    fun getAllMovies() = runBlocking {
        `when`(remote.getMovieList()).thenReturn(movieResponse)
        val movieListTest = movieRepository.getMovieList()
        verify(remote).getMovieList()
        assertNotNull(movieListTest)
        assertEquals(movieResponse.movies.size, movieListTest.size)
    }

    @Test
    fun getMovieDetail() = runBlocking {
        val movieId = 1
        `when`(remote.getMovieDetail(eq(movieId))).thenReturn(movieDetailResponse)
        val movieDetailTest = movieRepository.getMovieDetail(movieId)
        verify(remote).getMovieDetail(eq(movieId))
        assertNotNull(movieDetailTest)
        assertEquals(movieDetailResponse.title, movieDetailTest.title)
    }

    @Test
    fun getAllTvShows() = runBlocking {
        `when`(remote.getTvShowList()).thenReturn(tvShowResponse)
        val tvShowListTest = movieRepository.getTvShowList()
        verify(remote).getTvShowList()
        assertNotNull(tvShowListTest)
        assertEquals(tvShowResponse.tvShows.size, tvShowListTest.size)
    }

    @Test
    fun getTvShowDetail() = runBlocking {
        val tvShowId = 1
        `when`(remote.getTvShowDetail(eq(tvShowId))).thenReturn(tvShowDetailResponse)
        val tvShowDetailTest = movieRepository.getTvShowDetail(tvShowId)
        verify(remote).getTvShowDetail(eq(tvShowId))
        assertNotNull(tvShowDetailTest)
        assertEquals(tvShowDetailResponse.title, tvShowDetailTest.title)
    }

    @Test
    fun getFavoriteMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavouriteMovies()).thenReturn(dataSourceFactory)
        movieRepository.getFavoriteMovies()

        val favoriteMovies = PagedDataTestUtil.mockPagedList(DummyData.getDummyListData())
        verify(local).getFavouriteMovies()
        assertNotNull(favoriteMovies)
    }

    @Test
    fun getFavoriteTvMovies() {
        val dataSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavoriteTvShows()).thenReturn(dataSourceFactory)
        movieRepository.getFavoriteTvShows()

        val favoriteTvShow = PagedDataTestUtil.mockPagedList(DummyData.getDummyListData())
        verify(local).getFavoriteTvShows()
        assertNotNull(favoriteTvShow)
    }

    @Test
    fun checkMovieFavorite() {
        val movieId = 1
        val isFavorite = MutableLiveData<Boolean>()
        isFavorite.value = false
        `when`(local.checkMovieIsFavourite(movieId)).thenReturn(isFavorite)
        val result = LiveDataTestUtil.getValue(movieRepository.checkMovieIsFavourite(movieId))
        verify(local).checkMovieIsFavourite(movieId)

        assertEquals(false, result)
    }

}