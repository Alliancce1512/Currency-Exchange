package com.fibank.task.data.api

import com.fibank.task.data.config.SecureApiConfig
import com.fibank.task.data.model.ExchangeRateResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface ExchangeRateApi {
    suspend fun getExchangeRates(): Result<ExchangeRateResponse>
}

class ExchangeRateApiImpl : ExchangeRateApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys   = true
                isLenient           = true
                coerceInputValues   = true
            })
        }
    }
    
    override suspend fun getExchangeRates(): Result<ExchangeRateResponse> {
        return try {
            val response = client.get(SecureApiConfig.EXCHANGE_RATES_URL) {
                headers {
                    append("Accept", "application/json")
                }
            }.body<ExchangeRateResponse>()

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
