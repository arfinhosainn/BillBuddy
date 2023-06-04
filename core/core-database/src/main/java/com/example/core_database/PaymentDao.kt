package com.example.core_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.util.model.Payment
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayments(payments: Payment)

    @Query("SELECT * FROM payment_table")
    fun getAllPayments(): Flow<List<Payment>>

    @Query("SELECT * FROM payment_table WHERE id = :id")
    suspend fun getPaymentById(id: Int): Payment?

    @Delete
    suspend fun deletePayment(payments: Payment)
}