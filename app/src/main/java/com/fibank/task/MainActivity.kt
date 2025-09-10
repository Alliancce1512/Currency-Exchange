package com.fibank.task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.fibank.task.data.api.ExchangeRateApiImpl
import com.fibank.task.data.repository.ExchangeRateRepository
import com.fibank.task.navigation.CurrencyNavigation
import com.fibank.task.ui.theme.FibankTheme
import com.fibank.task.ui.viewmodel.CurrencyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            FibankTheme {
                CurrencyApp()
            }
        }
    }
}

class CurrencyViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            val repository = ExchangeRateRepository(ExchangeRateApiImpl())
            @Suppress("UNCHECKED_CAST")
            return CurrencyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun CurrencyApp() {
    val navController                   = rememberNavController()
    val viewModel: CurrencyViewModel    = viewModel(factory = CurrencyViewModelFactory())
    
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        CurrencyNavigation(
            modifier        = Modifier.padding(innerPadding),
            navController   = navController,
            viewModel       = viewModel
        )
    }
}