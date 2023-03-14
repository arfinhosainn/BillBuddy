package com.example.billbuddy.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "payment_history_table")
data class PaymentHistory(
    @PrimaryKey(autoGenerate = false)
    val id: Int? = null,
    val paymentTitle: String,
    val payeeName: String,
    val paymentAmount: String,
    val paymentIcon: Int,
    val paymentDate: LocalDate,
    val paymentHistoryDate: LocalDate = LocalDate.now(),
)

