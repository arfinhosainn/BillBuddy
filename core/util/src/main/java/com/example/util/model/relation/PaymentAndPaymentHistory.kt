package com.example.billbuddy.data.local.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.util.model.Payment
import com.example.util.model.PaymentHistory

data class PaymentAndPaymentHistory(
    @Embedded val payment: Payment,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val paymentHistory: PaymentHistory
)
