package com.example.billbuddy.di

import com.example.billbuddy.data.local.repository.FirebaseAuthRepositoryImpl
import com.example.billbuddy.domain.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseAuthRepository(auth: FirebaseAuth): FirebaseAuthRepository {
        return FirebaseAuthRepositoryImpl(auth)
    }
}