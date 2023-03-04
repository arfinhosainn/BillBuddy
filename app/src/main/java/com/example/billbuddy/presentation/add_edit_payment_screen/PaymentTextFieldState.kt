package com.example.billbuddy.presentation.add_edit_payment_screen

import com.example.billbuddy.util.DateTimeProviderImpl
import java.time.LocalDateTime

data class PaymentTextFieldState(
    val text: String = "",
    val amount: Double = 0.00,
    val hint: String = "",
    val isHintVisible: Boolean = true,
    val paymentDate: LocalDateTime = DateTimeProviderImpl().now()
)
