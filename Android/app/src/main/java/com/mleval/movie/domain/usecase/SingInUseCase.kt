package com.mleval.movie.domain.usecase

import com.mleval.movie.domain.entity.AuthResponse
import com.mleval.movie.domain.entity.SignInData
import com.mleval.movie.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class SingInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(signInData: SignInData): AuthResponse {
        delay(5000)
        return authRepository.signIn(signInData)
    }
}