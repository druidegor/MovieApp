package com.mleval.movie.data.repository

import android.util.Log
import com.mleval.movie.data.mapper.toEntities
import com.mleval.movie.data.mapper.toEntity
import com.mleval.movie.data.remote.MovieApiService
import com.mleval.movie.domain.entity.Movie
import com.mleval.movie.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApiService: MovieApiService
): MovieRepository {


    override suspend fun getPopularMovies(): List<Movie> {
        return movieApiService.loadPopularMovies().results.toEntities()
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        return movieApiService.loadTopRatedMovies().results.toEntities()
    }

    override suspend fun getTrendingMovies(): List<Movie> {

        return withContext(Dispatchers.IO) {
            movieApiService.loadTrendingMovies().results.toEntities()
        }
    }

}