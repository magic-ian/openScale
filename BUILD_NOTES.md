# Build Notes

## Known Issues

### Android Gradle Plugin Version

The current configuration specifies AGP version 8.13.2 in `android_app/gradle/libs.versions.toml`, which does not exist as of February 2026. This appears to be a pre-existing configuration issue in the repository.

**Possible solutions:**
1. Update to AGP 8.7.3 (latest stable as of Feb 2026)
2. Update to AGP 8.9.0 if available
3. Wait for AGP 8.13.2 to be released if this is an intentional future version

**To fix immediately**, update `android_app/gradle/libs.versions.toml`:
```toml
[versions]
agp = "8.7.3"  # or latest stable version
```

And update the root `build.gradle.kts`:
```kotlin
classpath("com.android.tools.build:gradle:8.7.3")
id("com.android.library") version "8.7.3" apply false
```

This issue exists in the base repository and is not introduced by the Linux Mobile multiplatform changes.

## Multiplatform Build Structure

The repository now uses a multiplatform structure:

```
.
├── build.gradle.kts          # Root build configuration
├── settings.gradle.kts       # Project structure definition
├── shared/                   # Kotlin Multiplatform shared module
├── android_app/              # Android application
└── linux_app/                # Linux Mobile application (in development)
```

### Building Individual Modules

**Android app (once AGP version is fixed):**
```bash
cd android_app
./gradlew assembleDebug
```

**Linux app:**
```bash
cd linux_app  
./gradlew build
```

**Shared module:**
```bash
./gradlew :shared:build
```

## Development Status

- ✅ Multiplatform architecture designed and documented
- ✅ Shared module structure created
- ✅ Platform abstraction pattern (expect/actual) demonstrated
- ✅ Documentation complete (ARCHITECTURE.md, CONTRIBUTING.md, READMEs)
- ⏳ Build verification pending AGP version fix
- ⏳ CI/CD configuration for Linux builds (future work)
- ⏳ Actual code migration to shared module (future work)
- ⏳ Linux UI implementation (future work)

## Next Steps

1. **Fix AGP version** to a valid release
2. **Verify Android build** works after AGP fix
3. **Migrate core business logic** from android_app to shared module incrementally
4. **Implement Linux platform specifics** (Bluetooth via BlueZ, storage, etc.)
5. **Implement Linux UI** (GTK4/Qt)
6. **Add CI/CD** for Linux builds
7. **Package for distribution** (Flatpak/AppImage)

## Testing the Multiplatform Structure

Once AGP version is fixed, you can verify the multiplatform setup:

```bash
# Test that shared module compiles for both platforms
./gradlew :shared:build

# Test platform abstraction
./gradlew :shared:test
```

## Contributing

See [CONTRIBUTING.md](./CONTRIBUTING.md) for detailed guidelines on contributing to the multiplatform codebase.
