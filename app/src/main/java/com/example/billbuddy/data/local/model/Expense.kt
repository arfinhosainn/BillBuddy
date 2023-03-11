package com.example.billbuddy.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "expense_table")
data class Expense(
    @ColumnInfo(defaultValue = "")
    val expenseTitle: String,
    @ColumnInfo(defaultValue = "")
    val expenseCategoryColor: String,
    @ColumnInfo("expenseAmount", defaultValue = "0")
    val expenseAmount: Double,
    val expenseCategory: String,
    @ColumnInfo("expenseDate", defaultValue = "0")
    val expenseDate: LocalDateTime = LocalDateTime.now(),
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)

class InvalidExpenseException(message: String) : Exception(message)

