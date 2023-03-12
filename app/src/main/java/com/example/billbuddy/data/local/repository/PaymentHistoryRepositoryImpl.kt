package com.example.billbuddy.data.local.repository

import com.example.billbuddy.data.local.PaymentHistoryDao
import com.example.billbuddy.data.local.model.PaymentHistory
import com.example.billbuddy.data.local.model.relation.PaymentAndPaymentHistory
import com.example.billbuddy.domain.repository.PaymentHistoryRepository
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