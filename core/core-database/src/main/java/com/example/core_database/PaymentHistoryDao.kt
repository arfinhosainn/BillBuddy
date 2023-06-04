package com.example.core_database

import androidx.room.*
import com.example.util.model.PaymentHistory
import com.example.billbuddy.data.local.model.relation.PaymentAndPaymentHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaymentHistory(paymentHistory: PaymentHistory)

    @Transaction
    @Query("SELECT * FROM payment_history_table")
    fun getPaymentHistory(): Flow<List<PaymentAndPaymentHistory>>
}