package com.example.moviesapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.moviesapp.R
import com.example.moviesapp.helper.NOW_PLAYING_HEADER
import com.example.moviesapp.helper.POPULAR_HEADER
import com.example.moviesapp.helper.UPCOMING_HEADER
import com.example.moviesapp.model.detail.MovieDetailResponse
import com.example.moviesapp.model.list.MovieListResponse
import com.google.gson.Gson
import com.network.networkmanager.Http
import com.network.networkmanager.ResponseListener
import org.json.JSONObject
import javax.inject.Inject

class MoveListRepo @Inject constructor(val application: Application) {

    private val _moviesList = MutableLiveData(MovieListResponse())
     val movieList: LiveData<MovieListResponse>
        get() = _moviesList

    private val _now_moviesList = MutableLiveData(MovieListResponse())
    val nowPlaying: LiveData<MovieListResponse>
        get() = _now_moviesList

    private val _movieDetailResponse = MutableLiveData(MovieDetailResponse())
    val movieDetailResponse: LiveData<MovieDetailResponse>
        get() = _movieDetailResponse

    private val _upcoming_moviesList = MutableLiveData(MovieListResponse())
    val upcomingMoviesList: LiveData<MovieListResponse>
        get() = _upcoming_moviesList


    fun getMoviesList(request:Http.RequestCall) {

       // Http.RequestCall(requestType).url("$API_BASE_URL$POPULAR_END_POINT?page=2&api_key=${getAPIToken()}")
        request.makeRequest(object : ResponseListener {
                override fun onResponse(res: JSONObject?) {
                    //Add response code here
                    val movieListResponse =
                        Gson().fromJson(res.toString(), MovieListResponse::class.java)
                    if(!movieListResponse.success){
                        _moviesList.postValue(
                            MovieListResponse(
                                status_message = movieListResponse.status_message, success = false))
                    }else{
                        movieListResponse.movieHeaderName = POPULAR_HEADER
                        _moviesList.postValue(movieListResponse)
                    }
                }
                override fun onFailure(e: Exception?) {
                    //Add failure code here
                    _moviesList.postValue(
                        MovieListResponse(
                        status_message = application.getString(R.string.error_str), success = false))
                }
            })
    }

    fun getNowPlayingList(request:Http.RequestCall) {
        request.makeRequest(object : ResponseListener {
                override fun onResponse(res: JSONObject?) {
                    //Add response code here
                    val movieListResponse =
                        Gson().fromJson(res.toString(), MovieListResponse::class.java)
                    if(!movieListResponse.success){
                        _now_moviesList.postValue(
                            MovieListResponse(
                                status_message = movieListResponse.status_message, success = false))
                    }else {
                        movieListResponse.movieHeaderName = NOW_PLAYING_HEADER
                        _now_moviesList.postValue(movieListResponse)
                    }
                }
                override fun onFailure(e: Exception?) {
                    //Add failure code here
                    _now_moviesList.postValue(MovieListResponse(
                        status_message = application.getString(R.string.error_str), success = false))
                }
            })
    }

    fun getUpComingPlayingList(request:Http.RequestCall) {
        request.makeRequest(object : ResponseListener {
                override fun onResponse(res: JSONObject?) {
                    //Add response code here
                    val movieListResponse =
                        Gson().fromJson(res.toString(), MovieListResponse::class.java)
                    if(!movieListResponse.success){
                        _upcoming_moviesList.postValue(
                            MovieListResponse(
                                status_message = movieListResponse.status_message, success = false))
                    }else {
                        movieListResponse.movieHeaderName = UPCOMING_HEADER
                        _upcoming_moviesList.postValue(movieListResponse)
                    }
                }
                override fun onFailure(e: Exception?) {
                    //Add failure code here
                    _upcoming_moviesList.postValue(MovieListResponse(
                        status_message = application.getString(R.string.error_str), success = false))
                }
            })
    }

    fun getMovieDetail(request: Http.RequestCall) {
        request.makeRequest(object : ResponseListener {
                override fun onResponse(res: JSONObject?) {
                    //Add response code here
                    val movieDetailRes =
                        Gson().fromJson(res.toString(), MovieDetailResponse::class.java)
                    if(!movieDetailRes.success){
                        _movieDetailResponse.postValue(
                            MovieDetailResponse(
                                status_message = movieDetailRes.status_message, success = false))
                    }else {
                        _movieDetailResponse.postValue(movieDetailRes)
                    }
                }
                override fun onFailure(e: Exception?) {
                    //Add failure code here
                    _movieDetailResponse.postValue(MovieDetailResponse(
                        status_message = application.getString(R.string.error_str), success = false))
                }
            })
    }
}