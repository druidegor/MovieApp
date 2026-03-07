package com.mleval.movie.domain.usecase

import com.mleval.movie.domain.entity.AuthResponse
import com.mleval.movie.domain.entity.SignUpData
import com.mleval.movie.domain.repository.AuthRepository
import javax.inject.Inject

class SingUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(signUpData: SignUpData): AuthResponse {
        return authRepository.signUp(signUpData)
    }
}