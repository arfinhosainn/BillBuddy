package com.example.feature_expense.expense

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.repository.DataStoreOperation
import com.example.core_domain.repository.ExpenseRepository
import com.example.util.model.Expense
import com.example.util.model.InvalidExpenseException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class AddEditExpenseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dataStoreOperation: DataStoreOperation,
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    var paymentCurrency = MutableStateFlow("")
        private set


    private val _expenseAmount = MutableStateFlow(
        ExpenseScreenState(
            hint = "Amount"
        )
    )
    val expenseAmount = _expenseAmount.asStateFlow()

    private val _expenseDate = MutableStateFlow(
        ExpenseScreenState(
            expenseDate = LocalDateTime.now()
        )
    )

    val expenseDate = _expenseDate.asStateFlow()

    private val _expenseCategoryTitle = MutableStateFlow(
        ExpenseScreenState()
    )

    val expenseCategoryTitle = _expenseCategoryTitle.asStateFlow()

    private val _expenseCategoryColor = MutableStateFlow(
        ExpenseScreenState()
    )

    val expenseCategoryColor = _expenseCategoryColor.asStateFlow()

    private val _expenseCategory = MutableStateFlow(
        ExpenseScreenState(
            expenseCategory = ""
        )
    )

    val expenseCategory = _expenseCategory.asStateFlow()

    init {
        savedStateHandle.get<Int>("expenseId")?.let { expenseId ->
            if (expenseId != -1) {
                viewModelScope.launch {
                    expenseRepository.getExpenseById(expenseId)?.also { expense ->
                        currentPaymentId = expense.id
                        _expenseAmount.value = expenseAmount.value.copy(
                            expenseAmount = expense.expenseAmount,
                            isHintVisible = false
                        )

                        _expenseCategory.value = expenseCategory.value.copy(
                            expenseCategory = expenseCategory.value.expenseCategory
                        )
                    }
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            dataStoreOperation.readCurrencyFromDataStore().collect { currency ->
                paymentCurrency.value = currency
            }
        }
    }

    private val _eventFlow = MutableSharedFlow<ExpenseUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentPaymentId: Int? = null

    fun onEvent(event: AddEditExpenseEvent) {
        when (event) {
            is AddEditExpenseEvent.EnteredAmount -> {
                _expenseAmount.value = expenseAmount.value.copy(
                    expenseAmount = event.value
                )
            }

            is AddEditExpenseEvent.ChangeAmountFocus -> {
                _expenseAmount.value = expenseAmount.value.copy(
                    isHintVisible = !event.focusState.isFocused && expenseAmount.value.expenseAmount.isBlank()
                )
            }

            is AddEditExpenseEvent.ChooseExpenseCategory -> {
                _expenseCategory.value = expenseCategory.value.copy(
                    expenseCategory = event.value
                )
            }

            is AddEditExpenseEvent.ExpenseCategoryTitle -> {
                _expenseCategoryTitle.value = expenseCategoryTitle.value.copy(
                    expenseCategoryTitle = event.value
                )
            }

            is AddEditExpenseEvent.ExpenseCategoryColor -> {
                _expenseCategoryColor.value = expenseCategoryColor.value.copy(
                    expenseCategoryColor = event.value
                )
            }

            is AddEditExpenseEvent.SaveExpense -> {
                viewModelScope.launch {
                    try {
                        expenseRepository.insertExpense(
                            Expense(
                                expenseAmount = expenseAmount.value.expenseAmount!!,
                                expenseCategory = expenseCategory.value.expenseCategory,
                                expenseDate = expenseDate.value.expenseDate,
                                expenseTitle = expenseCategoryTitle.value.expenseCategoryTitle,
                                expenseCategoryColor = expenseCategoryColor.value.expenseCategoryColor
                            )
                        )
                        _eventFlow.emit(ExpenseUiEvent.SaveExpense)
                    } catch (e: InvalidExpenseException) {
                        _eventFlow.emit(
                            ExpenseUiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save payment"
                            )
                        )
                    }
                }
            }
        }
    }
}

sealed class ExpenseUiEvent {
    data class ShowSnackbar(val message: String) : ExpenseUiEvent()
    object SaveExpense : ExpenseUiEvent()
}