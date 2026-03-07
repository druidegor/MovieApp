package com.mleval.movie.domain.repository

import com.mleval.movie.domain.entity.AuthResponse
import com.mleval.movie.domain.entity.SignInData
import com.mleval.movie.domain.entity.SignUpData
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signIn(signInData: SignInData): AuthResponse

    suspend fun signUp(signUpData: SignUpData): AuthResponse

    fun getToken(): Flow<String>

    suspend fun addToken(token: String)

    suspend fun removeToken()
}