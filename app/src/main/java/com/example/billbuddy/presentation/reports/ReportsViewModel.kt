package com.example.billbuddy.presentation.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billbuddy.domain.repository.DataStoreOperation
import com.example.billbuddy.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val dataStoreOperation: DataStoreOperation,
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _expenseAmount = MutableStateFlow(ReportsScreenState())
    val expenseAmount = _expenseAmount.asStateFlow()


    fun getSavingsByMonth(startMonth: LocalDateTime, endMonth: LocalDateTime) {
        viewModelScope.launch {
            val expenses = expenseRepository.getExpensesForMonthRange(startMonth, endMonth)
            _expenseAmount.value = ReportsScreenState(expense = expenses)
        }
    }


}