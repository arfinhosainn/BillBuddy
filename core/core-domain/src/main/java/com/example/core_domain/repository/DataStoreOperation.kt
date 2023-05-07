package com.example.core_domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperation {

    suspend fun writeOnBoardingKeyToDataStore(completed: Boolean)

    suspend fun readOnBoardingKeyFormDataStore(): Flow<Boolean>

    suspend fun writeCurrencyToDataStore(currency: String)

    suspend fun readCurrencyFromDataStore(): Flow<String>

    suspend fun writeBudgetLimitToDataStore(amount: Double)

    suspend fun readExpenseLimitFromDataStore(): Flow<Double>

    suspend fun readLimitKeyFromDataStore(): Flow<Boolean>

    suspend fun writeLimitKeyToDataStore(enables: Boolean)

    suspend fun writeLimitDurationToDataStore(duration: Int)

    suspend fun readLimitDurationFromDataStore(): Flow<Int>

    suspend fun eraseDataStore()
}