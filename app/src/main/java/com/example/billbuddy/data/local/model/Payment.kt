package com.example.billbuddy.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "payment_table")
data class Payment(
    val paymentTitle: String,
    val payerName: String,
    val paymentAmount: Double?,
    val paymentDate: LocalDateTime,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)

class InvalidPaymentException(message: String) : Exception(message)