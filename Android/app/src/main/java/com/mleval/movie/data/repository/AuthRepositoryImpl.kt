package com.mleval.movie.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mleval.movie.data.mapper.toDbModel
import com.mleval.movie.data.mapper.toEntity
import com.mleval.movie.data.remote.AuthApiService
import com.mleval.movie.domain.entity.AuthResponse
import com.mleval.movie.domain.entity.SignInData
import com.mleval.movie.domain.entity.SignUpData
import com.mleval.movie.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tokens")
class AuthRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val api: AuthApiService
): AuthRepository {

    private val tokenKey = stringPreferencesKey(name = "token")

    override suspend fun signIn(signInData: SignInData): AuthResponse {
        return api.singIn(signInData.toEntity()).toDbModel()
    }

    override suspend fun signUp(signUpData: SignUpData): AuthResponse {
        return api.singUp(signUpData.toEntity()).toDbModel()
    }

    override fun getToken(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[tokenKey] ?: ""
        }
    }

    override suspend fun removeToken() {
        context.dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences.remove(tokenKey)
            }
        }
    }

    override suspend fun addToken(token: String) {
        context.dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[tokenKey] = token
            }
        }
    }
}