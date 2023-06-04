package com.example.payments

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
data class PaymentTextFieldState (
    val text: String = "",
    val amount: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true,
    val paymentIcon: Int = R.drawable.mobile,
    var paymentDate: LocalDate = LocalDate.now()
)
