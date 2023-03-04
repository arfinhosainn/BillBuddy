package com.example.billbuddy.data.local.repository

import com.example.billbuddy.data.local.PaymentDao
import com.example.billbuddy.data.local.model.InvalidPaymentException
import com.example.billbuddy.data.local.model.Payment
import com.example.billbuddy.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val paymentDao: PaymentDao
) : PaymentRepository {

    @Throws(InvalidPaymentException::class)
    override suspend fun insertPayments(payments: Payment) {
        if (payments.paymentTitle.isBlank()) {
            throw InvalidPaymentException("The title of the payment can't be empty")
        }
        if (payments.paymentAmount == null){
            throw InvalidPaymentException("The amount of the payment can't be empty")
        }
        paymentDao.insertPayments(payments = payments)
    }

    override fun getAllPayments(): Flow<List<Payment>> {
        return paymentDao.getAllPayments()
    }

    override suspend fun getPaymentById(id: Int): Payment? {
        return paymentDao.getPaymentById(id = id)
    }

    override suspend fun deletePayment(payments: Payment) {
        paymentDao.deletePayment(payments = payments)
    }
}