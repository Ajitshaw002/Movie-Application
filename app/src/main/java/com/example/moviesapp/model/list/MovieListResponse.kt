package com.example.moviesapp.model.list

data class MovieListResponse(
    val page: Int =0,
    val results: List<Result> = emptyList(),
    val total_pages: Int=0,
    val total_results: Int=0,
    var movieHeaderName:String="",
    val status_code: Int=0,
    val status_message: String="",
    val success: Boolean=true
)