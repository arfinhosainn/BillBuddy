package com.example.util.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Entity(tableName = "expense_table")
data class Expense (
    @ColumnInfo(defaultValue = "")
    val expenseTitle: String,
    @ColumnInfo(defaultValue = "")
    val expenseCategoryColor: String,
    @ColumnInfo("expenseAmount", defaultValue = "0")
    val expenseAmount: String,
    val expenseCategory: String,
    @ColumnInfo("expenseDate", defaultValue = "0")
    val expenseDate: LocalDateTime = LocalDateTime.now(),
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)

class InvalidExpenseException(message: String) : Exception(message)
