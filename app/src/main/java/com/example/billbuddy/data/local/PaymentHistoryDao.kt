package com.example.billbuddy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.billbuddy.data.local.model.PaymentHistory
import com.example.billbuddy.data.local.model.relation.PaymentAndPaymentHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaymentHistory(paymentHistory: PaymentHistory)

    @Query("SELECT * FROM payment_history_table")
    fun getPaymentHistory():Flow<List<PaymentAndPaymentHistory>>

}