package com.mleval.movie.domain.repository

import com.mleval.movie.domain.entity.Movie

interface MovieRepository {

    suspend fun getPopularMovies(): List<Movie>

    suspend fun getTrendingMovies(): List<Movie>

    suspend fun getTopRatedMovies(): List<Movie>
}