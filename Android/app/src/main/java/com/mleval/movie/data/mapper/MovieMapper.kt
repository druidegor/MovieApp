package com.mleval.movie.data.mapper

import com.mleval.movie.data.remote.MovieDto
import com.mleval.movie.domain.entity.Movie

fun List<MovieDto>.toEntities(): List<Movie> {
    return map {
        Movie(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath
        )
    }
}