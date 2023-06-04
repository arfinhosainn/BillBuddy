package com.example.feature_expense.expense

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
data class ExpenseScreenState (
    val expenseAmount: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true,
    val expenseCategory: String = "",
    val expenseCategoryTitle: String = "",
    val expenseCategoryColor: String = "",
    var expenseDate: LocalDateTime = LocalDateTime.now()
)