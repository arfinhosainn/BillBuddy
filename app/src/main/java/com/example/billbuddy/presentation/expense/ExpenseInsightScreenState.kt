package com.example.billbuddy.presentation.expense

import com.example.billbuddy.data.local.model.Expense

data class ExpenseInsightScreenState(
    val isLoading: Boolean = false,
    val payments: List<Expense> = emptyList(),
    val error: String = ""
)