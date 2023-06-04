package com.example.home.components

import com.example.util.model.PaymentHistory

sealed class PaymentHistoryEvent {

    data class InsertPaymentHistory(val paymentHistory: PaymentHistory) : PaymentHistoryEvent()
}