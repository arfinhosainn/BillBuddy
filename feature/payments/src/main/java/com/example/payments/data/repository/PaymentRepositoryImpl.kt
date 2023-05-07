package com.example.payments.data.repository

import com.example.billbuddy.util.Resource
import com.example.core_database.PaymentDao
import com.example.core_domain.repository.PaymentRepository
import com.example.util.model.InvalidPaymentException
import com.example.util.model.Payment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val paymentDao: PaymentDao
) : PaymentRepository {

    @Throws(InvalidPaymentException::class)
    override suspend fun insertPayments(payments: Payment) {
        if (payments.paymentTitle.isBlank()) {
            throw InvalidPaymentException("The title of the payment can't be empty")
        }
        if (payments.paymentAmount.isBlank()) {
            throw InvalidPaymentException("The amount of the payment can't be empty")
        }
        paymentDao.insertPayments(payments = payments)
    }

    override fun getAllPayments(): Flow<Resource<List<Payment>>> = flow {
        try {
            emit(Resource.Loading())
            paymentDao.getAllPayments().collect {
                emit(Resource.Success(data = it))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Something is wrong"))
        }
    }

    override suspend fun getPaymentById(id: Int): Payment? {
        return paymentDao.getPaymentById(id = id)
    }

    override suspend fun deletePayment(payments: Payment) {
        paymentDao.deletePayment(payments = payments)
    }
}