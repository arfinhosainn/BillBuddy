package com.example.billbuddy.presentation.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billbuddy.domain.repository.DataStoreOperation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreOperation: DataStoreOperation
): ViewModel(){

    var expenseLimit = MutableStateFlow(0.0)
    private set

    var currency = MutableStateFlow("")
        private set

    var reminderLimit = MutableStateFlow(false)
        private set

    var expenseLimitDuration = MutableStateFlow(0)
        private set

    init {
        viewModelScope.launch (Dispatchers.IO){
            dataStoreOperation.readCurrencyFromDataStore().collect{selectedCurrency ->
                currency.value = selectedCurrency
            }
        }

        viewModelScope.launch (Dispatchers.IO){
            dataStoreOperation.readExpenseLimitFromDataStore().collect {expenseAmount ->
                expenseLimit.value = expenseAmount
            }
        }
        viewModelScope.launch (Dispatchers.IO){
            dataStoreOperation.readLimitKeyFromDataStore().collect {limitKey ->
                reminderLimit.value = limitKey
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreOperation.readLimitDurationFromDataStore().collect {duration ->
                expenseLimitDuration.value = duration
            }
        }

    }


    fun editExpenseLimit(amount: Double ){
        viewModelScope.launch (Dispatchers.IO){
            dataStoreOperation.writeBudgetLimitToDataStore(amount)
            Log.d("budgetAmount", "editExpenseLimit: $amount")
        }
    }

    fun editLimitKey(enabled: Boolean){
        viewModelScope.launch (Dispatchers.IO){
            dataStoreOperation.writeLimitKeyToDataStore(enabled)
        }
    }

    fun editLimitDuration(duration:Int){
        viewModelScope.launch (Dispatchers.IO){
            dataStoreOperation.writeLimitDurationToDataStore(duration)
        }
    }

}