package com.fibank.task.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CurrencyUtilsTest {

    @Test
    fun `generateCurrenciesFromRates should create correct currency list`() {
        // Given
        val rates       = mapOf(
            "USD" to 1.0,
            "EUR" to 0.85,
            "GBP" to 0.73,
            "JPY" to 110.0
        )

        // When
        val currencies  = CurrencyUtils.generateCurrenciesFromRates(rates)

        // Then
        assertEquals(4, currencies.size)
        assertEquals("EUR", currencies[0].code) // Sorted by code
        assertEquals("Euro", currencies[0].name)
        assertEquals("GBP", currencies[1].code)
        assertEquals("British Pound Sterling", currencies[1].name)
        assertEquals("JPY", currencies[2].code)
        assertEquals("Japanese Yen", currencies[2].name)
        assertEquals("USD", currencies[3].code)
        assertEquals("US Dollar", currencies[3].name)
    }

    @Test
    fun `generateCurrenciesFromRates should handle unknown currency codes`() {
        // Given
        val rates       = mapOf(
            "USD" to 1.0,
            "UNKNOWN" to 2.0
        )

        // When
        val currencies  = CurrencyUtils.generateCurrenciesFromRates(rates)

        // Then
        assertEquals(2, currencies.size)
        val unknownCurrency = currencies.find { it.code == "UNKNOWN" }
        assertNotNull(unknownCurrency)
        assertEquals("UNKNOWN", unknownCurrency?.name) // Should fallback to code
    }

    @Test
    fun `getCurrencyName should return correct name for known currency`() {
        // When
        val name = CurrencyUtils.getCurrencyName("USD")

        // Then
        assertEquals("US Dollar", name)
    }

    @Test
    fun `getCurrencyName should return code for unknown currency`() {
        // When
        val name = CurrencyUtils.getCurrencyName("UNKNOWN")

        // Then
        assertEquals("UNKNOWN", name)
    }

    @Test
    fun `getAllCurrencyCodes should return all supported currencies`() {
        // When
        val codes = CurrencyUtils.getAllCurrencyCodes()

        // Then
        assertTrue(codes.contains("USD"))
        assertTrue(codes.contains("EUR"))
        assertTrue(codes.contains("GBP"))
        assertTrue(codes.contains("JPY"))
        assertTrue(codes.size > 100) // Should have many currencies
    }
}
