package com.example.billbuddy.domain.repository

import com.example.billbuddy.data.local.model.Payment
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {

    suspend fun insertPayments(payments: Payment)

    fun getAllPayments(): Flow<List<Payment>>

    suspend fun getPaymentById(id: Int): Payment?

    suspend fun deletePayment(payments: Payment)


}