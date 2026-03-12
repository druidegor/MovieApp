package com.mleval.movie.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponseDto (
    @SerialName("page") val page: Int,
    @SerialName("results")  val results: List<MovieDto>,
    @SerialName("total_pages")  val total_pages: Int,
    @SerialName("total_results")  val total_results: Int
)

