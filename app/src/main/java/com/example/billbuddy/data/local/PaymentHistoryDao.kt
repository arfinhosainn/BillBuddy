package com.example.billbuddy.data.local

import androidx.room.*
import com.example.billbuddy.data.local.model.PaymentHistory
import com.example.billbuddy.data.local.model.relation.PaymentAndPaymentHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaymentHistory(paymentHistory: PaymentHistory)


    @Transaction
    @Query("SELECT * FROM payment_history_table")
    fun getPaymentHistory():Flow<List<PaymentAndPaymentHistory>>

}