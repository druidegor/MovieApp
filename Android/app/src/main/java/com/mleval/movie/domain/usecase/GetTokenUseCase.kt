package com.mleval.movie.domain.usecase

import com.mleval.movie.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(): Flow<String> {
        return authRepository.getToken()
    }
}