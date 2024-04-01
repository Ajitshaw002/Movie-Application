package com.example.moviesapp.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.moviesapp.BuildConfig
import com.example.moviesapp.helper.calculateUserScorePercentage
import com.example.moviesapp.model.list.Result
import com.example.moviesapp.ui.AnimatedCircularProgressIndicator
import com.example.moviesapp.ui.theme.GreenOr
import com.example.moviesapp.ui.theme.GreenSuperLight
import com.example.moviesapp.ui.theme.RedOr
import com.example.moviesapp.ui.theme.RedSuperLight
import com.example.moviesapp.ui.theme.YellowOr
import com.example.moviesapp.ui.theme.YellowSuperLight
import com.example.moviesapp.viewmodel.MovieListViewModel


@Composable
fun MovieListScreen(onCLick: (movieList: Result) -> Unit) {

    //get the view model from Hilt
    val movieListViewModel: MovieListViewModel = hiltViewModel()

    //observe the live data from view model
    val countState by movieListViewModel.movieList.observeAsState()
    val countStateNowPlaying by movieListViewModel.movieListNowPlaying.observeAsState()
    val movieListUpComing by movieListViewModel.movieListUpComing.observeAsState()


    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (countStateNowPlaying!!.movieHeaderName == "") {
            CircularProgressIndicator(progress = 0.5f)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {

                items(1) {
                    //Popular movies
                    if (!countState!!.success) {
                        Toast.makeText(
                            context,
                            countState!!.status_message, Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Text(
                            text = countState!!.movieHeaderName,
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(15.dp, 15.dp, 8.dp, 0.dp)
                        )
                        LazyRow {
                            items(countState!!.results.distinct()) {
                                MovieListImageCell(it, onCLick)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (!movieListUpComing!!.success) {
                        Toast.makeText(
                            context,
                            countState!!.status_message, Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        //Upcoming movies
                        Text(
                            text = movieListUpComing!!.movieHeaderName,
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(15.dp, 15.dp, 8.dp, 0.dp)
                        )
                        LazyRow {
                            items(movieListUpComing!!.results.distinct()) {
                                MovieListImageCell(it, onCLick)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    if (!countStateNowPlaying!!.success) {
                        Toast.makeText(
                            context,
                            countState!!.status_message, Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        //Now Playing Movies
                        Text(
                            text = countStateNowPlaying!!.movieHeaderName,
                            fontSize = 20.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(15.dp, 10.dp, 8.dp, 8.dp)
                        )
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.height(400.dp),
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.SpaceAround,
                        ) {
                            items(countStateNowPlaying!!.results.distinct()) {
                                MovieListItem(it, onCLick)
                            }
                        }
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieListImageCell(movieList: Result, onCLick: (movieList: Result) -> Unit) {
    //Design for Only Image
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 15.dp)
            .height(170.dp)
            .clickable {
                onCLick(movieList)
            },

        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        GlideImage(
            model = "${BuildConfig.IMAGE_BASE_URL}${movieList.poster_path}",
            contentDescription = "Movie images",
            failure = placeholder(ColorPainter(Color.Red)),
            modifier = Modifier.fillMaxSize()
        )

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieListItem(movieList: Result, onCLick: (movieList: Result) -> Unit) {
    ///Design for Image, Title & Release date

    val progressBackgroundColor: Color?
    val progressIndicatorColor: Color?
    val completedColor: Color?
    val progress = calculateUserScorePercentage(movieList.vote_average)
    if (progress >= 70) {
        //green
        progressBackgroundColor = GreenSuperLight
        progressIndicatorColor = GreenOr
        completedColor = GreenSuperLight
    } else if (progress >= 40) {
        //yellow
        progressBackgroundColor = YellowSuperLight
        progressIndicatorColor = YellowOr
        completedColor = YellowSuperLight
    } else {
        //red
        progressBackgroundColor = RedSuperLight
        progressIndicatorColor = RedOr
        completedColor = RedSuperLight
    }
    Box(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth()
        .clickable {
            onCLick(movieList)
        }

    ) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                GlideImage(
                    model = "${BuildConfig.IMAGE_BASE_URL}${movieList.poster_path}",
                    contentDescription = "Movie images",
                    failure = placeholder(ColorPainter(Color.Red)),
                    modifier = Modifier.fillMaxSize()
                )

            }


            Row(modifier = Modifier.padding(0.dp, 8.dp)) {
                Column(
                    Modifier
                        .weight(2f)
                        .padding(0.dp, 10.dp)
                ) {
                    Text(
                        text = movieList.title,
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)
                    )
                    Text(
                        text = movieList.release_date,
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(0.dp, 5.dp),
                    )
                }
                Box(
                    modifier = Modifier
                        .height(45.dp)
                        .width(45.dp)


                ) {
                    AnimatedCircularProgressIndicator(
                        currentValue = progress.toInt(),
                        maxValue = 100,
                        progressBackgroundColor = progressBackgroundColor,
                        progressIndicatorColor = progressIndicatorColor,
                        completedColor = completedColor
                    )
                }
            }
        }
    }
}