package com.mleval.movie.data.remote

data class MovieDetailsDto(
    val id: Int,
    val title: String,
    val overview: String,
    val runtime: Int,
    val releaseDate: String,
    val rating: Double,
    val languages: List<String>,
    val backdropPath: String
)
