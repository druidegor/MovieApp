package com.mleval.movie.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("backdrop_path") val backdropPath: String = "",
    @SerialName("vote_average") val rating: Double
)