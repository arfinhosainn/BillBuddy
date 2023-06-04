package com.example.core_database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.core_domain.repository.DataStoreOperation
import com.example.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "bill_buddy_key_store")

class DataStoreOperationImpl @Inject constructor(
    context: Context
) : DataStoreOperation {

    private val budgetLimit = doublePreferencesKey(Constants.EXPENSE_LIMIT_KEY)
    private val datastore = context.datastore
    private val onBoardingKey = booleanPreferencesKey(Constants.WELCOME_KEY)
    private val limitKey = booleanPreferencesKey(Constants.LIMIT_KEY)
    private val selectedCurrency = stringPreferencesKey(Constants.CURRENCY_KEY)
    private val limitDuration = intPreferencesKey(Constants.LIMIT_DURATION)

    override suspend fun writeOnBoardingKeyToDataStore(completed: Boolean) {
        datastore.edit { store ->
            store[onBoardingKey] = completed
        }
    }

    override suspend fun readOnBoardingKeyFormDataStore(): Flow<Boolean> {
        val preferences = datastore.data
        return flow {
            preferences.collect { pref ->
                emit(pref[onBoardingKey] ?: false)
            }
        }
    }

    override suspend fun writeCurrencyToDataStore(currency: String) {
        datastore.edit { store ->
            store[selectedCurrency] = currency
        }
    }

    override suspend fun readCurrencyFromDataStore(): Flow<String> {
        val preferences = datastore.data
        return flow {
            preferences.collect { pref ->
                emit(pref[selectedCurrency] ?: String())
            }
        }
    }

    override suspend fun writeBudgetLimitToDataStore(amount: Double) {
        datastore.edit { store ->
            store[budgetLimit] = amount
        }
    }

    override suspend fun readExpenseLimitFromDataStore(): Flow<Double> {
        val preferences = datastore.data
        return flow {
            preferences.collect { pref ->
                emit(pref[budgetLimit] ?: 0.0)
            }
        }
    }

    override suspend fun readLimitKeyFromDataStore(): Flow<Boolean> {
        val preferences = datastore.data
        return flow {
            preferences.collect { pref ->
                emit(pref[limitKey] ?: false)
            }
        }
    }

    override suspend fun writeLimitKeyToDataStore(enables: Boolean) {
        datastore.edit { store ->
            store[limitKey] = enables
        }
    }

    override suspend fun writeLimitDurationToDataStore(duration: Int) {
        datastore.edit { store ->
            store[limitDuration] = duration
        }
    }

    override suspend fun readLimitDurationFromDataStore(): Flow<Int> {
        val preferences = datastore.data
        return flow {
            preferences.collect { pref ->
                emit(pref[limitDuration] ?: 0)
            }
        }
    }

    override suspend fun eraseDataStore() {
        datastore.edit {
            it.remove(limitKey)
            it.remove(limitDuration)
            it.remove(budgetLimit)
        }
    }
}