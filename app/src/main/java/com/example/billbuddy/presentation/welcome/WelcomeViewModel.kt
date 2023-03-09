package com.example.billbuddy.presentation.welcome

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billbuddy.domain.repository.DataStoreOperation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val dataStoreOperation: DataStoreOperation
) : ViewModel() {

    var countryCurrencies = mutableStateOf(emptyMap<Char, List<CurrencyModel>>())
        private set


    init {
        countryCurrencies.value = getCurrency().groupBy { it.country[0] }
    }

    val listOfPages: State<List<OnBoardingScreens>> = mutableStateOf(
        listOf(
            OnBoardingScreens.FirstPage,
            OnBoardingScreens.SecondPage,
            OnBoardingScreens.ThirdPage
        )
    )

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch(IO) {
            dataStoreOperation.writeOnBoardingKeyToDataStore(completed = completed)
        }
    }

    fun saveCurrency(currency: String) {
        viewModelScope.launch {
            dataStoreOperation.writeCurrencyToDataStore(currency = currency)
        }
    }

    fun createAccounts() {

    }


    private fun getCurrency(): List<CurrencyModel> {
        val currencies = mutableListOf<CurrencyModel>()
        val countries = mutableListOf<String>()
        val allLocale = Locale.getAvailableLocales()

        allLocale.forEach { locale ->
            val countryName = locale.displayCountry
            try {
                val currencyCode = Currency.getInstance(locale).currencyCode
                val currency = Currency.getInstance(currencyCode)
                val currencySymbol = currency.getSymbol(locale)

                val currencyModel = CurrencyModel(
                    country = countryName,
                    currencyCode = currencyCode,
                    currencySymbol = currencySymbol
                )
                if (countryName.trim().isNotEmpty() && !countries.contains(countryName)) {
                    countries.add(countryName)
                    currencies.add(currencyModel)
                }
            } catch (e: Exception) {
            }
        }
        return currencies.sortedBy { it.country }
    }


}