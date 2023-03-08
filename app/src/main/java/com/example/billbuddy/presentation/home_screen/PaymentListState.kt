package com.example.billbuddy.presentation.home_screen

import com.example.billbuddy.data.local.model.Payment

data class PaymentListState(
    val isLoading: Boolean = false,
    val payments: List<Payment> = emptyList(),
    val error: String = ""
)