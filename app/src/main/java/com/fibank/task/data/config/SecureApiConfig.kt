package com.fibank.task.data.config

import com.fibank.task.BuildConfig
import java.io.File
import java.io.FileInputStream
import java.util.Properties

object SecureApiConfig {
    private const val ENDPOINT_LATEST = "latest/USD"
    
    // Try to load from local.properties first, fallback to BuildConfig
    private val apiKey: String by lazy {
        try {
            val properties          = Properties()
            val localPropertiesFile = File("local.properties")

            if (localPropertiesFile.exists()) {
                properties.load(FileInputStream(localPropertiesFile))
                properties.getProperty("EXCHANGE_RATE_API_KEY") ?: BuildConfig.EXCHANGE_RATE_API_KEY
            } else {
                BuildConfig.EXCHANGE_RATE_API_KEY
            }
        } catch (_: Exception) {
            BuildConfig.EXCHANGE_RATE_API_KEY
        }
    }
    
    private val baseUrl: String by lazy {
        try {
            val properties          = Properties()
            val localPropertiesFile = File("local.properties")

            if (localPropertiesFile.exists()) {
                properties.load(FileInputStream(localPropertiesFile))
                properties.getProperty("EXCHANGE_RATE_BASE_URL") ?: BuildConfig.EXCHANGE_RATE_BASE_URL
            } else {
                BuildConfig.EXCHANGE_RATE_BASE_URL
            }
        } catch (_: Exception) {
            BuildConfig.EXCHANGE_RATE_BASE_URL
        }
    }
    
    val EXCHANGE_RATES_URL = "$baseUrl$apiKey/$ENDPOINT_LATEST"
}