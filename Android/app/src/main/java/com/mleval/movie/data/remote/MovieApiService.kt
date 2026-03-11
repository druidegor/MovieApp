package com.mleval.movie.data.remote

import com.mleval.movie.BuildConfig
import retrofit2.http.GET


interface MovieApiService {

    @GET("movie/popular?api_key=${BuildConfig.MOVIE_API_KEY}")
    suspend fun loadPopularMovies(): MovieResponseDto

    @GET("trending/movie/week?api_key=${BuildConfig.MOVIE_API_KEY}")
    suspend fun loadTrendingMovies(): MovieResponseDto

    @GET("movie/top_rated?api_key=${BuildConfig.MOVIE_API_KEY}")
    suspend fun loadTopRatedMovies(): MovieResponseDto

}