package com.example.moviesapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesapp.screen.MovieDetailScreen
import com.example.moviesapp.screen.MovieListScreen
import com.example.moviesapp.ui.theme.MoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @SuppressLint("SuspiciousIndentation", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesAppTheme {
                Column {
                    App()
                }
            }
        }
    }

    @Composable
    fun App() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "movieList") {
            composable(route = "movieList") {
                MovieListScreen {
                    navController.navigate("detailScreen/${it.id}")
                }
            }
            composable(route = "detailScreen/{movieId}", arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                }
            )
            ) {
                MovieDetailScreen()
            }
        }
    }
}
