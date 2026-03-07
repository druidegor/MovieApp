package com.mleval.movie.data.mapper

import com.mleval.movie.data.remote.SignInRequest
import com.mleval.movie.data.remote.SignInResponse
import com.mleval.movie.data.remote.SignUpRequest
import com.mleval.movie.data.remote.SignUpResponse
import com.mleval.movie.domain.entity.AuthResponse
import com.mleval.movie.domain.entity.SignInData
import com.mleval.movie.domain.entity.SignUpData

fun SignInResponse.toDbModel(): AuthResponse {
    return AuthResponse(
        token = this.accessToken
    )
}

fun SignUpResponse.toDbModel(): AuthResponse {
    return AuthResponse(
        token = this.accessToken
    )
}

fun SignInData.toEntity(): SignInRequest {
    return SignInRequest(
        email = email,
        password = password,
    )
}

fun SignUpData.toEntity(): SignUpRequest {
    return SignUpRequest(
        firstName = firstName,
        lastName = lastName,
        age = age
    )
}