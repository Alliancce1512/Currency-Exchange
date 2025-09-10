package com.fibank.task.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fibank.task.R
import com.fibank.task.data.model.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelectionScreen(
    modifier                : Modifier = Modifier,
    availableCurrencies     : List<Currency>,
    fromCurrency            : Currency?,
    toCurrency              : Currency?,
    amount                  : String,
    isLoading               : Boolean = false,
    error                   : String? = null,
    onFromCurrencySelected  : (Currency) -> Unit,
    onToCurrencySelected    : (Currency) -> Unit,
    onAmountChanged         : (String) -> Unit,
    onContinue              : () -> Unit,
    onClearError            : () -> Unit
) {
    val context                  = LocalContext.current
    var showFromCurrencyDialog  by remember { mutableStateOf(false) }
    var showToCurrencyDialog    by remember { mutableStateOf(false) }
    
    Column(
        modifier            = modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text        = context.getString(R.string.currency_converter_title),
            style       = typography.headlineMedium,
            fontWeight  = FontWeight.Bold
        )
        
        Text(
            text    = context.getString(R.string.currency_converter_subtitle),
            style   = typography.bodyMedium,
            color   = colorScheme.onSurfaceVariant
        )
        
        // Loading indicator
        if (isLoading) {
            Box(
                modifier            = Modifier.fillMaxWidth(),
                contentAlignment    = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        
        // Error message
        error?.let { errorMessage ->
            Card(
                modifier    = Modifier.fillMaxWidth(),
                colors      = cardColors(containerColor = colorScheme.errorContainer)
            ) {
                Row(
                    modifier            = Modifier.padding(16.dp),
                    verticalAlignment   = Alignment.CenterVertically
                ) {
                    Text(
                        modifier    = Modifier.weight(1f),
                        text        = errorMessage,
                        color       = colorScheme.onErrorContainer,
                    )

                    TextButton(onClick = onClearError) {
                        Text(context.getString(R.string.dismiss))
                    }
                }
            }
        }
        
        // Amount Input
        OutlinedTextField(
            modifier            = Modifier.fillMaxWidth(),
            value               = amount,
            onValueChange       = onAmountChanged,
            label               = { Text(context.getString(R.string.amount_label)) },
            placeholder         = { Text(context.getString(R.string.amount_placeholder)) },
            keyboardOptions     = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine          = true
        )
        
        // From Currency Selection
        Card(
            modifier    = Modifier.fillMaxWidth(),
            onClick     = { showFromCurrencyDialog = true }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text    = context.getString(R.string.from_currency_label),
                    style   = typography.labelMedium,
                    color   = colorScheme.onSurfaceVariant
                )

                Text(
                    text        = fromCurrency?.toString() ?: context.getString(R.string.select_currency_placeholder),
                    style       = typography.bodyLarge,
                    fontWeight  = fromCurrency?.let { FontWeight.Medium } ?: FontWeight.Normal
                )
            }
        }
        
        // To Currency Selection
        Card(
            modifier    = Modifier.fillMaxWidth(),
            onClick     = { showToCurrencyDialog = true }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text    = context.getString(R.string.to_currency_label),
                    style   = typography.labelMedium,
                    color   = colorScheme.onSurfaceVariant
                )
                Text(
                    text        = toCurrency?.toString() ?: context.getString(R.string.select_currency_placeholder),
                    style       = typography.bodyLarge,
                    fontWeight  = toCurrency?.let { FontWeight.Medium } ?: FontWeight.Normal
                )
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Continue Button
        Button(
            modifier    = Modifier.fillMaxWidth(),
            onClick     = onContinue,
            enabled     = fromCurrency != null && toCurrency != null && amount.isNotBlank()
        ) {
            Text(context.getString(R.string.continue_to_conversion))
        }
    }
    
    // From Currency Dialog
    if (showFromCurrencyDialog) {
        CurrencySelectionDialog(
            context             = context,
            currencies          = availableCurrencies,
            onCurrencySelected  = { currency ->
                onFromCurrencySelected(currency)
                showFromCurrencyDialog = false
            },
            onDismiss           = { showFromCurrencyDialog = false }
        )
    }
    
    // To Currency Dialog
    if (showToCurrencyDialog) {
        CurrencySelectionDialog(
            context             = context,
            currencies          = availableCurrencies,
            onCurrencySelected  = { currency ->
                onToCurrencySelected(currency)
                showToCurrencyDialog = false
            },
            onDismiss           = { showToCurrencyDialog = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CurrencySelectionDialog(
    context             : Context,
    currencies          : List<Currency>,
    onCurrencySelected  : (Currency) -> Unit,
    onDismiss           : () -> Unit
) {
    var searchQuery         by remember { mutableStateOf("") }
    var filteredCurrencies  by remember { mutableStateOf(currencies) }
    
    // Filter currencies based on search query
    LaunchedEffect(searchQuery, currencies) {
        filteredCurrencies =
            if (searchQuery.isBlank()) {
                currencies
            } else {
                currencies.filter { currency ->
                    currency.code.contains(searchQuery, ignoreCase = true) ||
                    currency.name.contains(searchQuery, ignoreCase = true)
                }
            }
    }
    
    AlertDialog(
        onDismissRequest    = onDismiss,
        title               = { Text(context.getString(R.string.select_currency_dialog_title)) },
        text                = {
            Column {
                // Search field
                OutlinedTextField(
                    modifier            = Modifier.fillMaxWidth(),
                    value               = searchQuery,
                    onValueChange       = { searchQuery = it },
                    placeholder         = { Text(context.getString(R.string.search_currency_placeholder)) },
                    singleLine          = true,
                    keyboardOptions     = KeyboardOptions(imeAction = ImeAction.Search)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Currency list
                if (filteredCurrencies.isEmpty()) {
                    Box(
                        modifier            = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 100.dp),
                        contentAlignment    = Alignment.Center
                    ) {
                        Text(
                            text    = context.getString(R.string.no_currencies_found),
                            style   = typography.bodyMedium,
                            color   = colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(modifier = Modifier.heightIn(max = 300.dp)) {
                        items(items = filteredCurrencies, key = { currency -> currency.code }) { currency ->
                            Card(
                                modifier    = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp),
                                onClick     = { onCurrencySelected(currency) }
                            ) {
                                Text(
                                    modifier    = Modifier.padding(16.dp),
                                    text        = currency.toString()
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton       = {
            TextButton(onClick = onDismiss) {
                Text(context.getString(R.string.cancel))
            }
        }
    )
}