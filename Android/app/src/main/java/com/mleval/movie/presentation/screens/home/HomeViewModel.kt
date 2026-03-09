package com.mleval.movie.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mleval.movie.domain.entity.Movie
import com.mleval.movie.domain.usecase.GetPopularMoviesUseCase
import com.mleval.movie.domain.usecase.GetTopRatedMoviesUseCase
import com.mleval.movie.domain.usecase.GetTrendingMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
): ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { previousState ->
                previousState.copy(
                    heroMovie = getTrendingMoviesUseCase().first(),
                    popularMovies = getPopularMoviesUseCase(),
                    trendingMovies = getTrendingMoviesUseCase(),
                    topRatedMovies = getTopRatedMoviesUseCase()
                )

            }
        }

    }

}

data class HomeState (
    val heroMovie: Movie = Movie(0,"",""),
    val popularMovies: List<Movie> = listOf(),
    val trendingMovies: List<Movie> = listOf(),
    val topRatedMovies: List<Movie> = listOf()
)
