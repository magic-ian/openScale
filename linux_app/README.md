# openScale for Linux Mobile

This directory contains the Linux Mobile implementation of openScale for **phosh** (GNOME-based) and **Plasma Mobile** (KDE-based) environments.

## Status

🚧 **In Development** - The Linux Mobile port is currently under active development.

## Architecture

The Linux Mobile app shares the core business logic with the Android app through the `shared` Kotlin Multiplatform module. Only the UI layer and platform-specific integrations are implemented separately.

### Shared Components (from `../shared`)
- All business logic (use cases, services)
- Data models and database interfaces
- Bluetooth scale protocols
- Import/export functionality
- Calculation and evaluation logic

### Linux-Specific Implementations
- **UI Framework**: 
  - GTK4 + Libadwaita for phosh (GNOME)
  - Qt/QML + Kirigami for Plasma Mobile
- **Bluetooth**: BlueZ via D-Bus
- **Storage**: SQLite + GSettings/config files
- **Notifications**: D-Bus notification service
- **Background Tasks**: systemd timers

## Building

### Prerequisites
- Java 21 or higher
- Gradle 8.x
- GTK4 development libraries (for GTK version)
- Qt development libraries (for Qt version)

### Build Commands

```bash
# Build the Linux app
cd linux_app
./gradlew build

# Run the app
./gradlew run

# Create distribution package
./gradlew packageDistribution  # Creates Flatpak or AppImage
```

## Distribution

The Linux Mobile version will be distributed via:
- **Flatpak** (recommended for both phosh and Plasma Mobile)
- **AppImage** (portable alternative)
- Distribution-specific packages (Debian, RPM, etc.)

## Development Status

- [x] Multiplatform architecture design
- [x] Shared module structure
- [ ] Linux platform interfaces
- [ ] GTK4 UI implementation
- [ ] Qt/QML UI implementation
- [ ] BlueZ Bluetooth integration
- [ ] Storage implementation
- [ ] Notification support
- [ ] Packaging (Flatpak/AppImage)
- [ ] Testing on real devices

## Contributing

Contributions to the Linux Mobile port are highly welcome! Areas where help is needed:
- GTK4/Libadwaita UI implementation
- Qt/QML/Kirigami UI implementation
- BlueZ Bluetooth integration
- Platform-specific features
- Testing on phosh and Plasma Mobile devices

Please see the main [ARCHITECTURE.md](../ARCHITECTURE.md) for details on the multiplatform structure.

## Credits

This Linux Mobile port builds upon the excellent work by **oliexdev** and all contributors to the original Android application. We aim to maintain compatibility and share as much code as possible while providing a native Linux Mobile experience.

## License

openScale is licensed under GPL v3. See [LICENSE](../LICENSE) for details.
