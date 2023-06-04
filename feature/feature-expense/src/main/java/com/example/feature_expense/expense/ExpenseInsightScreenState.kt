package com.example.feature_expense.expense

import com.example.util.model.Expense

data class ExpenseInsightScreenState(
    val isLoading: Boolean = false,
    val payments: List<Expense> = emptyList(),
    val error: String = ""
)