package com.mleval.movie.domain.entity

data class SignUpData(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val age: Int
)