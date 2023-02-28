package com.example.billbuddy.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.billbuddy.domain.model.Transaction
import java.util.*


@Entity(tableName = "transaction_table")
data class TransactionDto(
    @PrimaryKey
    val date: Date,
    val dateOfEntry: String,
    val amount: Double,
    val category: String,
    val transactionTypes: String,
    val title: String
) {
    fun toTransaction() = Transaction(
        date = date,
        dateOfEntry = dateOfEntry,
        amount = amount,
        category = category,
        transactionType = transactionTypes,
        title = title
    )
}
