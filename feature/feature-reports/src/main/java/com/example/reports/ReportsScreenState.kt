package com.example.reports

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.util.model.Expense
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
data class ReportsScreenState(
    var expense: List<Expense> = emptyList(),
    var startMonthAndYear: LocalDateTime = LocalDateTime.now(),
    val endMonthAndYear: LocalDateTime = LocalDateTime.now()
)
