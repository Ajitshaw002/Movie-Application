package com.example.moviesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.moviesapp.BuildConfig
import com.example.moviesapp.helper.ApiRequest
import com.example.moviesapp.model.detail.MovieDetailResponse
import com.example.moviesapp.repository.MoveListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject
constructor(
    private val moviListRepo: MoveListRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        init {
            System.loadLibrary("keys")
        }
    }

    external fun getAPIToken(): String

    var detailResponse: LiveData<MovieDetailResponse> = moviListRepo.movieDetailResponse

    init {
        //get the movieId from the Listing page
        val movieId: Int = savedStateHandle.get<Int>("movieId") ?: 0
        //call movie Detail API
        moviListRepo.getMovieDetail(
            ApiRequest.callApi()
                .url("${BuildConfig.API_BASE_URL}/$movieId?api_key=${getAPIToken()}")
        )
    }
}

