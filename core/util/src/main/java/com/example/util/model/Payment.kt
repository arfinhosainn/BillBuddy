package com.example.util.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "payment_table")
data class Payment(
    val paymentTitle: String,
    val payeeName: String,
    val paymentAmount: String,
    val paymentIcon: Int,
    val paymentDate: LocalDate,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)

class InvalidPaymentException(message: String) : Exception(message)