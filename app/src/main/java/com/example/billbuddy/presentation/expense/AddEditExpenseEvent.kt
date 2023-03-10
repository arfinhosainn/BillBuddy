package com.example.billbuddy.presentation.expense

import androidx.compose.ui.focus.FocusState

sealed class AddEditExpenseEvent {
    data class EnteredAmount(val value: String) : AddEditExpenseEvent()
    data class ChangeAmountFocus(val focusState: FocusState) : AddEditExpenseEvent()
    data class ChooseExpenseCategory(val value: Int) : AddEditExpenseEvent()
    object SaveExpense : AddEditExpenseEvent()
}
