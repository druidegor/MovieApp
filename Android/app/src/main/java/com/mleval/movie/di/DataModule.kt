package com.mleval.movie.di

import com.mleval.movie.data.remote.AuthApiService
import com.mleval.movie.data.repository.AuthRepositoryImpl
import com.mleval.movie.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun provideAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    companion object {

        @Provides
        @Singleton
        fun provideJson(): Json {
            return Json{
                ignoreUnknownKeys = true
                coerceInputValues = true
            }
        }

        @Provides
        @Singleton
        fun provideConverterFactory(
            json: Json
        ): Converter.Factory {
            return json.asConverterFactory("application/json".toMediaType())
        }

        @Provides
        @Singleton
        fun provideRetrofit(
            converter: Converter.Factory
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .addConverterFactory(converter)
                .build()
        }

        @Provides
        @Singleton
        fun provideApiService(
            retrofit: Retrofit
        ): AuthApiService {
            return retrofit.create()
        }
    }
}