package com.example.core_database

import com.example.util.model.PaymentHistory
import com.example.billbuddy.data.local.model.relation.PaymentAndPaymentHistory
import com.example.core_domain.repository.PaymentHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaymentHistoryRepositoryImpl @Inject constructor(
    private val paymentHistoryDao: PaymentHistoryDao
) : PaymentHistoryRepository {
    override suspend fun insertPaymentHistory(paymentHistory: PaymentHistory) {
        paymentHistoryDao.insertPaymentHistory(paymentHistory)
    }

    override fun getPaymentHistory(): Flow<List<PaymentAndPaymentHistory>> {
        return paymentHistoryDao.getPaymentHistory()
    }
}