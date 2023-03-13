package com.example.billbuddy.presentation.expense

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billbuddy.domain.repository.ExpenseRepository
import com.example.billbuddy.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseInsightViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {


    private var _filteredExpense = MutableStateFlow(ExpenseInsightScreenState().payments)
    val filteredExpense = _filteredExpense.asStateFlow()


    private var _fakeList = MutableStateFlow(ExpenseInsightScreenState())
    val fakeList = _fakeList.asStateFlow()

    init {
        getFilteredExpense()
        getAllExpenses()
    }


    fun getFilteredExpense(duration: Int = 4) {
        viewModelScope.launch(Dispatchers.IO) {
            filterExpense(duration)
        }
    }


    fun getAllExpenses() {
        viewModelScope.launch {
            expenseRepository.getAllExpenses().collect {
                when (it) {
                    is Resource.Error -> null
                    is Resource.Loading -> null
                    is Resource.Success -> _fakeList.value =
                        ExpenseInsightScreenState(payments = it.data ?: emptyList())
                }

            }

        }
    }


    private suspend fun filterExpense(duration: Int) {
        when (duration) {
            0 -> {
                expenseRepository.get3DayExpenses().collectLatest { filteredResults ->

                    _filteredExpense.value = filteredResults.data ?: emptyList()
                }
            }
            1 -> {
                expenseRepository.getWeeklyExpenses().collectLatest { filteredResults ->
                    _filteredExpense.value = filteredResults.data ?: emptyList()
                }
            }
            2 -> {
                expenseRepository.get14DayExpenses().collectLatest { filteredResults ->
                    _filteredExpense.value = filteredResults.data ?: emptyList()
                }
            }
            3 -> {
                expenseRepository.getMonthlyExpenses().collectLatest { filteredResults ->
                    _filteredExpense.value = filteredResults.data ?: emptyList()
                }
            }
            4 -> {
                expenseRepository.getAllExpenses().collectLatest { filteredResults ->
                    _filteredExpense.value = filteredResults.data ?: emptyList()
                    Log.d("ExpenseData", "Size of expenses: ${filteredResults.data}")

                }
            }
        }

    }


}