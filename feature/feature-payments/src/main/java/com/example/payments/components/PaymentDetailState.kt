package com.example.payments.components

import com.example.util.model.Payment

data class PaymentDetailState(
    val isLoading: Boolean = false,
    val payments: List<Payment> = emptyList(),
    val error: String = ""
)