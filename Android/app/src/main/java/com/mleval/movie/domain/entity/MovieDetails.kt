package com.mleval.movie.domain.entity

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val runtime: Int,
    val releaseDate: String,
    val rating: Double,
    val languages: List<String>,
    val backdropPath: String
)
