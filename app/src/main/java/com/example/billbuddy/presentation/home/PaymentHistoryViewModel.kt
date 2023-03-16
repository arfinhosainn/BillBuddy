package com.example.billbuddy.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billbuddy.domain.repository.PaymentHistoryRepository
import com.example.billbuddy.presentation.home.components.PaymentHistoryEvent
import com.example.billbuddy.presentation.home.components.PaymentHistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentHistoryViewModel @Inject constructor(
    private val paymentHistoryRepository: PaymentHistoryRepository,
) : ViewModel() {

    private val _paymentHistory = MutableStateFlow(PaymentHistoryState())
    val paymentHistory = _paymentHistory.asStateFlow()


        init {
            viewModelScope.launch {
                paymentHistoryRepository.getPaymentHistory().collect {
                    _paymentHistory.value.paymentHistory = it.map { paymentAndPaymentHistory ->
                        paymentAndPaymentHistory.paymentHistory
                    }
                }
            }
        }


    fun onEvent(event: PaymentHistoryEvent) {
        when (event) {
            is PaymentHistoryEvent.InsertPaymentHistory -> {
                viewModelScope.launch {
                    paymentHistoryRepository.insertPaymentHistory(event.paymentHistory)
                }
            }
        }
    }


}