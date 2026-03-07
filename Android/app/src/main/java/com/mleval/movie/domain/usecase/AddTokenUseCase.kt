package com.mleval.movie.domain.usecase

import com.mleval.movie.domain.repository.AuthRepository
import javax.inject.Inject

class AddTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(token: String) {
        authRepository.addToken(token)
    }
}