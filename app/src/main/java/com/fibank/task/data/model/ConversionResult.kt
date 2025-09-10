package com.fibank.task.data.model

data class ConversionResult(
    val fromCurrency    : String,
    val toCurrency      : String,
    val amount          : Double,
    val convertedAmount : Double,
    val rate            : Double
)