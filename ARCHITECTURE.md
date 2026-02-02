# openScale Architecture

## Overview

openScale is a Kotlin-based application that tracks weight and body metrics with support for Bluetooth scales. The architecture is designed to support multiple platforms including Android and Linux Mobile (phosh & Plasma Mobile).

## Multiplatform Architecture

### Design Principles

1. **Shared Business Logic**: Core business logic, data models, and use cases are written once and shared across all platforms
2. **Platform-Specific UI**: Each platform implements its native UI framework (Jetpack Compose for Android, GTK/Qt for Linux)
3. **Platform Abstractions**: Platform-specific features (Bluetooth, storage, notifications) use expect/actual patterns
4. **Maintainability**: Single Kotlin codebase that scales across platforms while respecting platform conventions

### Project Structure

```
openScale/
├── android_app/           # Android-specific implementation
│   └── app/
│       └── src/
│           └── main/
│               └── java/com/health/openscale/
│                   ├── MainActivity.kt           # Android entry point
│                   └── ui/                       # Android UI (Jetpack Compose)
│
├── shared/               # Shared Kotlin Multiplatform code
│   └── src/
│       ├── commonMain/   # Platform-independent code
│       │   └── kotlin/com/health/openscale/
│       │       ├── core/
│       │       │   ├── data/          # Data models
│       │       │   ├── database/      # Database interfaces
│       │       │   ├── usecase/       # Business logic
│       │       │   └── service/       # Services
│       │       └── platform/          # Platform interface definitions (expect)
│       │
│       ├── androidMain/  # Android-specific implementations (actual)
│       │   └── kotlin/com/health/openscale/platform/
│       │
│       └── linuxMain/    # Linux-specific implementations (actual)
│           └── kotlin/com/health/openscale/platform/
│
└── linux_app/            # Linux Mobile application
    └── src/
        └── main/
            └── kotlin/com/health/openscale/
                └── ui/               # Linux UI (GTK or Qt)
```

### Shared Components (commonMain)

The following components are platform-independent and reside in the `shared` module:

#### Data Layer
- **Domain Models**: `User`, `Measurement`, `MeasurementValue`, `MeasurementType`, `UserGoals`
- **Enums**: Weight units, measurement types, gender, activity levels
- **Database Interfaces**: Repository patterns for data access

#### Business Logic Layer
- **Use Cases**: All business logic operations
  - `MeasurementCrudUseCases` - Create, read, update, delete measurements
  - `UserUseCases` - User management
  - `MeasurementQueryUseCases` - Data queries and filtering
  - `MeasurementEvaluationUseCases` - BMI, body fat calculations
  - `ImportExportUseCases` - CSV import/export
  - `BackupRestoreUseCases` - Data backup and restore

#### Service Layer
- **Calculators**: Trend calculation, data smoothing
- **Enrichers**: Data enrichment and validation
- **Protocol Handlers**: Bluetooth scale protocols (device-specific)

### Platform-Specific Components

#### Android (androidMain)
- **UI Framework**: Jetpack Compose + Material 3
- **Bluetooth**: Android BLE APIs
- **Storage**: Room Database + DataStore Preferences
- **Background Tasks**: WorkManager
- **Notifications**: Android NotificationManager

#### Linux (linuxMain)
- **UI Framework**: 
  - **For phosh**: GTK4 + Libadwaita (GNOME-based)
  - **For Plasma Mobile**: Qt/QML + Kirigami
- **Bluetooth**: BlueZ via D-Bus
- **Storage**: SQLite + File-based preferences (GSettings or config files)
- **Background Tasks**: systemd timers or cron
- **Notifications**: D-Bus notification system

### Platform Interface Pattern (expect/actual)

Platform-specific features use Kotlin's `expect`/`actual` mechanism:

```kotlin
// In commonMain
expect class BluetoothManager {
    fun scanForDevices(): Flow<BluetoothDevice>
    fun connect(device: BluetoothDevice): BluetoothConnection
}

// In androidMain
actual class BluetoothManager {
    actual fun scanForDevices(): Flow<BluetoothDevice> {
        // Android BLE implementation
    }
    actual fun connect(device: BluetoothDevice): BluetoothConnection {
        // Android connection implementation
    }
}

// In linuxMain
actual class BluetoothManager {
    actual fun scanForDevices(): Flow<BluetoothDevice> {
        // BlueZ D-Bus implementation
    }
    actual fun connect(device: BluetoothDevice): BluetoothConnection {
        // BlueZ connection implementation
    }
}
```

## Key Architectural Decisions

### 1. Kotlin Multiplatform over Flutter/React Native
- **Rationale**: Maintains the existing Kotlin codebase and respects the work of oliexdev and contributors
- **Benefits**: 
  - Code reuse of existing business logic
  - Type-safe, native performance
  - Platform-specific UI feels native on each platform

### 2. Separate UI per Platform
- **Rationale**: Each mobile ecosystem has distinct design languages and user expectations
- **Benefits**:
  - Native look and feel (Material You on Android, Libadwaita on GNOME, Kirigami on Plasma)
  - Better platform integration
  - Optimal performance and UX

### 3. Dependency Injection
- **Android**: Hilt (existing)
- **Linux**: Koin or manual dependency injection
- **Shared**: Factory patterns and interfaces

### 4. Database Strategy
- **Android**: Room ORM (existing)
- **Linux**: SQLDelight (multiplatform) or direct SQLite
- **Shared**: Repository pattern abstracts implementation

## Building for Different Platforms

### Android
```bash
cd android_app
./gradlew assembleDebug
```

### Linux Mobile
```bash
cd linux_app
./gradlew build
# Output: Flatpak or AppImage for distribution
```

## Testing Strategy

1. **Unit Tests**: Test shared business logic in `commonTest`
2. **Platform Tests**: Test platform-specific implementations
3. **Integration Tests**: End-to-end tests per platform

## Contributing

When contributing to openScale:

1. **Shared Logic**: Add new business logic to `shared/src/commonMain/`
2. **Platform Features**: Use expect/actual pattern for platform-specific code
3. **UI Changes**: Implement in both platforms' UI layers
4. **Database Changes**: Update repository interfaces and both implementations
5. **Testing**: Add tests in appropriate test source sets

## Future Considerations

- **iOS Support**: The multiplatform architecture can be extended to iOS
- **Desktop Linux**: Same codebase can support full desktop Linux distributions
- **Web**: Kotlin/JS could enable a web version with the same business logic

## Credits

This architecture respects and builds upon the excellent work by:
- **oliexdev** and all contributors to the original Android application
- The openScale community for their valuable feedback and contributions

---

For more information, see:
- [README.md](./README.md) - Project overview
- [Contributing Guidelines](https://github.com/oliexdev/openScale/wiki) - How to contribute
- [Supported Scales](https://github.com/oliexdev/openScale/wiki/Supported-scales-in-openScale) - Device compatibility
