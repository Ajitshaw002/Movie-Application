package com.example.moviesapp.helper

import com.network.networkmanager.Http

object ApiRequest {
    fun callApi():Http.RequestCall{
        return Http.RequestCall(Http.GET)
    }
}