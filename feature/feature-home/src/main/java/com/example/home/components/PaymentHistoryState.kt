package com.example.home.components

import com.example.util.model.PaymentHistory

data class PaymentHistoryState(
    val isLoading: Boolean = false,
    var paymentHistory: List<PaymentHistory> = emptyList(),
    val error: String = ""

)