package com.example.billbuddy.presentation.yourpayments.writepayments

import com.example.billbuddy.R
import java.time.LocalDate

data class PaymentTextFieldState(
    val text: String = "",
    val amount: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true,
    val paymentIcon: Int = R.drawable.mobile,
    var paymentDate: LocalDate = LocalDate.now()
)
