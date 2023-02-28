package com.example.billbuddy.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperation {

    suspend fun writeOnBoardingKeyToDataStore(completed: Boolean)

    suspend fun readOnBoardingKeyFormDataStore(): Flow<Boolean>

    suspend fun writeCurrencyToDataStore(currency: String)

    suspend fun readCurrencyFromDataStore(): Flow<String>

    suspend fun writeBudgetLimitToDataStore(amount: Double)

    suspend fun readBudgetLimitFromDataStore(): Flow<Double>

    suspend fun readLimitKeyFromDataStore(): Flow<Boolean>

    suspend fun writeLimitKeyFromDataStore(enables: Boolean)

    suspend fun writeLimitDurationToDataStore(duration: Int)

    suspend fun readLimitDurationFromDataStore(): Flow<Int>

    suspend fun eraseDataStore()

}