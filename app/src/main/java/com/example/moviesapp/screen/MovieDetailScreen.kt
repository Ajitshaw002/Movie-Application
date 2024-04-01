package com.example.moviesapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.moviesapp.helper.calculateUserScorePercentage
import com.example.moviesapp.ui.AnimatedCircularProgressIndicator
import com.example.moviesapp.ui.theme.GreenOr
import com.example.moviesapp.ui.theme.GreenSuperLight
import com.example.moviesapp.ui.theme.RedOr
import com.example.moviesapp.ui.theme.RedSuperLight
import com.example.moviesapp.ui.theme.YellowOr
import com.example.moviesapp.ui.theme.YellowSuperLight
import com.example.moviesapp.viewmodel.MovieDetailViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetailScreen() {
    val movieDetailViewModel: MovieDetailViewModel = hiltViewModel()

    val detailState by movieDetailViewModel.detailResponse.observeAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (detailState!!.original_title.isEmpty()) {
            CircularProgressIndicator(progress = 0.5f)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(1) {
                    Column {
                        val progressBackgroundColor: Color?
                        val progressIndicatorColor: Color?
                        val completedColor: Color?
                        val progress = calculateUserScorePercentage(
                            detailState!!.vote_average
                        )
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
                        Box {
                            GlideImage(
                                model = "https://image.tmdb.org/t/p/w500/${detailState!!.backdrop_path}",
                                contentDescription = "Movie images",
                                failure = placeholder(ColorPainter(Color.Red)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(218.dp)
                            )
                            Card(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .width(133.dp)
                                    .padding(10.dp, 15.dp)
                                    .height(170.dp),
                                shape = RoundedCornerShape(8.dp),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 10.dp
                                )
                            ) {
                                GlideImage(
                                    model = "https://image.tmdb.org/t/p/w500/${detailState!!.poster_path}",
                                    contentDescription = "Movie images",
                                    failure = placeholder(ColorPainter(Color.Red)),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )

                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    text = detailState!!.original_title,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .padding(bottom = 8.dp)
                                        .align(Alignment.CenterVertically)
                                )

                                Box(
                                    modifier = Modifier
                                        .height(65.dp)
                                        .width(65.dp)


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

                            Text(
                                text = detailState!!.overview,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black,
                                modifier = Modifier.padding(bottom = 16.dp, top = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

