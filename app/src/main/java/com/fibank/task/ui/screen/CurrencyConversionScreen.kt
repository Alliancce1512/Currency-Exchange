package com.fibank.task.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fibank.task.R
import com.fibank.task.data.model.ConversionResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConversionScreen(
    fromCurrency        : String,
    toCurrency          : String,
    amount              : String,
    conversionResult    : ConversionResult?,
    onAmountChanged     : (String) -> Unit,
    onReverseCurrencies : () -> Unit,
    onBack              : () -> Unit,
    onRefresh           : () -> Unit = {}
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title           = { Text(context.getString(R.string.currency_conversion_title)) },
                navigationIcon  = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector         = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription  = context.getString(R.string.back_content_description)
                        )
                    }
                },
                actions         = {
                    IconButton(onClick = onRefresh) {
                        Icon(
                            imageVector         = Icons.Default.Refresh,
                            contentDescription  = context.getString(R.string.refresh_rates_content_description)
                        )
                    }
                },
                windowInsets    = WindowInsets(top = 0.dp, bottom = 0.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier            = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Currency Pair Display
            Card(
                modifier    = Modifier.fillMaxWidth(),
                colors      = cardColors(containerColor = colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier                = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement   = Arrangement.SpaceBetween,
                    verticalAlignment       = Alignment.CenterVertically
                ) {
                    // Left Currency (From)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text        = fromCurrency,
                            style       = typography.headlineMedium,
                            fontWeight  = FontWeight.Bold
                        )

                        Text(
                            text    = context.getString(R.string.from_label),
                            style   = typography.bodySmall,
                            color   = colorScheme.onSurfaceVariant
                        )
                    }
                    
                    // Swap Button
                    IconButton(
                        modifier    = Modifier
                            .size(48.dp)
                            .background(
                                color = colorScheme.primary,
                                shape = CircleShape
                            ),
                        onClick     = onReverseCurrencies
                    ) {
                        Icon(
                            modifier            = Modifier.size(24.dp),
                            imageVector         = Icons.Default.SwapHoriz,
                            contentDescription  = context.getString(R.string.reverse_currencies_content_description),
                            tint                = colorScheme.onPrimary
                        )
                    }
                    
                    // Right Currency (To)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text        = toCurrency,
                            style       = typography.headlineMedium,
                            fontWeight  = FontWeight.Bold
                        )

                        Text(
                            text    = context.getString(R.string.to_label),
                            style   = typography.bodySmall,
                            color   = colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            // Amount Input
            OutlinedTextField(
                modifier        = Modifier.fillMaxWidth(),
                value           = amount,
                onValueChange   = onAmountChanged,
                label           = { Text(context.getString(R.string.amount_to_convert_label)) },
                placeholder     = { Text(context.getString(R.string.amount_placeholder)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine      = true
            )
            
            // Conversion Result
            if (conversionResult != null) {
                Card(
                    modifier    = Modifier.fillMaxWidth(),
                    colors      = cardColors(containerColor = colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text    = context.getString(R.string.converted_amount_label),
                            style   = typography.labelLarge,
                            color   = colorScheme.onPrimaryContainer
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text        = String.format(context.getString(R.string.converted_amount_format), conversionResult.convertedAmount, conversionResult.toCurrency),
                            style       = typography.headlineLarge,
                            fontWeight  = FontWeight.Bold,
                            color       = colorScheme.onPrimaryContainer,
                            textAlign   = TextAlign.Center
                        )
                    }
                }
                
                // Exchange Rate Info
                Card(
                    modifier    = Modifier.fillMaxWidth(),
                    colors      = cardColors(containerColor = colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text    = context.getString(R.string.exchange_rate_label),
                            style   = typography.labelMedium,
                            color   = colorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(4.dp))
                        
                        Text(
                            text        = context.getString(R.string.exchange_rate_format, conversionResult.fromCurrency, conversionResult.rate, conversionResult.toCurrency),
                            style       = typography.bodyLarge,
                            fontWeight  = FontWeight.Medium,
                            textAlign   = TextAlign.Center
                        )
                    }
                }
            } else if (amount.isNotBlank()) {
                Card(
                    modifier    = Modifier.fillMaxWidth(),
                    colors      = cardColors(containerColor = colorScheme.errorContainer)
                ) {
                    Text(
                        modifier    = Modifier.padding(16.dp),
                        text        = context.getString(R.string.unable_to_convert_message),
                        color       = colorScheme.onErrorContainer,
                        textAlign   = TextAlign.Center
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Info Text
            Text(
                modifier    = Modifier.fillMaxWidth(),
                text        = context.getString(R.string.exchange_rate_info),
                style       = typography.bodySmall,
                color       = colorScheme.onSurfaceVariant,
                textAlign   = TextAlign.Center
            )
        }
    }
}