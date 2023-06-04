package com.example.core_domain.repository

import com.example.util.model.PaymentHistory
import com.example.billbuddy.data.local.model.relation.PaymentAndPaymentHistory
import kotlinx.coroutines.flow.Flow

interface PaymentHistoryRepository {

    suspend fun insertPaymentHistory(paymentHistory: PaymentHistory)
    fun getPaymentHistory(): Flow<List<PaymentAndPaymentHistory>>
}