package com.example.billbuddy.presentation.reports

import com.example.billbuddy.data.local.model.Expense
import java.time.LocalDateTime

data class ReportsScreenState(
    var expense: List<Expense> = emptyList(),
    var startMonthAndYear: LocalDateTime = LocalDateTime.now(),
    val endMonthAndYear: LocalDateTime = LocalDateTime.now()
)
