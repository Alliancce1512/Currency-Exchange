package com.fibank.task.data.repository

import com.fibank.task.data.api.ExchangeRateApi
import com.fibank.task.data.model.ExchangeRateResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class ExchangeRateRepositoryTest {

    @Mock
    private lateinit var mockApi    : ExchangeRateApi

    private lateinit var repository : ExchangeRateRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = ExchangeRateRepository(mockApi)
    }

    @Test
    fun `convertCurrency from USD to EUR should return correct result`() = runTest {
        // Given
        val mockResponse = ExchangeRateResponse(
            result                  = "success",
            documentation           = "",
            termsOfUse              = "",
            timeLastUpdateUnix      = 0L,
            timeLastUpdateUtc       = "",
            timeNextUpdateUnix      = 0L,
            timeNextUpdateUtc       = "",
            baseCode                = "USD",
            conversionRates         = mapOf(
                "USD" to 1.0,
                "EUR" to 0.85,
                "GBP" to 0.73
            )
        )
        whenever(mockApi.getExchangeRates()).thenReturn(Result.success(mockResponse))
        
        // When
        repository.fetchExchangeRates()
        val result = repository.convertCurrency("USD", "EUR", 100.0)
        
        // Then
        assertNotNull(result)
        assertEquals("USD", result!!.fromCurrency)
        assertEquals("EUR", result.toCurrency)
        assertEquals(100.0, result.amount, 0.01)
        assertEquals(85.0, result.convertedAmount, 0.01)
        assertEquals(0.85, result.rate, 0.01)
    }

    @Test
    fun `convertCurrency from EUR to USD should return correct result`() = runTest {
        // Given
        val mockResponse = ExchangeRateResponse(
            result                  = "success",
            documentation           = "",
            termsOfUse              = "",
            timeLastUpdateUnix      = 0L,
            timeLastUpdateUtc       = "",
            timeNextUpdateUnix      = 0L,
            timeNextUpdateUtc       = "",
            baseCode                = "USD",
            conversionRates         = mapOf(
                "USD" to 1.0,
                "EUR" to 0.85,
                "GBP" to 0.73
            )
        )
        whenever(mockApi.getExchangeRates()).thenReturn(Result.success(mockResponse))
        
        // When
        repository.fetchExchangeRates()
        val result = repository.convertCurrency("EUR", "USD", 100.0)
        
        // Then
        assertNotNull(result)
        assertEquals("EUR", result!!.fromCurrency)
        assertEquals("USD", result.toCurrency)
        assertEquals(100.0, result.amount, 0.01)
        assertEquals(117.65, result.convertedAmount, 0.01) // 100 / 0.85
        assertEquals(1.176, result.rate, 0.01) // 1 / 0.85
    }

    @Test
    fun `convertCurrency between non-USD currencies should convert through USD`() = runTest {
        // Given
        val mockResponse = ExchangeRateResponse(
            result                  = "success",
            documentation           = "",
            termsOfUse              = "",
            timeLastUpdateUnix      = 0L,
            timeLastUpdateUtc       = "",
            timeNextUpdateUnix      = 0L,
            timeNextUpdateUtc       = "",
            baseCode                = "USD",
            conversionRates         = mapOf(
                "USD" to 1.0,
                "EUR" to 0.85,
                "GBP" to 0.73
            )
        )
        whenever(mockApi.getExchangeRates()).thenReturn(Result.success(mockResponse))
        
        // When
        repository.fetchExchangeRates()
        val result = repository.convertCurrency("EUR", "GBP", 100.0)
        
        // Then
        assertNotNull(result)
        assertEquals("EUR", result!!.fromCurrency)
        assertEquals("GBP", result.toCurrency)
        assertEquals(100.0, result.amount, 0.01)
        // 100 EUR -> 100/0.85 USD -> (100/0.85) * 0.73 GBP = 85.88 GBP
        assertEquals(85.88, result.convertedAmount, 0.01)
        assertEquals(0.858, result.rate, 0.01) // 0.73 / 0.85
    }

    @Test
    fun `convertCurrency with null exchange rates should return null`() {
        // When
        val result = repository.convertCurrency("USD", "EUR", 100.0)
        
        // Then
        assertNull(result)
    }

    @Test
    fun `convertCurrency with invalid currency should return null`() = runTest {
        // Given
        val mockResponse = ExchangeRateResponse(
            result                  = "success",
            documentation           = "",
            termsOfUse              = "",
            timeLastUpdateUnix      = 0L,
            timeLastUpdateUtc       = "",
            timeNextUpdateUnix      = 0L,
            timeNextUpdateUtc       = "",
            baseCode                = "USD",
            conversionRates         = mapOf(
                "USD" to 1.0,
                "EUR" to 0.85
            )
        )
        whenever(mockApi.getExchangeRates()).thenReturn(Result.success(mockResponse))
        
        // When
        repository.fetchExchangeRates()
        val result = repository.convertCurrency("USD", "INVALID", 100.0)
        
        // Then
        assertNull(result)
    }
}
