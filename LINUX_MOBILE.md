# Linux Mobile Support for openScale

## Summary

openScale now supports **Linux Mobile** platforms including **phosh** (GNOME-based) and **Plasma Mobile** (KDE-based) through a **Kotlin Multiplatform** architecture.

## Current Status

🎉 **Foundation Complete** - The multiplatform architecture is in place and ready for development.

### What's Ready
- ✅ Kotlin Multiplatform project structure
- ✅ Shared module for common business logic
- ✅ Platform abstraction patterns (expect/actual)
- ✅ Linux app module structure
- ✅ Comprehensive documentation
- ✅ CI/CD workflow for multiplatform builds
- ✅ Migration guide for moving existing code

### What's Next
- ⏳ Migrate existing business logic to shared module
- ⏳ Implement Linux platform-specific code:
  - BlueZ Bluetooth integration (via D-Bus)
  - SQLite storage (or SQLDelight)
  - GSettings/config file preferences
  - D-Bus notifications
  - systemd timer integration
- ⏳ Implement Linux UI:
  - GTK4 + Libadwaita for phosh
  - Qt/QML + Kirigami for Plasma Mobile
- ⏳ Package for distribution (Flatpak, AppImage)
- ⏳ Testing on real Linux Mobile devices

## Architecture

```
openScale/
├── shared/                    # Kotlin Multiplatform - business logic shared by all platforms
│   ├── src/commonMain/       # Platform-independent code
│   ├── src/androidMain/      # Android-specific implementations
│   └── src/linuxMain/        # Linux-specific implementations
│
├── android_app/              # Android application (Jetpack Compose)
│   └── app/
│       └── src/main/java/com/health/openscale/
│           └── ui/           # Android UI
│
└── linux_app/                # Linux Mobile application
    └── src/main/kotlin/com/health/openscale/
        └── ui/               # Linux UI (GTK/Qt - to be implemented)
```

## Key Design Decisions

### 1. Kotlin Multiplatform over Cross-Platform Frameworks
**Why?** Respects the existing Kotlin codebase and the work of oliexdev and contributors.

**Benefits:**
- Reuse existing business logic
- Native UI on each platform
- Type-safe, compile-time verified
- No runtime performance overhead

### 2. Platform-Specific UI
**Why?** Each mobile platform has distinct design languages and user expectations.

**Implementations:**
- **Android**: Jetpack Compose + Material 3 (existing)
- **phosh**: GTK4 + Libadwaita (GNOME HIG)
- **Plasma Mobile**: Qt/QML + Kirigami (KDE HIG)

### 3. Shared Business Logic
**What's shared:**
- Data models (User, Measurement, etc.)
- Use cases (all business operations)
- Calculations (BMI, body fat, trends)
- Database repository interfaces
- Bluetooth scale protocols
- Import/export logic

**What's platform-specific:**
- UI layer
- Bluetooth communication (Android BLE vs BlueZ)
- Storage implementation (Room vs SQLite)
- Notifications (Android vs D-Bus)
- Background tasks (WorkManager vs systemd)

## For Contributors

### Contributing to Linux Mobile Support

We welcome contributions! Here's how you can help:

1. **UI Implementation**
   - GTK4/Libadwaita for phosh
   - Qt/QML/Kirigami for Plasma Mobile

2. **Platform Features**
   - BlueZ Bluetooth integration
   - Storage implementation
   - Notification support
   - Background task scheduling

3. **Code Migration**
   - Help move existing code to shared module
   - See [MIGRATION_GUIDE.md](./MIGRATION_GUIDE.md)

4. **Testing**
   - Test on PinePhone, Librem 5, or other Linux Mobile devices
   - Report compatibility issues
   - Help with device-specific adaptations

5. **Documentation**
   - Improve user guides
   - Add platform-specific setup instructions
   - Document Linux Mobile best practices

### Quick Start for Contributors

1. **Read the docs:**
   - [ARCHITECTURE.md](./ARCHITECTURE.md) - Architecture overview
   - [CONTRIBUTING.md](./CONTRIBUTING.md) - Contribution guidelines
   - [MIGRATION_GUIDE.md](./MIGRATION_GUIDE.md) - Code migration guide
   - [BUILD_NOTES.md](./BUILD_NOTES.md) - Build information

2. **Set up development environment:**
   ```bash
   # Clone the repo
   git clone https://github.com/oliexdev/openScale.git
   cd openScale
   
   # Build shared module
   ./gradlew :shared:build
   
   # Build Linux app
   cd linux_app && ./gradlew build
   ```

3. **Start contributing:**
   - Pick an issue labeled `linux-mobile` or `multiplatform`
   - Or create new issues for Linux-specific features
   - Submit PRs following the contribution guidelines

## Distribution

Once the Linux Mobile implementation is complete, openScale will be available via:

### phosh (GNOME-based)
- **Flatpak** (recommended)
- **AppImage**
- **Mobian/Debian packages**

### Plasma Mobile (KDE-based)
- **Flatpak** (recommended)
- **AppImage**
- **postmarketOS packages**

## Compatibility

### Target Devices
- **PinePhone** (phosh/Plasma Mobile)
- **PinePhone Pro** (phosh/Plasma Mobile)
- **Librem 5** (phosh/PureOS)
- **OnePlus 6** (postmarketOS)
- Any Linux Mobile device running phosh or Plasma Mobile

### Requirements
- Linux Mobile environment (phosh or Plasma Mobile)
- Bluetooth support (BlueZ)
- SQLite (for database)
- D-Bus (for notifications and system integration)

## Credits

This Linux Mobile port is made possible by:
- **oliexdev** - Creator and maintainer of openScale
- **All openScale contributors** - For building an excellent Android app
- **The Linux Mobile community** - For making mobile Linux a reality

The multiplatform architecture respects and builds upon the excellent work already done, extending openScale's reach while maintaining code quality and the original vision.

## Resources

- **Main Repository**: https://github.com/oliexdev/openScale
- **Wiki**: https://github.com/oliexdev/openScale/wiki
- **Issues**: https://github.com/oliexdev/openScale/issues
- **Weblate (Translations)**: https://hosted.weblate.org/engage/openscale/

### Linux Mobile Resources
- **phosh**: https://puri.sm/posts/phosh/
- **Plasma Mobile**: https://plasma-mobile.org/
- **postmarketOS**: https://postmarketos.org/
- **Mobian**: https://mobian-project.org/

## License

openScale is licensed under GPL v3. See [LICENSE](./LICENSE) for details.

---

**Ready to contribute?** Check out the [issues](https://github.com/oliexdev/openScale/issues) or start with [CONTRIBUTING.md](./CONTRIBUTING.md)!
