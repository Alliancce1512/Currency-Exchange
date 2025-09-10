package com.fibank.task.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fibank.task.data.model.ConversionResult
import com.fibank.task.data.model.Currency
import com.fibank.task.data.repository.ExchangeRateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CurrencyUiState(
    val isLoading               : Boolean           = false,
    val error                   : String?           = null,
    val availableCurrencies     : List<Currency>    = emptyList(),
    val fromCurrency            : Currency?         = null,
    val toCurrency              : Currency?         = null,
    val amount                  : String            = "",
    val conversionResult        : ConversionResult? = null,
    val isReversed              : Boolean           = false
)

class CurrencyViewModel(private val repository: ExchangeRateRepository) : ViewModel() {
    
    private val _uiState                    = MutableStateFlow(CurrencyUiState())
    val uiState: StateFlow<CurrencyUiState> = _uiState.asStateFlow()
    
    init {
        collectRepositoryFlows()
        fetchExchangeRates()
    }
    
    private fun collectRepositoryFlows() {
        // Collect loading state
        viewModelScope.launch {
            repository.isLoading.collect { isLoading ->
                _uiState.value = _uiState.value.copy(isLoading = isLoading)
            }
        }

        // Collect error state
        viewModelScope.launch {
            repository.error.collect { error ->
                _uiState.value = _uiState.value.copy(error = error)
            }
        }

        // Collect available currencies
        viewModelScope.launch {
            repository.availableCurrencies.collect { currencies ->
                _uiState.value = _uiState.value.copy(availableCurrencies = currencies)
            }
        }

        // Collect exchange rates and trigger conversion when available
        viewModelScope.launch {
            repository.exchangeRates.collect { rates ->
                rates?.let {
                    performConversion()
                }
            }
        }
    }
    
    private fun fetchExchangeRates() {
        viewModelScope.launch {
            repository.fetchExchangeRates()
        }
    }
    
    fun setFromCurrency(currency: Currency) {
        _uiState.value = _uiState.value.copy(fromCurrency = currency)
        performConversion()
    }
    
    fun setToCurrency(currency: Currency) {
        _uiState.value = _uiState.value.copy(toCurrency = currency)
        performConversion()
    }
    
    fun setAmount(amount: String) {
        _uiState.value = _uiState.value.copy(amount = amount)
        performConversion()
    }
    
    fun reverseCurrencies() {
        val currentState = _uiState.value

        _uiState.value   = currentState.copy(
            fromCurrency    = currentState.toCurrency,
            toCurrency      = currentState.fromCurrency,
            isReversed      = !currentState.isReversed
        )
        performConversion()
    }
    
    private fun performConversion() {
        val state   = _uiState.value
        val amount  = state.amount.toDoubleOrNull()
        
        if (amount != null && amount > 0 && 
            state.fromCurrency != null && 
            state.toCurrency != null
        ) {
            val result = repository.convertCurrency(
                fromCurrency    = state.fromCurrency.code,
                toCurrency      = state.toCurrency.code,
                amount          = amount
            )
            
            _uiState.value = state.copy(conversionResult = result)
        } else {
            _uiState.value = state.copy(conversionResult = null)
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun refreshExchangeRates() {
        fetchExchangeRates()
    }
}