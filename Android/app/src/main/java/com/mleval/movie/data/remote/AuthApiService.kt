package com.mleval.movie.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    suspend fun singIn(@Body request: SignInRequest): SignInResponse

    @POST("users/add")
    suspend fun singUp(@Body request: SignUpRequest): SignUpResponse
}