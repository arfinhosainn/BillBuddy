package com.example.billbuddy.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "expense_table")
data class Expense(
    val expenseAmount: String,
    val expenseCategory: Int,
    @ColumnInfo("expenseDate", defaultValue = "0")
    val expenseDate: LocalDateTime = LocalDateTime.now(),
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)
class InvalidExpenseException(message: String) : Exception(message)

