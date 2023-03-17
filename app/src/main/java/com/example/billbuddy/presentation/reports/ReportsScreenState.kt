package com.example.billbuddy.presentation.reports

import com.example.billbuddy.data.local.model.Expense

data class ReportsScreenState(
    var expense: List<Expense> = emptyList()
)
