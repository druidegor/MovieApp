package com.mleval.movie.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    @SerialName("username")
    val email: String,
    val password: String,
    val expiresInMins: Int = 30
)