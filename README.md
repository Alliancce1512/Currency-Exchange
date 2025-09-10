# Fibank Currency Converter

A modern Android currency conversion app built with Kotlin and Jetpack Compose, featuring real-time exchange rates and an intuitive user interface.

## 🌟 Features

### Core Functionality
- **Real-time Exchange Rates**: Fetches live exchange rates from ExchangeRate-API
- **Currency Selection**: Choose from 160+ supported currencies with search functionality
- **Amount Input**: Convert any amount with decimal precision
- **Bidirectional Conversion**: Swap currencies with a single tap
- **Live Conversion**: Real-time calculation as you type

### User Experience
- **Modern UI**: Built with Material 3 design system
- **Search & Filter**: Quick currency search in selection dialogs
- **Keyboard-Friendly**: Proper keyboard handling and input validation
- **Responsive Design**: Optimized for both portrait and landscape orientations
- **Error Handling**: User-friendly error messages and loading states

### Technical Features
- **MVVM Architecture**: Clean separation of concerns
- **Kotlin Coroutines**: Asynchronous operations and reactive programming
- **Ktor Client**: Modern HTTP client for API requests
- **Navigation Component**: Type-safe navigation between screens
- **Unit Tests**: Comprehensive test coverage for business logic

## 🏗️ Architecture

### MVVM Pattern
```
UI Layer (Compose) ↔ ViewModel ↔ Repository ↔ API
```

- **UI Layer**: Jetpack Compose screens and components
- **ViewModel**: Manages UI state and business logic
- **Repository**: Abstracts data sources and conversion logic
- **API**: Ktor HTTP client for external data

### Key Components
- `CurrencySelectionScreen`: Currency and amount input
- `CurrencyConversionScreen`: Conversion results and swapping
- `CurrencyViewModel`: State management and business logic
- `ExchangeRateRepository`: Data management and conversion calculations
- `ExchangeRateApi`: HTTP client for API communication

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- JDK 11 or later
- Android SDK API 24+ (Android 7.0)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Fibank
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Open the project folder
   - Wait for Gradle sync to complete

3. **Configure API Key** (Optional)
   - The app comes with a demo API key
   - For production, update `EXCHANGE_RATE_API_KEY` in `app/build.gradle.kts`
   - Or set it in `local.properties`:
     ```properties
     EXCHANGE_RATE_API_KEY=your_api_key_here
     ```

4. **Build and Run**
   ```bash
   ./gradlew assembleDebug
   ```
   - Or use Android Studio's Run button

## 📱 App Screens

### Currency Selection Screen
- Clean input interface
- Searchable currency selection
- Real-time validation

### Currency Conversion Screen
- Visual currency pair display
- Live conversion results
- Easy currency swapping

## 🛠️ Technical Details

### Dependencies
- **UI**: Jetpack Compose, Material 3
- **Architecture**: ViewModel, Navigation Component
- **Networking**: Ktor Client with JSON serialization
- **Async**: Kotlin Coroutines
- **Testing**: JUnit, Mockito, Coroutines Test

### API Integration
- **Provider**: ExchangeRate-API (v6)
- **Base Currency**: USD
- **Update Frequency**: Daily
- **Rate Limiting**: 1500 requests/month (free tier)

### Security
- **API Key Protection**: Stored in BuildConfig with ProGuard obfuscation
- **Network Security**: HTTPS-only communication
- **Input Validation**: Sanitized user inputs

## 🧪 Testing

### Running Tests
```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest

# All tests
./gradlew check
```

### Test Coverage
- **Repository Tests**: API integration and conversion logic
- **ViewModel Tests**: State management and business logic
- **Utility Tests**: Currency generation and formatting

## 🔧 Configuration

### Build Variants
- **Debug**: Development with logging and debug features
- **Release**: Production build with optimizations and obfuscation

### Environment Variables
- `EXCHANGE_RATE_API_KEY`: Your API key for ExchangeRate-API
- `EXCHANGE_RATE_BASE_URL`: API base URL (default: https://v6.exchangerate-api.com/v6/)

## 📊 Performance

### Optimizations
- **Memory Caching**: Exchange rates cached in memory
- **Efficient Conversions**: Direct rate calculations when possible
- **Lazy Loading**: Currency lists loaded on demand
- **Background Processing**: API calls on background threads

### Network Efficiency
- **Single API Call**: Rates fetched once per session
- **Rate Calculations**: Client-side calculations for all currency pairs
- **Error Recovery**: Automatic retry mechanisms

## 🐛 Troubleshooting

### Common Issues
1. **API Key Invalid**: Check your ExchangeRate-API key
2. **Network Errors**: Ensure internet connectivity
3. **Build Failures**: Clean and rebuild project
4. **Currency Not Found**: Check if currency code is supported

### Debug Mode
Enable debug logging by setting `BuildConfig.DEBUG = true` in development builds.

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add comments for complex logic
- Maintain test coverage above 80%
