package com.mleval.movie.data.remote

import com.mleval.movie.BuildConfig
import com.mleval.movie.domain.entity.Trailer
import retrofit2.http.GET
import retrofit2.http.Path


interface MovieApiService {

    @GET("movie/popular?api_key=${BuildConfig.MOVIE_API_KEY}")
    suspend fun loadPopularMovies(): MovieResponseDto

    @GET("trending/movie/week?api_key=${BuildConfig.MOVIE_API_KEY}")
    suspend fun loadTrendingMovies(): MovieResponseDto

    @GET("movie/top_rated?api_key=${BuildConfig.MOVIE_API_KEY}")
    suspend fun loadTopRatedMovies(): MovieResponseDto

    @GET("movie/{movie_id}?api_key=${BuildConfig.MOVIE_API_KEY}")
    suspend fun loadMovieDetails(
        @Path("movie_id") id: Int
    ): MovieDetailsDto

    @GET("movie/{movie_id}/credits?api_key=${BuildConfig.MOVIE_API_KEY}")
    suspend fun loadMovieActors(
        @Path("movie_id") id: Int
    ): List<ActorDto>

    @GET("movie/{movie_id}/videos?api_key=${BuildConfig.MOVIE_API_KEY}")
    suspend fun loadMovieTrailer(
        @Path("movie_id") id: Int
    ): Trailer
}