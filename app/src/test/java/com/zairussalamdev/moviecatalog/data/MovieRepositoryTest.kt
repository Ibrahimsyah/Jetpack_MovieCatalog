package com.zairussalamdev.moviecatalog.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.zairussalamdev.moviecatalog.data.source.remote.RemoteDataSource
import com.zairussalamdev.moviecatalog.utils.DummyData
import com.zairussalamdev.moviecatalog.utils.LiveDataTestUtil
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class MovieRepositoryTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val movieRepository = FakeMovieRepository(remote)

    private val movieResponse = DummyData.getDummyMovieResponse()
    private val movieDetailResponse = DummyData.getDummyMovieDetailResponse()
    private val tvShowResponse = DummyData.getDummyTvShowResponse()
    private val tvShowDetailResponse = DummyData.getDummyTvShowDetailResponse()

    @Test
    fun getAllMovies(){
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.MovieListCallback)
                .onMovieListLoaded(movieResponse)
            null
        }.`when`(remote).getMovieList(any())
        val movieListTest = LiveDataTestUtil.getValue(movieRepository.getMovieList())
        verify(remote).getMovieList(any())
        assertNotNull(movieListTest)
        assertEquals(movieResponse.size, movieListTest.size)
    }

    @Test
    fun getMovieDetail(){
        val movieId = 1
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.MovieDetailCallback)
                .onMovieDetailLoaded(movieDetailResponse)
            null
        }.`when`(remote).getMovieDetail(eq(movieId), any())
        val movieDetailTest = LiveDataTestUtil.getValue(movieRepository.getMovieDetail(movieId))
        verify(remote).getMovieDetail(eq(movieId), any())
        assertNotNull(movieDetailTest)
        assertEquals(movieDetailResponse.title, movieDetailTest.title)
    }

    @Test
    fun getAllTvShows(){
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.TvShowListCallback)
                .onTvShowListLoaded(tvShowResponse)
            null
        }.`when`(remote).getTvShowList(any())
        val tvShowListTest = LiveDataTestUtil.getValue(movieRepository.getTvShowList())
        verify(remote).getTvShowList(any())
        assertNotNull(tvShowListTest)
        assertEquals(tvShowResponse.size, tvShowListTest.size)
    }

    @Test
    fun getTvShowDetail(){
        val tvShowId = 1
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.TvShowDetailCallback)
                .onTvShowDetailLoaded(tvShowDetailResponse)
            null
        }.`when`(remote).getTvShowDetail(eq(tvShowId), any())
        val tvShowDetailTest = LiveDataTestUtil.getValue(movieRepository.getTvShowDetail(tvShowId))
        verify(remote).getTvShowDetail(eq(tvShowId), any())
        assertNotNull(tvShowDetailTest)
        assertEquals(tvShowDetailResponse.title, tvShowDetailTest.title)
    }

}