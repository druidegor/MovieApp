@file:OptIn(ExperimentalMaterial3Api::class)

package com.mleval.movie.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mleval.movie.R
import com.mleval.movie.domain.entity.Movie

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onUserClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMovieClick: (Int) -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
       topBar =  {
           HomeTopAppBar(
               scrollBehavior = scrollBehavior,
               onSearchClick = {
                    onSearchClick()
               },
               onAccountClick = {
                    onUserClick()
               }
           )
       }
    ) { innerPadding ->

        val state by viewModel.state.collectAsState()

        when(val current = state) {
            HomeScreenState.Error -> {
                ErrorScreen(
                    modifier = Modifier.padding(innerPadding),
                    onRetry = {
                        viewModel.loadMovies()
                    }
                )
            }
            HomeScreenState.Loading -> {
                LoadingScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is HomeScreenState.Success -> {
                    HomeContent(
                        modifier = Modifier.padding(innerPadding),
                        scrollBehavior = scrollBehavior,
                        state = current,
                        onMovieClick = onMovieClick
                    )

                }
            }
        }
    }

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    state: HomeScreenState.Success,
    onMovieClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeroCard(
                movie = state.heroMovie,
                onMovieClick = { id ->
                     onMovieClick(id)
                }
            )
        }

        item {
            MovieSection(
                title = stringResource(R.string.trending_movies),
                movies = state.trendingMovies,
                onMovieClick = onMovieClick
            )
        }

        item {
            MovieSection(
                title = stringResource(R.string.popular_movies),
                movies = state.popularMovies,
                onMovieClick = onMovieClick
            )
        }

        item {
            MovieSection(
                title = stringResource(R.string.top_rated_movies),
                movies = state.topRatedMovies,
                onMovieClick = onMovieClick
            )
        }
    }
}
@Composable
private fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchClick: () -> Unit,
    onAccountClick: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        scrollBehavior = scrollBehavior,
        actions = {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
                    .clickable {
                        onSearchClick()
                    }
                    .background(MaterialTheme.colorScheme.tertiary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = stringResource(R.string.search_film),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
                    .clickable {
                        onAccountClick()
                    }
                    .background(MaterialTheme.colorScheme.tertiary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_user),
                    contentDescription = stringResource(R.string.personal_account),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
private fun HeroCard(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 40.dp)
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 13f)
                    .heightIn(max = 400.dp)
                    .clip(RoundedCornerShape(48.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.backdropPath)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.heromovie_image),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y = 40.dp)
                    .clip(RoundedCornerShape(48.dp))
                    .background(MaterialTheme.colorScheme.tertiary)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.trending),
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = movie.title,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row {
                            Text(
                                text = movie.rating.toString(),
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = stringResource(R.string.star)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(60.dp)
                            .clip(CircleShape)
                            .clickable {
                                onMovieClick(movie.id)
                            }
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(35.dp),
                            painter = painterResource(R.drawable.ic_watch),
                            contentDescription = stringResource(R.string.watch_film),
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                }
            }
        }

    }
}

@Composable
private fun MovieCard(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClick: (Int) -> Unit
) {
    Column(
        modifier = modifier.width(160.dp),
    ) {
        Box{
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(39.dp)),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.backdropPath)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.film_image),
                contentScale = ContentScale.Crop
            )

            IconButton(
                onClick = {
                    onMovieClick(movie.id)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(18.dp)
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.tertiary, CircleShape),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_play_arrow),
                    contentDescription = stringResource(R.string.play_movie),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Spacer(Modifier.height(10.dp))

        Text(
            text = movie.title,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            minLines = 2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun MoviesRow(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = movies,
            key = { it.id }
        ) { movie ->
            MovieCard(
                movie = movie,
                onMovieClick = {id ->
                    onMovieClick(id)
                }
            )
        }
    }
}

@Composable
private fun MovieSection(
    modifier: Modifier = Modifier,
    title: String,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    Text(
        modifier = modifier,
        text = title,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(Modifier.height(16.dp))
    MoviesRow(
        movies = movies,
        onMovieClick = onMovieClick
    )
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(stringResource(R.string.something_went_wrong))

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onRetry
        ) {
            Text(stringResource(R.string.retry))
        }
    }
}