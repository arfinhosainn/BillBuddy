package com.example.billbuddy.domain.repository

import com.example.billbuddy.data.local.model.TransactionDto
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun insertTransaction(monthlyExpenses: TransactionDto)

    suspend fun getMonthlyTransaction(entryDate: String): Flow<List<TransactionDto>>

    fun getAllTransaction(): Flow<List<TransactionDto>>

    fun eraseTransaction()

    fun getCurrentMonthExpTransaction(): Flow<List<TransactionDto>>

    fun get3DayTransaction(transactionType: String): Flow<List<TransactionDto>>

    fun get14DayTransaction(transactionType: String): Flow<List<TransactionDto>>

    fun getStartOfMonthTransaction(transactionType: String): Flow<List<TransactionDto>>

    fun getLastMonthTransaction(transactionType: String): Flow<List<TransactionDto>>

    fun getTransactionByType(transactionType: String): Flow<List<TransactionDto>>
}