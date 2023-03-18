package com.example.billbuddy.presentation.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billbuddy.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _expenseAmount = MutableStateFlow(ReportsScreenState())
    val expenseAmount = _expenseAmount.asStateFlow()


    private val _startMonthAndYearState = MutableStateFlow(ReportsScreenState())
    val startMonthAndYearState = _startMonthAndYearState.asStateFlow()

    private val _endMonthAndYearState = MutableStateFlow(ReportsScreenState())
    val endMonthAndYearState = _endMonthAndYearState.asStateFlow()


    private val _startAndEndMonthYearState = MutableStateFlow(
        ReportsScreenState()
    )
    val startAndEndMonthYearState = _startAndEndMonthYearState.asStateFlow()


    fun onEvent(event: ReportsEvent) {
        when (event) {
            is ReportsEvent.ChangeStartAndEndMonthYear -> {
                viewModelScope.launch {
                    val expenses = expenseRepository.getExpensesForMonthRange(
                        startMonth = event.start,
                        endMonth = event.end
                    )
                    _startAndEndMonthYearState.value =
                        _startAndEndMonthYearState.value.copy(expense = expenses)

                }

            }
            is ReportsEvent.EndMonthYear -> {
                _endMonthAndYearState.value = endMonthAndYearState.value.copy(
                    endMonthAndYear = event.value
                )
            }
            is ReportsEvent.StartMonthYear -> {
                _startMonthAndYearState.value = startMonthAndYearState.value.copy(
                    startMonthAndYear = event.value
                )
            }
        }
    }
}