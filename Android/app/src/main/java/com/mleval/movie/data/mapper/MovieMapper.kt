package com.mleval.movie.data.mapper

import android.annotation.SuppressLint
import com.mleval.movie.data.remote.MovieDto
import com.mleval.movie.domain.entity.Movie

@SuppressLint("DefaultLocale")
fun List<MovieDto>.toEntities(): List<Movie> {
    return map {
        Movie(
            id = it.id,
            title = it.title,
            backdropPath = "https://image.tmdb.org/t/p/w342" + it.backdropPath,
            rating = String.format("%.1f",it.rating).toDouble()
        )
    }
}