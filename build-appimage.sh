#!/bin/bash
# Build script for creating openScale AppImage

set -e

APP_NAME="openScale"
APP_VERSION="3.0.2"
ARCH="x86_64"

# Build the application
echo "Building openScale..."
./gradlew :linux_app:installDist

# Create AppDir structure
echo "Creating AppDir structure..."
mkdir -p AppDir/usr/bin
mkdir -p AppDir/usr/lib/openscale
mkdir -p AppDir/usr/share/applications
mkdir -p AppDir/usr/share/icons/hicolor/256x256/apps

# Copy application files
cp -r linux_app/build/install/linux_app/* AppDir/usr/lib/openscale/

# Create launcher script
cat > AppDir/usr/bin/openscale <<EOF
#!/bin/bash
APPDIR="\$(dirname "\$(dirname "\$(readlink -f "\$0")")")"
exec "\$APPDIR/usr/lib/openscale/bin/linux_app" "\$@"
EOF
chmod +x AppDir/usr/bin/openscale

# Create desktop file
cat > AppDir/usr/share/applications/openscale.desktop <<EOF
[Desktop Entry]
Type=Application
Name=openScale
Comment=Open-source weight and body metrics tracker
Exec=openscale
Icon=openscale
Categories=Utility;Health;
Terminal=false
EOF

# Create placeholder icon (in production, use actual icon)
# For now, create a simple SVG placeholder
cat > AppDir/usr/share/icons/hicolor/256x256/apps/openscale.svg <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<svg width="256" height="256" xmlns="http://www.w3.org/2000/svg">
  <rect width="256" height="256" fill="#4CAF50"/>
  <text x="128" y="128" font-size="48" text-anchor="middle" fill="white" dy=".3em">oS</text>
</svg>
EOF

# Create AppRun
cat > AppDir/AppRun <<EOF
#!/bin/bash
APPDIR="\$(dirname "\$(readlink -f "\$0")")"
export PATH="\$APPDIR/usr/bin:\$PATH"
exec "\$APPDIR/usr/bin/openscale" "\$@"
EOF
chmod +x AppDir/AppRun

# Download appimagetool if not present
if [ ! -f appimagetool-${ARCH}.AppImage ]; then
    echo "Downloading appimagetool..."
    wget "https://github.com/AppImage/AppImageKit/releases/download/continuous/appimagetool-${ARCH}.AppImage"
    chmod +x appimagetool-${ARCH}.AppImage
fi

# Create AppImage
echo "Creating AppImage..."
ARCH=${ARCH} ./appimagetool-${ARCH}.AppImage AppDir ${APP_NAME}-${APP_VERSION}-${ARCH}.AppImage

echo "AppImage created: ${APP_NAME}-${APP_VERSION}-${ARCH}.AppImage"
