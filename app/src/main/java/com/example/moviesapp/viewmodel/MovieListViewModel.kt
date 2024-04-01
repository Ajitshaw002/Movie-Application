package com.example.moviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.BuildConfig.API_BASE_URL
import com.example.moviesapp.helper.ApiRequest
import com.example.moviesapp.helper.NOW_PLAYING_END_POINT
import com.example.moviesapp.helper.POPULAR_END_POINT
import com.example.moviesapp.helper.UPCOMING_MOVIES_END_POINT
import com.example.moviesapp.model.list.MovieListResponse
import com.example.moviesapp.repository.MoveListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject
constructor(private val moveListRepo: MoveListRepo) : ViewModel() {

    companion object {
        init {
            System.loadLibrary("keys")
        }
    }

    external  fun getAPIToken(): String
    var movieList: LiveData<MovieListResponse> = moveListRepo.movieList
    var movieListNowPlaying: LiveData<MovieListResponse> = moveListRepo.nowPlaying
    var movieListUpComing: LiveData<MovieListResponse> = moveListRepo.upcomingMoviesList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            //get popular movies
            getPopularMovies()
            //get Upcoming movies
            getUpcomingPlayingMovies()

            //call now playing API
            getNowPlayingMovies()
        }
    }

    private fun getPopularMovies() {
        moveListRepo.getMoviesList(
            ApiRequest.callApi().url("$API_BASE_URL$POPULAR_END_POINT?page=2&api_key=${getAPIToken()}"))
    }

    private fun getNowPlayingMovies() {
        moveListRepo.getNowPlayingList(
            ApiRequest.callApi().url("$API_BASE_URL$NOW_PLAYING_END_POINT?api_key=${getAPIToken()}")
        )
    }

    private fun getUpcomingPlayingMovies() {
        moveListRepo.getUpComingPlayingList(
            ApiRequest.callApi().url("$API_BASE_URL$UPCOMING_MOVIES_END_POINT?page=3&api_key=${getAPIToken()}"))
    }

}