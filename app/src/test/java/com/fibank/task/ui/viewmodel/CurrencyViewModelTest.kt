package com.fibank.task.ui.viewmodel

import com.fibank.task.data.model.Currency
import com.fibank.task.data.model.ConversionResult
import com.fibank.task.data.repository.ExchangeRateRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyViewModelTest {

    @Mock
    private lateinit var mockRepository : ExchangeRateRepository

    private lateinit var viewModel      : CurrencyViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        // Mock the repository methods to return empty flows
        whenever(mockRepository.isLoading).thenReturn(MutableStateFlow(false))
        whenever(mockRepository.error).thenReturn(MutableStateFlow(null))
        whenever(mockRepository.exchangeRates).thenReturn(MutableStateFlow(null))
        whenever(mockRepository.availableCurrencies).thenReturn(MutableStateFlow(emptyList()))
        viewModel = CurrencyViewModel(mockRepository)
    }

    @Test
    fun `setFromCurrency should update UI state`() {
        // Given
        val currency = Currency("USD", "US Dollar")
        
        // When
        viewModel.setFromCurrency(currency)
        
        // Then
        assertEquals(currency, viewModel.uiState.value.fromCurrency)
    }

    @Test
    fun `setToCurrency should update UI state`() {
        // Given
        val currency = Currency("EUR", "Euro")
        
        // When
        viewModel.setToCurrency(currency)
        
        // Then
        assertEquals(currency, viewModel.uiState.value.toCurrency)
    }

    @Test
    fun `setAmount should update UI state`() {
        // Given
        val amount = "100.50"
        
        // When
        viewModel.setAmount(amount)
        
        // Then
        assertEquals(amount, viewModel.uiState.value.amount)
    }

    @Test
    fun `reverseCurrencies should swap from and to currencies`() {
        // Given
        val usd = Currency("USD", "US Dollar")
        val eur = Currency("EUR", "Euro")
        viewModel.setFromCurrency(usd)
        viewModel.setToCurrency(eur)
        
        // When
        viewModel.reverseCurrencies()
        
        // Then
        assertEquals(eur, viewModel.uiState.value.fromCurrency)
        assertEquals(usd, viewModel.uiState.value.toCurrency)
        assertTrue(viewModel.uiState.value.isReversed)
    }

    @Test
    fun `reverseCurrencies twice should return to original state`() {
        // Given
        val usd = Currency("USD", "US Dollar")
        val eur = Currency("EUR", "Euro")
        viewModel.setFromCurrency(usd)
        viewModel.setToCurrency(eur)
        
        // When
        viewModel.reverseCurrencies()
        viewModel.reverseCurrencies()
        
        // Then
        assertEquals(usd, viewModel.uiState.value.fromCurrency)
        assertEquals(eur, viewModel.uiState.value.toCurrency)
        assertFalse(viewModel.uiState.value.isReversed)
    }

    @Test
    fun `performConversion should call repository with correct parameters`() = runTest {
        // Given
        val usd             = Currency("USD", "US Dollar")
        val eur             = Currency("EUR", "Euro")
        val amount          = "100.0"
        val expectedResult  = ConversionResult("USD", "EUR", 100.0, 85.0, 0.85)
        
        whenever(mockRepository.convertCurrency("USD", "EUR", 100.0))
            .thenReturn(expectedResult)
        
        // When
        viewModel.setFromCurrency(usd)
        viewModel.setToCurrency(eur)
        viewModel.setAmount(amount)
        
        // Then
        assertEquals(expectedResult, viewModel.uiState.value.conversionResult)
    }

    @Test
    fun `performConversion with invalid amount should not call repository`() = runTest {
        // Given
        val usd = Currency("USD", "US Dollar")
        val eur = Currency("EUR", "Euro")
        
        // When
        viewModel.setFromCurrency(usd)
        viewModel.setToCurrency(eur)
        viewModel.setAmount("invalid")
        
        // Then
        assertNull(viewModel.uiState.value.conversionResult)
    }

    @Test
    fun `performConversion with zero amount should not call repository`() = runTest {
        // Given
        val usd = Currency("USD", "US Dollar")
        val eur = Currency("EUR", "Euro")
        
        // When
        viewModel.setFromCurrency(usd)
        viewModel.setToCurrency(eur)
        viewModel.setAmount("0")
        
        // Then
        assertNull(viewModel.uiState.value.conversionResult)
    }

    @Test
    fun `performConversion with negative amount should not call repository`() = runTest {
        // Given
        val usd = Currency("USD", "US Dollar")
        val eur = Currency("EUR", "Euro")
        
        // When
        viewModel.setFromCurrency(usd)
        viewModel.setToCurrency(eur)
        viewModel.setAmount("-100")
        
        // Then
        assertNull(viewModel.uiState.value.conversionResult)
    }

    @Test
    fun `clearError should clear error state`() {
        // Given
        viewModel.clearError()
        
        // Then
        assertNull(viewModel.uiState.value.error)
    }
}
