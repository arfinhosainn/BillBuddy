package com.example.billbuddy.presentation.add_edit_payment_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billbuddy.R
import com.example.billbuddy.data.local.model.InvalidPaymentException
import com.example.billbuddy.data.local.model.Payment
import com.example.billbuddy.domain.repository.PaymentRepository
import com.example.billbuddy.services.AndroidAlarmScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddEditPaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val alarmScheduler: AndroidAlarmScheduler,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _paymentTitle = MutableStateFlow(
        PaymentTextFieldState(
            hint = "Enter Title"
        )
    )
    val paymentTitle = _paymentTitle.asStateFlow()

    private val _payerName = MutableStateFlow(
        PaymentTextFieldState(
            hint = "Enter Payer Name"
        )
    )

    val payerName = _payerName.asStateFlow()

    private val _paymentAmount = MutableStateFlow(
        PaymentTextFieldState(
            hint = "Amount"
        )
    )
    val paymentAmount = _paymentAmount.asStateFlow()


    private val _paymentDate = MutableStateFlow(
        PaymentTextFieldState(
            paymentDate = LocalDate.now()
        )
    )

    val paymentDate = _paymentDate.asStateFlow()

    private val _paymentIcon = MutableStateFlow(
        PaymentTextFieldState(
            paymentIcon = R.drawable.mobile
        )
    )

    val paymentIcon = _paymentIcon.asStateFlow()


    init {
        savedStateHandle.get<Int>("paymentId")?.let { paymentId ->
            if (paymentId != -1) {
                viewModelScope.launch {
                    paymentRepository.getPaymentById(paymentId)?.also { payment ->
                        currentPaymentId = payment.id
                        _paymentTitle.value = paymentTitle.value.copy(
                            text = payment.paymentTitle,
                            isHintVisible = false
                        )
                        _paymentAmount.value = paymentAmount.value.copy(
                            amount = payment.paymentAmount,
                            isHintVisible = false
                        )
                        _paymentDate.value = paymentDate.value.copy(
                            paymentDate = payment.paymentDate
                        )
                        _payerName.value = payerName.value.copy(
                            text = payment.payeeName,
                            isHintVisible = false
                        )
                        _paymentIcon.value = paymentIcon.value.copy(
                            paymentIcon = paymentIcon.value.paymentIcon
                        )
                    }
                }
            }

        }
    }


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentPaymentId: Int? = null

    fun onEvent(event: AddEditPaymentEvent) {
        when (event) {
            is AddEditPaymentEvent.EnteredTitle -> {
                _paymentTitle.value = paymentTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditPaymentEvent.ChangeTitleFocus -> {
                _paymentTitle.value = paymentTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && paymentTitle.value.text.isBlank()
                )
            }
            is AddEditPaymentEvent.EnteredAmount -> {
                _paymentAmount.value = paymentAmount.value.copy(
                    amount = event.value
                )
            }
            is AddEditPaymentEvent.ChangeAmountFocus -> {
                _paymentAmount.value = paymentAmount.value.copy(
                    isHintVisible = !event.focusState.isFocused && paymentAmount.value.text.isBlank()
                )
            }
            is AddEditPaymentEvent.EnteredPayerName -> {
                _payerName.value = payerName.value.copy(
                    text = event.value
                )
            }
            is AddEditPaymentEvent.ChangePayerNameFocus -> {
                _payerName.value = payerName.value.copy(
                    isHintVisible = !event.focusState.isFocused && paymentTitle.value.text.isBlank()
                )
            }
            is AddEditPaymentEvent.ChangePaymentDate -> {
                _paymentDate.value = paymentDate.value.copy(
                    paymentDate = event.value
                )
            }
            is AddEditPaymentEvent.ChoosePaymentIcon -> {
                _paymentIcon.value = paymentIcon.value.copy(
                    paymentIcon = event.value
                )
            }
            is AddEditPaymentEvent.SavePayment -> {
                viewModelScope.launch {
                    try {
                        paymentRepository.insertPayments(
                            Payment(
                                paymentTitle = paymentTitle.value.text,
                                payeeName = payerName.value.text,
                                paymentAmount = paymentAmount.value.amount,
                                paymentDate = paymentDate.value.paymentDate,
                                id = currentPaymentId,
                                paymentIcon = paymentIcon.value.paymentIcon
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)

                        alarmScheduler.schedule(
                            Payment(
                                paymentTitle = paymentTitle.value.text,
                                payeeName = payerName.value.text,
                                paymentAmount = paymentAmount.value.amount,
                                paymentDate = paymentDate.value.paymentDate,
                                id = currentPaymentId,
                                paymentIcon = paymentIcon.value.paymentIcon
                            )
                        )

                    } catch (e: InvalidPaymentException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save payment"
                            )
                        )

                    }
                }
            }
        }

    }
}

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    object SaveNote : UiEvent()
}

