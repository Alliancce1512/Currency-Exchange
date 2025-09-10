package com.fibank.task.utils

import com.fibank.task.data.model.Currency

object CurrencyUtils {
    
    /**
     * Comprehensive mapping of currency codes to their full names
     * Based on ISO 4217 currency codes and common usage
     */
    private val currencyNames = mapOf(
        // A
        "AED" to "UAE Dirham",
        "AFN" to "Afghan Afghani",
        "ALL" to "Albanian Lek",
        "AMD" to "Armenian Dram",
        "ANG" to "Netherlands Antillean Guilder",
        "AOA" to "Angolan Kwanza",
        "ARS" to "Argentine Peso",
        "AUD" to "Australian Dollar",
        "AWG" to "Aruban Florin",
        "AZN" to "Azerbaijani Manat",
        
        // B
        "BAM" to "Bosnia-Herzegovina Convertible Mark",
        "BBD" to "Barbadian Dollar",
        "BDT" to "Bangladeshi Taka",
        "BGN" to "Bulgarian Lev",
        "BHD" to "Bahraini Dinar",
        "BIF" to "Burundian Franc",
        "BMD" to "Bermudian Dollar",
        "BND" to "Brunei Dollar",
        "BOB" to "Bolivian Boliviano",
        "BRL" to "Brazilian Real",
        "BSD" to "Bahamian Dollar",
        "BTN" to "Bhutanese Ngultrum",
        "BWP" to "Botswanan Pula",
        "BYN" to "Belarusian Ruble",
        "BZD" to "Belize Dollar",
        
        // C
        "CAD" to "Canadian Dollar",
        "CDF" to "Congolese Franc",
        "CHF" to "Swiss Franc",
        "CLP" to "Chilean Peso",
        "CNY" to "Chinese Yuan",
        "COP" to "Colombian Peso",
        "CRC" to "Costa Rican Colón",
        "CUP" to "Cuban Peso",
        "CVE" to "Cape Verdean Escudo",
        "CZK" to "Czech Koruna",
        
        // D
        "DJF" to "Djiboutian Franc",
        "DKK" to "Danish Krone",
        "DOP" to "Dominican Peso",
        "DZD" to "Algerian Dinar",
        
        // E
        "EGP" to "Egyptian Pound",
        "ERN" to "Eritrean Nakfa",
        "ETB" to "Ethiopian Birr",
        "EUR" to "Euro",
        
        // F
        "FJD" to "Fijian Dollar",
        "FKP" to "Falkland Islands Pound",
        "FOK" to "Faroese Króna",
        
        // G
        "GBP" to "British Pound Sterling",
        "GEL" to "Georgian Lari",
        "GGP" to "Guernsey Pound",
        "GHS" to "Ghanaian Cedi",
        "GIP" to "Gibraltar Pound",
        "GMD" to "Gambian Dalasi",
        "GNF" to "Guinean Franc",
        "GTQ" to "Guatemalan Quetzal",
        "GYD" to "Guyanaese Dollar",
        
        // H
        "HKD" to "Hong Kong Dollar",
        "HNL" to "Honduran Lempira",
        "HRK" to "Croatian Kuna",
        "HTG" to "Haitian Gourde",
        "HUF" to "Hungarian Forint",
        
        // I
        "IDR" to "Indonesian Rupiah",
        "ILS" to "Israeli New Shekel",
        "IMP" to "Manx Pound",
        "INR" to "Indian Rupee",
        "IQD" to "Iraqi Dinar",
        "IRR" to "Iranian Rial",
        "ISK" to "Icelandic Króna",
        
        // J
        "JEP" to "Jersey Pound",
        "JMD" to "Jamaican Dollar",
        "JOD" to "Jordanian Dinar",
        "JPY" to "Japanese Yen",
        
        // K
        "KES" to "Kenyan Shilling",
        "KGS" to "Kyrgyzstani Som",
        "KHR" to "Cambodian Riel",
        "KID" to "Kiribati Dollar",
        "KMF" to "Comorian Franc",
        "KRW" to "South Korean Won",
        "KWD" to "Kuwaiti Dinar",
        "KYD" to "Cayman Islands Dollar",
        "KZT" to "Kazakhstani Tenge",
        
        // L
        "LAK" to "Laotian Kip",
        "LBP" to "Lebanese Pound",
        "LKR" to "Sri Lankan Rupee",
        "LRD" to "Liberian Dollar",
        "LSL" to "Lesotho Loti",
        "LYD" to "Libyan Dinar",
        
        // M
        "MAD" to "Moroccan Dirham",
        "MDL" to "Moldovan Leu",
        "MGA" to "Malagasy Ariary",
        "MKD" to "Macedonian Denar",
        "MMK" to "Myanmar Kyat",
        "MNT" to "Mongolian Tugrik",
        "MOP" to "Macanese Pataca",
        "MRU" to "Mauritanian Ouguiya",
        "MUR" to "Mauritian Rupee",
        "MVR" to "Maldivian Rufiyaa",
        "MWK" to "Malawian Kwacha",
        "MXN" to "Mexican Peso",
        "MYR" to "Malaysian Ringgit",
        "MZN" to "Mozambican Metical",
        
        // N
        "NAD" to "Namibian Dollar",
        "NGN" to "Nigerian Naira",
        "NIO" to "Nicaraguan Córdoba",
        "NOK" to "Norwegian Krone",
        "NPR" to "Nepalese Rupee",
        "NZD" to "New Zealand Dollar",
        
        // O
        "OMR" to "Omani Rial",
        
        // P
        "PAB" to "Panamanian Balboa",
        "PEN" to "Peruvian Sol",
        "PGK" to "Papua New Guinean Kina",
        "PHP" to "Philippine Peso",
        "PKR" to "Pakistani Rupee",
        "PLN" to "Polish Zloty",
        "PYG" to "Paraguayan Guarani",
        
        // Q
        "QAR" to "Qatari Riyal",
        
        // R
        "RON" to "Romanian Leu",
        "RSD" to "Serbian Dinar",
        "RUB" to "Russian Ruble",
        "RWF" to "Rwandan Franc",
        
        // S
        "SAR" to "Saudi Riyal",
        "SBD" to "Solomon Islands Dollar",
        "SCR" to "Seychellois Rupee",
        "SDG" to "Sudanese Pound",
        "SEK" to "Swedish Krona",
        "SGD" to "Singapore Dollar",
        "SHP" to "Saint Helena Pound",
        "SLE" to "Sierra Leonean Leone",
        "SLL" to "Sierra Leonean Leone (Old)",
        "SOS" to "Somali Shilling",
        "SRD" to "Surinamese Dollar",
        "SSP" to "South Sudanese Pound",
        "STN" to "São Tomé and Príncipe Dobra",
        "SYP" to "Syrian Pound",
        "SZL" to "Swazi Lilangeni",
        
        // T
        "THB" to "Thai Baht",
        "TJS" to "Tajikistani Somoni",
        "TMT" to "Turkmenistani Manat",
        "TND" to "Tunisian Dinar",
        "TOP" to "Tongan Pa'anga",
        "TRY" to "Turkish Lira",
        "TTD" to "Trinidad and Tobago Dollar",
        "TVD" to "Tuvaluan Dollar",
        "TWD" to "Taiwan New Dollar",
        "TZS" to "Tanzanian Shilling",
        
        // U
        "UAH" to "Ukrainian Hryvnia",
        "UGX" to "Ugandan Shilling",
        "USD" to "US Dollar",
        "UYU" to "Uruguayan Peso",
        "UZS" to "Uzbekistani Som",
        
        // V
        "VES" to "Venezuelan Bolívar Soberano",
        "VND" to "Vietnamese Dong",
        "VUV" to "Vanuatu Vatu",
        
        // W
        "WST" to "Samoan Tala",
        
        // X
        "XAF" to "Central African CFA Franc",
        "XCD" to "East Caribbean Dollar",
        "XCG" to "East Caribbean Dollar (Old)",
        "XDR" to "Special Drawing Rights",
        "XOF" to "West African CFA Franc",
        "XPF" to "CFP Franc",
        
        // Y
        "YER" to "Yemeni Rial",
        
        // Z
        "ZAR" to "South African Rand",
        "ZMW" to "Zambian Kwacha",
        "ZWL" to "Zimbabwean Dollar"
    )
    
    /**
     * Generates a list of Currency objects from exchange rate data
     * @param rates Map of currency codes to exchange rates
     * @return List of Currency objects sorted by currency code
     */
    fun generateCurrenciesFromRates(rates: Map<String, Double>): List<Currency> {
        return rates.keys.map { code ->
            Currency(
                code = code,
                name = currencyNames[code] ?: code // Fallback to code if name not found
            )
        }.sortedBy { it.code }
    }
    
    /**
     * Gets the display name for a currency code
     * @param code Currency code (e.g., "USD")
     * @return Full currency name or the code if not found
     */
    fun getCurrencyName(code: String): String {
        return currencyNames[code] ?: code
    }
    
    /**
     * Gets all available currency codes
     * @return Set of all supported currency codes
     */
    fun getAllCurrencyCodes(): Set<String> {
        return currencyNames.keys
    }
}