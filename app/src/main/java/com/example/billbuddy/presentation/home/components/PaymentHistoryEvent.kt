package com.example.billbuddy.presentation.home.components

import com.example.billbuddy.data.local.model.PaymentHistory

sealed class PaymentHistoryEvent {

    data class InsertPaymentHistory(val paymentHistory: PaymentHistory) : PaymentHistoryEvent()
}