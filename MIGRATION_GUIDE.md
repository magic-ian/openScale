# Migration Guide: Android Code to Kotlin Multiplatform

This guide explains how to migrate existing Android code from `android_app/app/src/main/java/com/health/openscale/core/` to the shared Kotlin Multiplatform module.

## Overview

The goal is to move platform-independent business logic to the `shared` module so it can be reused by both Android and Linux Mobile platforms.

## Migration Strategy

### Phase 1: Core Data Models ✅ (Planned)
Move data classes and models that have no platform-specific dependencies.

**Source**: `android_app/app/src/main/java/com/health/openscale/core/data/`  
**Destination**: `shared/src/commonMain/kotlin/com/health/openscale/core/data/`

Examples:
- `User.kt`
- `Measurement.kt`
- `MeasurementType.kt`
- `MeasurementValue.kt`
- `UserGoals.kt`

### Phase 2: Use Cases ✅ (Planned)
Move business logic that operates on data models.

**Source**: `android_app/app/src/main/java/com/health/openscale/core/usecase/`  
**Destination**: `shared/src/commonMain/kotlin/com/health/openscale/core/usecase/`

Examples:
- `MeasurementCrudUseCases.kt`
- `UserUseCases.kt`
- `MeasurementQueryUseCases.kt`
- `MeasurementEvaluationUseCases.kt`
- `ImportExportUseCases.kt`

### Phase 3: Services ✅ (Planned)
Move calculation and transformation services.

**Source**: `android_app/app/src/main/java/com/health/openscale/core/service/`  
**Destination**: `shared/src/commonMain/kotlin/com/health/openscale/core/service/`

Examples:
- `MeasurementEnricher.kt`
- `TrendCalculator.kt`

### Phase 4: Database Interfaces ⏳ (Needs Platform Abstraction)
Move database repository interfaces; implementations stay platform-specific.

**Source**: `android_app/app/src/main/java/com/health/openscale/core/database/`  
**Destination**: 
- Interfaces → `shared/src/commonMain/kotlin/com/health/openscale/core/database/`
- Android implementation → `shared/src/androidMain/kotlin/com/health/openscale/core/database/`
- Linux implementation → `shared/src/linuxMain/kotlin/com/health/openscale/core/database/`

### Phase 5: Bluetooth Protocols ✅ (Planned)
Move scale protocol handlers (device-specific binary protocols).

**Source**: `android_app/app/src/main/java/com/health/openscale/core/bluetooth/libs/`  
**Destination**: `shared/src/commonMain/kotlin/com/health/openscale/core/bluetooth/libs/`

Note: Communication layer stays platform-specific (Android BLE vs BlueZ).

## Step-by-Step Migration Process

### 1. Identify Platform Dependencies

Before moving a file, check for Android-specific imports:
```kotlin
// ❌ Android-specific - needs abstraction
import android.content.Context
import android.bluetooth.BluetoothDevice
import androidx.room.*

// ✅ Platform-independent - safe to move
import kotlinx.coroutines.*
import kotlin.math.*
```

### 2. Create Abstractions for Platform Code

If a class has platform dependencies, use expect/actual:

**Example: Settings Storage**

```kotlin
// In shared/src/commonMain/kotlin/
expect class SettingsStorage {
    fun getString(key: String): String?
    fun putString(key: String, value: String)
}

// In shared/src/androidMain/kotlin/
actual class SettingsStorage(private val context: Context) {
    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
    
    actual fun getString(key: String): String? = prefs.getString(key, null)
    actual fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
}

// In shared/src/linuxMain/kotlin/
actual class SettingsStorage {
    private val configFile = File(System.getProperty("user.home"), ".openscale/settings")
    
    actual fun getString(key: String): String? {
        // Read from config file
    }
    actual fun putString(key: String, value: String) {
        // Write to config file
    }
}
```

### 3. Move the File

```bash
# Example: Moving a data class
git mv android_app/app/src/main/java/com/health/openscale/core/data/User.kt \
        shared/src/commonMain/kotlin/com/health/openscale/core/data/User.kt
```

