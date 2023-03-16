package com.example.billbuddy.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billbuddy.data.local.model.PaymentHistory
import com.example.billbuddy.domain.repository.PaymentHistoryRepository
import com.example.billbuddy.domain.repository.PaymentRepository
import com.example.billbuddy.presentation.home.components.PaymentListState
import com.example.billbuddy.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
   private val paymentHistoryRepository: PaymentHistoryRepository,
) : ViewModel() {

    private val _paymentHistory = MutableStateFlow<List<PaymentHistory>>(emptyList())
    val paymentHistory: StateFlow<List<PaymentHistory>> = _paymentHistory


    fun insertPaymentHistory(paymentHistory: PaymentHistory) = viewModelScope.launch {
        paymentHistoryRepository.insertPaymentHistory(paymentHistory)
    }


    private val _paymentList = MutableStateFlow(PaymentListState())
    val paymentList = _paymentList.asStateFlow()


    init {
        getPayments()
        viewModelScope.launch {
            paymentHistoryRepository.getPaymentHistory().collect {
                _paymentHistory.value = it.map { paymentAndPaymentHistory ->
                    paymentAndPaymentHistory.paymentHistory
                }
            }
        }

    }

    private fun getPayments() {
        paymentRepository.getAllPayments().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _paymentList.value = PaymentListState(payments = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _paymentList.value = PaymentListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _paymentList.value = PaymentListState(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}