package com.example.moviesapp.helper

fun calculateUserScorePercentage(voteAverage: Double): Double {
    // Normalize voteAverage to a scale of 0 to 10
    val normalizedVoteAverage = voteAverage / 10
    return normalizedVoteAverage * 100
}

const val POPULAR_END_POINT = "/popular"
const val NOW_PLAYING_END_POINT = "/now_playing"
const val UPCOMING_MOVIES_END_POINT = "/upcoming"


const val UPCOMING_HEADER = "UpComing Movies"
const val POPULAR_HEADER = "Popular Movies"
const val NOW_PLAYING_HEADER = "Now Playing Movies"