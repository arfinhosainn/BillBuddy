package com.example.billbuddy.data.local.repository

import com.example.billbuddy.data.local.TransactionDao
import com.example.billbuddy.data.local.model.TransactionDto
import com.example.billbuddy.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {
    override suspend fun insertTransaction(monthlyExpenses: TransactionDto) {
        dao.insertTransaction(transaction = monthlyExpenses)
    }

    override suspend fun getMonthlyTransaction(entryDate: String): Flow<List<TransactionDto>> {
        return dao.getMonthlyExpTransaction()
    }

    override fun getAllTransaction(): Flow<List<TransactionDto>> {
        return dao.getAllTransaction()
    }

    override fun eraseTransaction() {
        dao.eraseTransaction()
    }

    override fun getCurrentMonthExpTransaction(): Flow<List<TransactionDto>> {
        return dao.getCurrentMonthExpTransaction()
    }

    override fun get3DayTransaction(transactionType: String): Flow<List<TransactionDto>> {
        return dao.get3DayTransaction(transactionType = transactionType)
    }

    override fun get14DayTransaction(transactionType: String): Flow<List<TransactionDto>> {

        return get14DayTransaction(transactionType = transactionType)
    }

    override fun getStartOfMonthTransaction(transactionType: String): Flow<List<TransactionDto>> {

        return getStartOfMonthTransaction(transactionType = transactionType)
    }

    override fun getLastMonthTransaction(transactionType: String): Flow<List<TransactionDto>> {

        return dao.getLastMonthTransaction(transactionType = transactionType)
    }

    override fun getTransactionByType(transactionType: String): Flow<List<TransactionDto>> {
        return dao.getTransactionByType(transactionType = transactionType)
    }
}