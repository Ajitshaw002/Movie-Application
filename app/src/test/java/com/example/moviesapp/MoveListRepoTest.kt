package com.example.moviesapp

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.moviesapp.model.detail.MovieDetailResponse
import com.example.moviesapp.model.list.MovieListResponse
import com.example.moviesapp.repository.MoveListRepo
import com.network.networkmanager.Http
import com.network.networkmanager.ResponseListener
import org.json.JSONObject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoveListRepoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var applicationMock: Application

    @Mock
    private lateinit var httpMock: Http.RequestCall

    @Mock
    private lateinit var responseListenerMock: ResponseListener

    private lateinit var movieListRepo: MoveListRepo

    @Before
    fun setUp() {
        Mockito.`when`(applicationMock.getString(Mockito.anyInt())).thenReturn("Error")

        movieListRepo = MoveListRepo(applicationMock)
    }

    @Test
    fun `test getMoviesList success`() {
        val jsonResponse = JSONObject("{'success': true, 'results': []}")

        Mockito.`when`(httpMock.makeRequest(responseListenerMock)).then {
            responseListenerMock.onResponse(jsonResponse)
        }

        val observer = Mockito.mock(Observer::class.java) as Observer<MovieListResponse>
        movieListRepo.movieList.observeForever(observer)

        movieListRepo.getMoviesList(httpMock)

        Mockito.verify(observer).onChanged(Mockito.any())
        Mockito.verifyNoMoreInteractions(observer)
    }

    @Test
    fun `test getNowPlayingList success`() {
        val jsonResponse = JSONObject("{'success': true, 'results': []}")

        Mockito.`when`(httpMock.makeRequest(responseListenerMock)).then {
            responseListenerMock.onResponse(jsonResponse)
        }

        val observer = Mockito.mock(Observer::class.java) as Observer<MovieListResponse>
        movieListRepo.nowPlaying.observeForever(observer)

        movieListRepo.getNowPlayingList(httpMock)

        Mockito.verify(observer).onChanged(Mockito.any())
        Mockito.verifyNoMoreInteractions(observer)
    }

    @Test
    fun `test getUpComingPlayingList success`() {
        val jsonResponse = JSONObject("{'success': true, 'results': []}")

        Mockito.`when`(httpMock.makeRequest(responseListenerMock)).then {
            responseListenerMock.onResponse(jsonResponse)
        }

        val observer = Mockito.mock(Observer::class.java) as Observer<MovieListResponse>
        movieListRepo.upcomingMoviesList.observeForever(observer)

        movieListRepo.getUpComingPlayingList(httpMock)

        Mockito.verify(observer).onChanged(Mockito.any())
        Mockito.verifyNoMoreInteractions(observer)
    }

    @Test
    fun `test getMovieDetail success`() {
        val jsonResponse = JSONObject("{'success': true, 'id': 1}")

        Mockito.`when`(httpMock.makeRequest(responseListenerMock)).then {
            responseListenerMock.onResponse(jsonResponse)
        }

        val observer = Mockito.mock(Observer::class.java) as Observer<MovieDetailResponse>
        movieListRepo.movieDetailResponse.observeForever(observer)

        movieListRepo.getMovieDetail(httpMock)

        Mockito.verify(observer).onChanged(Mockito.any())
        Mockito.verifyNoMoreInteractions(observer)
    }
}