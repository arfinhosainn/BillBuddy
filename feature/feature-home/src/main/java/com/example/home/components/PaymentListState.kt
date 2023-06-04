package com.example.home.components

import com.example.util.model.Payment

data class PaymentListState(
    val isLoading: Boolean = false,
    val payments: List<Payment> = emptyList(),
    val error: String = ""
)