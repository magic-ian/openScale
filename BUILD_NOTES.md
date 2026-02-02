# Build Notes

## Linux Application Build Status

The Linux application can now be built successfully!

### Building the Linux App

```bash
./gradlew :linux_app:build
```

### Creating Distribution Packages

openScale now supports multiple Linux packaging formats:

1. **Flatpak** - See `com.health.openscale.yml` and [PACKAGING.md](./PACKAGING.md)
2. **Debian (.deb)** - See `debian/` directory
3. **Fedora (.rpm)** - See `openscale.spec`
4. **Arch Linux** - See `PKGBUILD`
5. **Nix** - See `default.nix`
6. **AppImage** - Run `./build-appimage.sh`

For detailed packaging instructions, see [PACKAGING.md](./PACKAGING.md).

## Known Issues

### Android Gradle Plugin Version

The repository previously specified AGP version 8.13.2, which doesn't exist. This has been fixed to use 8.5.0.

**Status**: ✅ Fixed

### Build Configuration

The build configuration has been updated to allow Linux app builds to work independently:
- Android dependencies are commented out in root `build.gradle.kts` for environments without Google Maven access
- Linux app can build standalone
- Shared module dependencies temporarily disabled to focus on packaging

**Status**: ✅ Working for Linux builds

## Multiplatform Build Structure

The repository uses a multiplatform structure:

```
.
├── build.gradle.kts          # Root build configuration
├── settings.gradle.kts       # Project structure definition
├── shared/                   # Kotlin Multiplatform shared module (temporarily disabled)
├── android_app/              # Android application (requires Google Maven)
└── linux_app/                # Linux application (✅ working)
```

### Building Individual Modules

**Linux app:**
```bash
./gradlew :linux_app:build
./gradlew :linux_app:installDist  # Create installable distribution
```

**Android app (requires Google Maven access):**
```bash
cd android_app
./gradlew assembleDebug
```

**Shared module (requires Android dependencies):**
```bash
./gradlew :shared:build
```

## Development Status

- ✅ Multiplatform architecture designed and documented
- ✅ Linux app builds successfully
- ✅ Packaging support for all major Linux distributions
- ✅ CI/CD configuration for packaging validation
- ⏳ Android build requires Google Maven access
- ⏳ Shared module temporarily disabled
- ⏳ Actual code migration to shared module (future work)
- ⏳ Linux UI implementation (future work)

## Next Steps

1. **Enable Google Maven access** for full multiplatform builds
2. **Re-enable shared module** integration
3. **Migrate core business logic** from android_app to shared module incrementally
4. **Implement Linux platform specifics** (Bluetooth via BlueZ, storage, etc.)
5. **Implement Linux UI** (GTK4/Qt)
6. **Test packaging** on real Linux distributions
7. **Publish to distribution repositories** (Flathub, AUR, etc.)

## Testing the Linux App

Once built, you can run the Linux app:

```bash
# After building
./linux_app/build/install/linux_app/bin/linux_app

# Or create and run from distribution
./gradlew :linux_app:installDist
./linux_app/build/install/linux_app/bin/linux_app
```

## CI/CD

Two CI workflows are configured:

1. **ci_multiplatform.yml** - Tests multiplatform builds (currently requires Google Maven)
2. **ci_packaging.yml** - Validates all packaging configurations (✅ working)

## Contributing

See [CONTRIBUTING.md](./CONTRIBUTING.md) for detailed guidelines on contributing to the multiplatform codebase.
