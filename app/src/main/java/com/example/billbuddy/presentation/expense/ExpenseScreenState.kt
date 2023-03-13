package com.example.billbuddy.presentation.expense

import com.example.billbuddy.R
import java.time.LocalDateTime

data class ExpenseScreenState(
    val expenseAmount: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true,
    val expenseCategory: String = "",
    val expenseCategoryTitle: String = "",
    val expenseCategoryColor: String = "",
    var expenseDate: LocalDateTime = LocalDateTime.now()
)