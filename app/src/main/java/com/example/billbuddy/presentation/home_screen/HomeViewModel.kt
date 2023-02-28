package com.example.billbuddy.presentation.home_screen

import androidx.lifecycle.ViewModel
import com.example.billbuddy.domain.model.Transaction
import com.example.billbuddy.domain.repository.TransactionRepository
import com.example.billbuddy.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private var decimal: String = String()
    private var isDecimal = MutableStateFlow(false)
    private var duration = MutableStateFlow(0)

    var transactionAmount = MutableStateFlow("0.00")
        private set

    var dailyTransaction = MutableStateFlow(emptyList<Transaction>())
        private set


    var monthlyTransaction = MutableStateFlow(mapOf<String, List<Transaction>>())
        private set

    var currentExpenseAmount = MutableStateFlow(0.0)
        private set


    var transactionTitle = MutableStateFlow(String())
        private set

    var totalIncome = MutableStateFlow(0.0)
        private set

    var totalExpense = MutableStateFlow(0.0)
        private set

    var formattedDate = MutableStateFlow(String())
        private set

    var date = MutableStateFlow(String())
        private set

    var currentDate = MutableStateFlow(Calendar.getInstance().time)
        private set

    var selectedCurrencyCode = MutableStateFlow(String())
        private set

    var limitAlert = MutableSharedFlow<UIEvent>(replay = 1)
        private set

    var limitKey = MutableStateFlow(false)
        private set


    private fun calculateTransaction(transactions: List<Double>): Double {
        return transactions.sumOf {
            it
        }
    }

    fun setTransactionTitle(title: String) {
        transactionTitle.value = title
    }

    fun setCurrentDate(date: Date) {
        currentDate.value = date
    }

//    fun insertDailyTransaction


}