### 4. Update Package Structure

The package structure should remain the same:
- Before: `com.health.openscale.core.data`
- After: `com.health.openscale.core.data`

### 5. Update Dependencies

**In shared/build.gradle.kts:**
```kotlin
sourceSets {
    val commonMain by getting {
        dependencies {
            // Add any new dependencies needed
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
        }
    }
}
```

### 6. Update Android App to Use Shared Module

**In android_app/app/build.gradle.kts:**
```kotlin
dependencies {
    implementation(project(":shared"))
    // ... other dependencies
}
```

Update imports in Android code:
```kotlin
// Same import path works!
import com.health.openscale.core.data.User
```

## Common Migration Patterns

### Pattern 1: Pure Data Classes
**No changes needed** - just move the file.

```kotlin
// This can move directly to commonMain
data class User(
    val id: Long,
    val name: String,
    val birthday: LocalDate
)
```

### Pattern 2: Classes with Platform Dependencies
**Extract interface, use expect/actual**.

```kotlin
// commonMain
expect class BluetoothManager {
    fun scan(): Flow<Device>
}

// androidMain  
actual class BluetoothManager {
    actual fun scan(): Flow<Device> {
        // Android BLE implementation
    }
}

// linuxMain
actual class BluetoothManager {
    actual fun scan(): Flow<Device> {
        // BlueZ D-Bus implementation
    }
}
```

### Pattern 3: Classes with Constructor Dependencies
**Use factory pattern or dependency injection**.

```kotlin
// commonMain
interface DatabaseRepository {
    suspend fun getUser(id: Long): User?
}

// androidMain (Room implementation)
class RoomDatabaseRepository(
    private val database: AppDatabase
) : DatabaseRepository {
    override suspend fun getUser(id: Long): User? = 
        database.userDao().getById(id)
}

// linuxMain (SQLite implementation)
class SqliteDatabaseRepository(
    private val connection: Connection
) : DatabaseRepository {
    override suspend fun getUser(id: Long): User? {
        // Direct SQLite implementation
    }
}
```

## Testing After Migration

### 1. Test Shared Module
```bash
./gradlew :shared:test
```

### 2. Test Android App
```bash
cd android_app && ./gradlew test
cd android_app && ./gradlew assembleDebug
```

### 3. Test Linux App
```bash
cd linux_app && ./gradlew test
cd linux_app && ./gradlew build
```

## Rollback Plan

If issues occur:
```bash
git revert <commit-hash>
```

Or restore the file:
```bash
git checkout HEAD~1 -- android_app/app/src/main/java/path/to/File.kt
```

## Migration Checklist

For each file you migrate:

- [ ] Identify and document all platform dependencies
- [ ] Create expect/actual abstractions if needed
- [ ] Move file to appropriate source set
- [ ] Update any hardcoded paths or platform-specific code
- [ ] Add necessary dependencies to shared module
- [ ] Update Android app to reference shared module
- [ ] Write or update tests in commonTest
- [ ] Verify Android build still works
- [ ] Verify Linux build compiles
- [ ] Update documentation if needed

## Getting Help

- See [ARCHITECTURE.md](./ARCHITECTURE.md) for architecture overview
- See [CONTRIBUTING.md](./CONTRIBUTING.md) for contribution guidelines
- Check [Kotlin Multiplatform docs](https://kotlinlang.org/docs/multiplatform.html)
- Open an issue for migration questions

## Future Considerations

### Kotlin/Native
Currently using JVM for Linux target. In the future, consider:
- Migrating to Kotlin/Native for better performance
- This would require replacing some JVM-specific libraries

### Database Migration
- Android: Room (JVM-based)
- Linux: SQLDelight (multiplatform) or direct SQLite
- Consider SQLDelight for full multiplatform database support

### UI Framework
- Keep UI completely separate per platform
- Android: Jetpack Compose
- Linux: GTK4/Libadwaita or Qt/QML
