package com.fibank.task.data.repository

import com.fibank.task.data.api.ExchangeRateApi
import com.fibank.task.data.model.ConversionResult
import com.fibank.task.data.model.Currency
import com.fibank.task.utils.CurrencyUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExchangeRateRepository(private val api: ExchangeRateApi) {

    private val _exchangeRates                          = MutableStateFlow<Map<String, Double>?>(null)
    val exchangeRates: StateFlow<Map<String, Double>?>  = _exchangeRates.asStateFlow()

    private val _availableCurrencies                    = MutableStateFlow<List<Currency>>(emptyList())
    val availableCurrencies: StateFlow<List<Currency>>  = _availableCurrencies.asStateFlow()

    private val _isLoading                              = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>                   = _isLoading.asStateFlow()

    private val _error                                  = MutableStateFlow<String?>(null)
    val error: StateFlow<String?>                       = _error.asStateFlow()
    
    suspend fun fetchExchangeRates() {
        _isLoading.value    = true
        _error.value        = null

        api.getExchangeRates()
            .onSuccess { response ->
                _exchangeRates.value        = response.conversionRates
                _availableCurrencies.value  = CurrencyUtils.generateCurrenciesFromRates(response.conversionRates)
            }
            .onFailure { exception ->
                _error.value = exception.message ?: "Failed to fetch exchange rates"
            }
            .also {
                _isLoading.value = false
            }
    }
    
    fun convertCurrency(
        fromCurrency    : String,
        toCurrency      : String,
        amount          : Double
    ): ConversionResult? {
        val rates = _exchangeRates.value ?: return null
        
        return when {
            // Converting from USD - use direct rate
            fromCurrency == "USD" -> {
                val rate = rates[toCurrency] ?: return null
                ConversionResult(
                    fromCurrency    = fromCurrency,
                    toCurrency      = toCurrency,
                    amount          = amount,
                    convertedAmount = amount * rate,
                    rate            = rate
                )
            }
            
            // Converting to USD - use inverse rate
            toCurrency == "USD" -> {
                val rate = rates[fromCurrency] ?: return null
                ConversionResult(
                    fromCurrency    = fromCurrency,
                    toCurrency      = toCurrency,
                    amount          = amount,
                    convertedAmount = amount / rate,
                    rate            = 1.0 / rate
                )
            }
            
            // Converting between non-USD currencies - convert through USD
            else -> {
                val fromRate        = rates[fromCurrency] ?: return null
                val toRate          = rates[toCurrency] ?: return null
                val usdAmount       = amount / fromRate
                val convertedAmount = usdAmount * toRate
                val rate            = toRate / fromRate
                
                ConversionResult(
                    fromCurrency    = fromCurrency,
                    toCurrency      = toCurrency,
                    amount          = amount,
                    convertedAmount = convertedAmount,
                    rate            = rate
                )
            }
        }
    }
}
