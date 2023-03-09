package com.example.billbuddy.presentation.add_edit_payment

import com.example.billbuddy.R
import java.time.LocalDate

data class PaymentTextFieldState(
    val text: String = "",
    val amount: Double = 0.00,
    val hint: String = "",
    val isHintVisible: Boolean = true,
    val paymentIcon: Int = R.drawable.mobile,
    var paymentDate: LocalDate = LocalDate.now()
)
