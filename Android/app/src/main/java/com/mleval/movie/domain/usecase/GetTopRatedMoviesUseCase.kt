package com.mleval.movie.domain.usecase

import com.mleval.movie.domain.entity.Movie
import com.mleval.movie.domain.repository.MovieRepository
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(): List<Movie> {
        return movieRepository.getTopRatedMovies()
    }
}