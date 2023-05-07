package com.example.payments

import androidx.compose.ui.focus.FocusState
import com.example.util.model.Payment
import java.time.LocalDate

sealed class AddEditPaymentEvent {
    data class EnteredTitle(val value: String) : AddEditPaymentEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditPaymentEvent()
    data class EnteredAmount(val value: String) : AddEditPaymentEvent()
    data class ChangeAmountFocus(val focusState: FocusState) : AddEditPaymentEvent()
    data class DeletePayment(val payment: Payment) : AddEditPaymentEvent()
    data class EnteredPayerName(val value: String) : AddEditPaymentEvent()
    data class ChangePayerNameFocus(val focusState: FocusState) : AddEditPaymentEvent()
    data class ChangePaymentDate(val value: LocalDate) : AddEditPaymentEvent()
    data class ChoosePaymentIcon(val value: Int) : AddEditPaymentEvent()
    object SavePayment : AddEditPaymentEvent()
}
