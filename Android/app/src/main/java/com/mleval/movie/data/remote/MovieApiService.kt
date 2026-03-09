package com.mleval.movie.data.remote

import retrofit2.http.GET


interface MovieApiService {

    @GET("/movie/popular?api_key=e4466b01cf38d7057cfac9b1817410e9")
    suspend fun loadPopularMovies(): List<MovieDto>

    @GET("trending/movie/week")
    suspend fun loadTrendingMovies(): List<MovieDto>

    @GET("movie/top_rated")
    suspend fun loadTopRatedMovies(): List<MovieDto>
}