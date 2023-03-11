package com.example.billbuddy.presentation.expense

import androidx.compose.ui.focus.FocusState

sealed class AddEditExpenseEvent {
    data class EnteredAmount(val value: Double) : AddEditExpenseEvent()
    data class ChangeAmountFocus(val focusState: FocusState) : AddEditExpenseEvent()
    data class ChooseExpenseCategory(val value: String) : AddEditExpenseEvent()
    data class ExpenseCategoryTitle(val value: String) : AddEditExpenseEvent()
    data class ExpenseCategoryColor(val value: String) : AddEditExpenseEvent()
    object SaveExpense : AddEditExpenseEvent()
}
