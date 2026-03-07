package com.mleval.movie.domain.usecase

import com.mleval.movie.domain.repository.AuthRepository
import javax.inject.Inject

class RemoveTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke() {
        authRepository.removeToken()
    }
}