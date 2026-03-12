package com.mleval.movie.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mleval.movie.domain.entity.Movie
import com.mleval.movie.domain.usecase.GetPopularMoviesUseCase
import com.mleval.movie.domain.usecase.GetTopRatedMoviesUseCase
import com.mleval.movie.domain.usecase.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
): ViewModel() {

    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _state.value = HomeScreenState.Loading
            try {


                    val trendingDeferred = async { getTrendingMoviesUseCase() }
                    val topRatedDeferred = async { getTopRatedMoviesUseCase() }
                    val popularDeferred = async { getPopularMoviesUseCase() }

                    val trendingMovies = trendingDeferred.await()
                    val topRatedMovies = topRatedDeferred.await()
                    val popularMovies = popularDeferred.await()

                    _state.value = HomeScreenState.Success(
                        heroMovie = trendingMovies.random(),
                        popularMovies = popularMovies,
                        trendingMovies = trendingMovies,
                        topRatedMovies = topRatedMovies
                    )
            } catch (e: Exception) {
                _state.value = HomeScreenState.Error
            }

        }
    }

}

sealed interface HomeScreenState{

    object Loading: HomeScreenState

    object Error: HomeScreenState

    data class Success (
        val heroMovie: Movie = Movie(0,"","", 0.0),
        val popularMovies: List<Movie> = listOf(),
        val trendingMovies: List<Movie> = listOf(),
        val topRatedMovies: List<Movie> = listOf(),
    ): HomeScreenState
}



