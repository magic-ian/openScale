# openScale Shared Module

This is the **Kotlin Multiplatform shared module** for openScale, containing all platform-independent business logic that is shared between Android and Linux Mobile implementations.

## Purpose

The shared module enables code reuse across different platforms while maintaining native performance and platform-specific implementations where needed.

## Structure

```
shared/src/
├── commonMain/        # Platform-independent code (business logic)
│   └── kotlin/
│       └── com.health.openscale/
│           ├── core/           # Core business logic
│           │   ├── data/       # Data models (User, Measurement, etc.)
│           │   ├── database/   # Database interfaces
│           │   ├── usecase/    # Business logic use cases
│           │   ├── service/    # Services and calculators
│           │   └── bluetooth/  # Scale protocol handlers
│           └── platform/       # Platform interface definitions (expect)
│
├── androidMain/       # Android-specific implementations (actual)
│   └── kotlin/
│       └── com.health.openscale.platform/
│
├── linuxMain/         # Linux-specific implementations (actual)
│   └── kotlin/
│       └── com.health.openscale.platform/
│
└── commonTest/        # Shared tests
    └── kotlin/
```

## What Goes in the Shared Module?

### ✅ Platform-Independent Code (commonMain)

- **Data Models**: User, Measurement, MeasurementType, UserGoals, etc.
- **Business Logic**: All use cases and domain logic
- **Calculations**: BMI, body fat, trend calculations
- **Database Interfaces**: Repository pattern definitions
- **Bluetooth Protocols**: Scale-specific protocol handlers (binary logic)
- **Services**: Data enrichment, validation, calculations
- **Utilities**: CSV import/export, data transformation

### ❌ Platform-Specific Code (androidMain/linuxMain)

- **UI Code**: Keep in android_app/ or linux_app/
- **Platform APIs**: Bluetooth stack, file I/O, notifications
- **Framework-Specific**: Android BLE, BlueZ, Room, etc.

## Using expect/actual Pattern

For features that need platform-specific implementation:

**1. Define interface in commonMain:**
```kotlin
// In commonMain/kotlin/
expect class BluetoothManager {
    fun scanForDevices(): Flow<BluetoothDevice>
}
```

**2. Implement in androidMain:**
```kotlin
// In androidMain/kotlin/
actual class BluetoothManager {
    actual fun scanForDevices(): Flow<BluetoothDevice> {
        // Android BLE implementation
    }
}
```

**3. Implement in linuxMain:**
```kotlin
// In linuxMain/kotlin/
actual class BluetoothManager {
    actual fun scanForDevices(): Flow<BluetoothDevice> {
        // BlueZ D-Bus implementation
    }
}
```

## Dependencies

### Common Dependencies
- Kotlin Coroutines (for async operations)
- Kotlin DateTime (for date/time handling)
- Kotlin Serialization (for data serialization)

### Platform-Specific Dependencies
- **Android**: Added in `androidMain`
- **Linux**: Added in `linuxMain`

## Building

The shared module is built automatically when building the main projects:

```bash
# Build Android app (includes shared module)
cd android_app && ./gradlew build

# Build Linux app (includes shared module)
cd linux_app && ./gradlew build
```

## Testing

Tests in `commonTest` run on all platforms:

```bash
./gradlew :shared:test
```

## Migration Strategy

As the project evolves, more code from `android_app/app/src/main/java/com/health/openscale/core/` will be migrated to this shared module to maximize code reuse.

### Migration Checklist
- [ ] Migrate data models to `commonMain/kotlin/.../core/data/`
- [ ] Migrate use cases to `commonMain/kotlin/.../core/usecase/`
- [ ] Migrate database interfaces to `commonMain/kotlin/.../core/database/`
- [ ] Migrate services to `commonMain/kotlin/.../core/service/`
- [ ] Migrate Bluetooth protocols to `commonMain/kotlin/.../core/bluetooth/`
- [ ] Create platform-specific implementations in `androidMain` and `linuxMain`

## Contributing

When adding new shared code:

1. **Start with commonMain**: Write platform-independent code first
2. **Use expect/actual**: Only when platform-specific features are needed
3. **Add tests**: Write tests in `commonTest`
4. **Document**: Add KDoc comments for public APIs
5. **Keep it clean**: No Android/Linux-specific imports in commonMain

See [CONTRIBUTING.md](../CONTRIBUTING.md) for detailed guidelines.

## License

Part of openScale, licensed under GPL v3. See [LICENSE](../LICENSE).
