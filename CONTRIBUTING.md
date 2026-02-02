# Contributing to openScale

Thank you for your interest in contributing to openScale! This document provides guidelines for contributing to the multiplatform version of openScale.

## Table of Contents
- [Architecture Overview](#architecture-overview)
- [Development Setup](#development-setup)
- [Making Changes](#making-changes)
- [Code Structure](#code-structure)
- [Testing](#testing)
- [Submitting Pull Requests](#submitting-pull-requests)
- [Platform-Specific Guidelines](#platform-specific-guidelines)

## Architecture Overview

openScale uses **Kotlin Multiplatform** to share business logic across Android and Linux Mobile (phosh & Plasma Mobile) while keeping platform-specific UI implementations separate.

```
openScale/
├── shared/              # Kotlin Multiplatform - shared business logic
│   ├── commonMain/     # Platform-independent code
│   ├── androidMain/    # Android-specific implementations
│   └── linuxMain/      # Linux-specific implementations
├── android_app/        # Android UI (Jetpack Compose)
└── linux_app/          # Linux UI (GTK/Qt)
```

See [ARCHITECTURE.md](./ARCHITECTURE.md) for detailed architecture information.

## Development Setup

### Prerequisites

**For all platforms:**
- JDK 21 or higher
- Gradle 8.x (included via wrapper)
- Git

**For Android development:**
- Android Studio Ladybug or newer
- Android SDK API 36

**For Linux development:**
- IntelliJ IDEA (or any Kotlin-capable IDE)
- GTK4 development libraries (for phosh UI)
- Qt development libraries (for Plasma Mobile UI)

### Setting Up Your Development Environment

1. **Clone the repository:**
   ```bash
   git clone https://github.com/oliexdev/openScale.git
   cd openScale
   ```

2. **Build the project:**
   ```bash
   ./gradlew build
   ```

3. **For Android development:**
   ```bash
   cd android_app
   ./gradlew assembleDebug
   ```

4. **For Linux development:**
   ```bash
   cd linux_app
   ./gradlew build
   ```

## Making Changes

### Where to Add Code

1. **Business Logic (shared across all platforms):**
   - Location: `shared/src/commonMain/`
   - Examples: Use cases, data models, calculations, scale protocols
   - Rule: Must be platform-independent (no Android/Linux-specific APIs)

2. **Platform-Specific Implementations:**
   - Android: `shared/src/androidMain/` + `android_app/`
   - Linux: `shared/src/linuxMain/` + `linux_app/`
   - Use `expect`/`actual` pattern for platform abstractions

3. **UI Code:**
   - Android: `android_app/app/src/main/java/com/health/openscale/ui/`
   - Linux: `linux_app/src/main/kotlin/com/health/openscale/ui/`

### Coding Conventions

- **Language:** Kotlin for all code
- **Style:** Follow [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
- **Formatting:** Use the project's `.editorconfig` (if present)
- **Comments:** 
  - Use KDoc for public APIs
  - Explain "why" not "what" in comments
  - Keep comments up-to-date with code changes

### Common Scenarios

#### Adding a New Feature

1. **Implement shared logic first:**
   ```kotlin
   // In shared/src/commonMain/kotlin/com/health/openscale/core/usecase/
   class MyNewUseCase {
       fun doSomething(): Result {
           // Platform-independent implementation
       }
   }
   ```

2. **Add platform-specific code if needed:**
   ```kotlin
   // In shared/src/commonMain/kotlin/
   expect class PlatformHelper {
       fun platformSpecificOperation(): String
   }
   
   // In shared/src/androidMain/kotlin/
   actual class PlatformHelper {
       actual fun platformSpecificOperation(): String = "Android implementation"
   }
   
   // In shared/src/linuxMain/kotlin/
   actual class PlatformHelper {
       actual fun platformSpecificOperation(): String = "Linux implementation"
   }
   ```

3. **Implement UI in both platforms:**
   - Android: Add Compose UI
   - Linux: Add GTK/Qt UI

#### Adding Support for a New Bluetooth Scale

1. Implement the protocol handler in `shared/src/commonMain/`
2. The Bluetooth communication layer is platform-specific:
   - Android: Uses Android BLE APIs
   - Linux: Uses BlueZ via D-Bus

See [How to support a new scale](https://github.com/oliexdev/openScale/wiki/How-to-support-a-new-scale) for details.

## Code Structure

### Shared Module Structure

```
shared/src/
├── commonMain/kotlin/com/health/openscale/
│   ├── core/
│   │   ├── data/          # Data models (User, Measurement, etc.)
│   │   ├── database/      # Database interfaces
│   │   ├── usecase/       # Business logic
│   │   ├── service/       # Services and calculators
│   │   └── bluetooth/     # Scale protocol handlers
│   └── platform/          # Platform interface definitions (expect)
├── androidMain/kotlin/    # Android implementations (actual)
└── linuxMain/kotlin/      # Linux implementations (actual)
```

### Dependency Injection

- **Android:** Uses Hilt
- **Linux:** Will use Koin or manual DI
- **Shared:** Use constructor injection and factory patterns

## Testing

### Running Tests

```bash
# All tests
./gradlew test

# Shared module tests
./gradlew :shared:test

# Android tests
cd android_app && ./gradlew test

# Linux tests
cd linux_app && ./gradlew test
```

### Writing Tests

- **Unit tests:** Place in appropriate `test/` directory
- **Platform tests:** Use `androidTest/` or `linuxTest/`
- **Shared tests:** Use `commonTest/` for platform-independent tests

Example:
```kotlin
// In shared/src/commonTest/kotlin/
class MyUseCaseTest {
    @Test
    fun `test business logic`() {
        // Test shared logic
    }
}
```

## Submitting Pull Requests

1. **Fork the repository**

2. **Create a feature branch:**
   ```bash
   git checkout -b feature/my-new-feature
   ```

3. **Make your changes:**
   - Follow the coding conventions
   - Add tests for new functionality
   - Update documentation if needed

4. **Test your changes:**
   ```bash
   ./gradlew test
   ./gradlew build
   ```

5. **Commit with clear messages:**
   ```bash
   git commit -m "Add feature: description of what you added"
   ```

6. **Push to your fork:**
   ```bash
   git push origin feature/my-new-feature
   ```

7. **Create a Pull Request:**
   - Provide a clear description of your changes
   - Reference any related issues
   - Explain how you tested your changes

## Platform-Specific Guidelines

### Android Development

- Use Jetpack Compose for all UI
- Follow Material 3 design guidelines
- Test on API 31+ devices
- Ensure backward compatibility

### Linux Development

- **For phosh (GNOME):**
  - Use GTK4 + Libadwaita
  - Follow GNOME HIG (Human Interface Guidelines)
  
- **For Plasma Mobile (KDE):**
  - Use Qt/QML + Kirigami
  - Follow KDE HIG

- Test on actual Linux Mobile devices when possible
- Consider both phone and tablet form factors

### Bluetooth Scale Support

When adding support for a new scale:
1. Document the scale's protocol
2. Implement the protocol handler in `shared/`
3. Test on both Android and Linux (if possible)
4. Update the [supported scales wiki](https://github.com/oliexdev/openScale/wiki/Supported-scales-in-openScale)

## Code Review Process

All contributions go through code review:
- Automated checks (CI/CD) must pass
- Code must follow project conventions
- Tests must be included for new features
- Documentation must be updated
- At least one maintainer approval required

## Questions or Need Help?

- Check the [FAQ](https://github.com/oliexdev/openScale/wiki/Frequently-Asked-Questions-(FAQ))
- Search [existing issues](https://github.com/oliexdev/openScale/issues)
- Create a [new issue](https://github.com/oliexdev/openScale/issues/new)
- Join the discussion on relevant issues

## Credits

Thank you to **oliexdev** and all contributors who have built openScale into what it is today. This multiplatform version builds upon their excellent work while extending support to Linux Mobile platforms.

## License

By contributing to openScale, you agree that your contributions will be licensed under the GPL v3 license. See [LICENSE](./LICENSE) for details.
