package com.example.moviesapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.example.moviesapp.helper.ApiRequest
import com.example.moviesapp.model.detail.MovieDetailResponse
import com.example.moviesapp.repository.MoveListRepo
import com.example.moviesapp.viewmodel.MovieDetailViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    // Required for LiveData to work with JUnit
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Mock dependencies
    @Mock
    private lateinit var moveListRepo: MoveListRepo

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    // Mock LiveData observer
    @Mock
    private lateinit var movieDetailObserver: Observer<MovieDetailResponse>

    // Class under test
    private lateinit var movieDetailViewModel: MovieDetailViewModel

    @Before
    fun setUp() {
        // Initialize the ViewModel
        movieDetailViewModel = MovieDetailViewModel(moveListRepo, savedStateHandle)

        // Mock the movieId saved in SavedStateHandle
        Mockito.`when`(savedStateHandle.get<Int>("movieId")).thenReturn(MOVIE_ID)

        // Observe LiveData
        movieDetailViewModel.detailResponse.observeForever(movieDetailObserver)
    }

    @Test
    fun testFetchMovieDetail() {
        // Mock movie detail response
        val movieDetailResponse = MovieDetailResponse(/* provide necessary details */)

        // Mock API request URL
        val expectedUrl = "${BuildConfig.API_BASE_URL}/$MOVIE_ID?api_key=${API_KEY}"


        // Verify that the API request URL is constructed correctly
        Mockito.verify(moveListRepo).getMovieDetail(ApiRequest.callApi().url(expectedUrl))

        // Verify that the movie detail response is received
        Mockito.verify(movieDetailObserver).onChanged(movieDetailResponse)
    }

    companion object {
        private const val MOVIE_ID = 12345
        private const val API_KEY = "our_api_key_need_to_add_here"
    }
}