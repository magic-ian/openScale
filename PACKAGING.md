# openScale Linux Packaging

This directory contains packaging configurations for distributing openScale on various Linux distributions and formats.

## Supported Package Formats

### 1. Flatpak
**File**: `com.health.openscale.yml`

Build with:
```bash
flatpak-builder --force-clean build-dir com.health.openscale.yml
```

Install locally:
```bash
flatpak-builder --user --install --force-clean build-dir com.health.openscale.yml
```

Run:
```bash
flatpak run com.health.openscale
```

### 2. Debian (.deb)
**Directory**: `debian/`

Build with:
```bash
dpkg-buildpackage -us -uc -b
```

Install:
```bash
sudo dpkg -i ../openscale_3.0.2-1_all.deb
```

### 3. Fedora (.rpm)
**File**: `openscale.spec`

Build with:
```bash
rpmbuild -ba openscale.spec
```

Install:
```bash
sudo dnf install rpmbuild/RPMS/noarch/openscale-3.0.2-1.noarch.rpm
```

### 4. Arch Linux
**File**: `PKGBUILD`

Build with:
```bash
makepkg -si
```

This will build and install the package.

### 5. Nix
**File**: `default.nix`

Build with:
```bash
nix-build
```

Install to profile:
```bash
nix-env -i -f default.nix
```

Run:
```bash
openscale
```

### 6. AppImage
**File**: `build-appimage.sh`

Build with:
```bash
./build-appimage.sh
```

Run:
```bash
chmod +x openScale-3.0.2-x86_64.AppImage
./openScale-3.0.2-x86_64.AppImage
```

## Requirements

All packages require:
- Java Runtime Environment 21 or later
- BlueZ (for Bluetooth scale support)

Build requirements:
- Java Development Kit 21 or later
- Gradle (included via wrapper)

## Distribution-Specific Notes

### Debian/Ubuntu
The package is architecture-independent (`all`) since it's a Java application.

### Fedora/RHEL
Uses standard RPM packaging conventions. Java package names may differ between RHEL versions.

### Arch Linux
Uses standard PKGBUILD format. The package pulls from the GitHub repository.

### NixOS
The Nix expression uses `fetchFromGitHub` and requires updating the hash when the version changes.

### Flatpak
Provides the most isolated and consistent runtime environment. Recommended for general Linux desktop use.

### AppImage
Provides a portable, self-contained executable that works on most Linux distributions without installation.

## Testing Packages

Before distributing, test each package format:

1. **Installation**: Verify the package installs without errors
2. **Execution**: Run the application and verify it starts
3. **Bluetooth**: Test Bluetooth connectivity (if hardware available)
4. **Uninstallation**: Verify clean removal

## Contributing

When adding new packaging formats or updating existing ones:

1. Test the package on the target distribution
2. Update this README with build and installation instructions
3. Ensure all dependencies are correctly specified
4. Submit a pull request with your changes

## License

All packaging files are licensed under GPL-3.0+, consistent with openScale's license.
