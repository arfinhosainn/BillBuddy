package com.example.billbuddy.presentation.reports

import java.time.LocalDateTime

sealed class ReportsEvent {
    data class StartMonthYear(val value: LocalDateTime) : ReportsEvent()
    data class EndMonthYear(val value: LocalDateTime) : ReportsEvent()
    data class ChangeStartAndEndMonthYear(val start: LocalDateTime, val end: LocalDateTime) :
        ReportsEvent()
}
