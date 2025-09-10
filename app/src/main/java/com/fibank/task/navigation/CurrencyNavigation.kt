package com.fibank.task.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fibank.task.ui.screen.CurrencyConversionScreen
import com.fibank.task.ui.screen.CurrencySelectionScreen
import com.fibank.task.ui.viewmodel.CurrencyViewModel

@Composable
fun CurrencyNavigation(
    navController   : NavHostController,
    viewModel       : CurrencyViewModel,
    modifier        : Modifier = Modifier
) {
    NavHost(
        navController       = navController,
        startDestination    = "currency_selection",
        modifier            = modifier
    ) {
       composable("currency_selection") {
           val state by viewModel.uiState.collectAsState()

           CurrencySelectionScreen(
               availableCurrencies      = state.availableCurrencies,
               fromCurrency             = state.fromCurrency,
               toCurrency               = state.toCurrency,
               amount                   = state.amount,
               isLoading                = state.isLoading,
               error                    = state.error,
               onFromCurrencySelected   = viewModel::setFromCurrency,
               onToCurrencySelected     = viewModel::setToCurrency,
               onAmountChanged          = viewModel::setAmount,
               onContinue               = { navController.navigate("currency_conversion") },
               onClearError             = viewModel::clearError
           )
       }
        
        composable("currency_conversion") {
            val state by viewModel.uiState.collectAsState()

            CurrencyConversionScreen(
                fromCurrency        = state.fromCurrency?.code ?: "",
                toCurrency          = state.toCurrency?.code ?: "",
                amount              = state.amount,
                conversionResult    = state.conversionResult,
                onAmountChanged     = viewModel::setAmount,
                onReverseCurrencies = viewModel::reverseCurrencies,
                onBack              = navController::popBackStack,
                onRefresh           = viewModel::refreshExchangeRates
            )
        }
    }
}