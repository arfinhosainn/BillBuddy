package com.example.feature_settings

data class SettingState(
    var expenseLimit: Double = 0.0,
    var currency: String = "",
    var reminderLimit: Boolean = false,
    var expenseLimitDuration: Int = 0
)
