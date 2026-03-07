package com.mleval.movie.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    @SerialName("email") val email: String = " ",
    @SerialName("firstName") val name: String = " ",
    @SerialName("accessToken") val accessToken: String = " ",
    )
