package com.example.billbuddy.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment_table")
data class PaymentDto(
    val paymentTitle: String,
    val payerName: String,
    val paymentAmount: Double,
    val paymentDate: String,
    val paymentMethod: String,
    @PrimaryKey(autoGenerate = true) val paymentId: Int? = null



)
