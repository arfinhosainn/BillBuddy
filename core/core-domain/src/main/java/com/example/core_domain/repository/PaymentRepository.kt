package com.example.core_domain.repository

import com.example.util.model.Payment
import com.example.billbuddy.util.Resource
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {

    suspend fun insertPayments(payments: Payment)

    fun getAllPayments(): Flow<Resource<List<Payment>>>

    suspend fun getPaymentById(id: Int): Payment?

    suspend fun deletePayment(payments: Payment)
}