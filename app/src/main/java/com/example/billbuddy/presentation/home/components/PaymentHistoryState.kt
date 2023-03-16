package com.example.billbuddy.presentation.home.components

import com.example.billbuddy.data.local.model.PaymentHistory

data class PaymentHistoryState(
    val isLoading: Boolean = false,
    var payments: List<PaymentHistory> = emptyList(),
    val error: String = ""

)