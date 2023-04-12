package com.example.billbuddy.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billbuddy.domain.repository.PaymentRepository
import com.example.billbuddy.presentation.home.components.PaymentListState
import com.example.billbuddy.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository
) : ViewModel() {

    private val _paymentList = MutableStateFlow(PaymentListState())
    val paymentList = _paymentList.asStateFlow()

    init {
        getPayments()
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