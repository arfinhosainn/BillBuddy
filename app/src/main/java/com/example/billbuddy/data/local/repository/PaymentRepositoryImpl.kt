package com.example.billbuddy.data.local.repository

import android.util.Log
import androidx.compose.runtime.collectAsState
import com.example.billbuddy.data.local.PaymentDao
import com.example.billbuddy.data.local.model.InvalidPaymentException
import com.example.billbuddy.data.local.model.Payment
import com.example.billbuddy.domain.repository.PaymentRepository
import com.example.billbuddy.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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
//        if (payments.paymentAmount == null){
//            throw InvalidPaymentException("The amount of the payment can't be empty")
//        }
        paymentDao.insertPayments(payments = payments)
    }

    override fun getAllPayments(): Flow<Resource<List<Payment>>> = flow {
        try {
            emit(Resource.Loading())
            paymentDao.getAllPayments().collect {
                emit(Resource.Success(data = it))
                Log.d("newpaymentsss", "getAllPayments: $it")
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