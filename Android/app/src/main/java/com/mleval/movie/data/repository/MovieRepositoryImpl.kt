package com.mleval.movie.data.repository

import com.mleval.movie.data.mapper.toEntities
import com.mleval.movie.data.mapper.toEntity
import com.mleval.movie.data.remote.MovieApiService
import com.mleval.movie.domain.entity.Movie
import com.mleval.movie.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService
): MovieRepository {

    override suspend fun getPopularMovies(): List<Movie> {
        return movieApiService.loadPopularMovies().toEntities()
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        return movieApiService.loadTopRatedMovies().toEntities()
    }

    override suspend fun getTrendingMovies(): List<Movie> {
        return movieApiService.loadTrendingMovies().toEntities()
    }

}