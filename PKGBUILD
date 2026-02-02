# Maintainer: openScale Team <maintainer@openscale.org>
pkgname=openscale
pkgver=3.0.2
pkgrel=1
pkgdesc="Open-source weight and body metrics tracker with Bluetooth scale support"
arch=('any')
url="https://github.com/oliexdev/openScale"
license=('GPL3')
depends=('java-runtime>=21' 'bluez')
makedepends=('java-environment>=21' 'gradle')
source=("${pkgname}-${pkgver}.tar.gz::https://github.com/oliexdev/openScale/archive/refs/tags/v${pkgver}.tar.gz")
# TODO: Update checksum for each release using:
# makepkg -g >> PKGBUILD
# Then replace the line below with the generated checksum
sha256sums=('SKIP')

build() {
    cd "${srcdir}/${pkgname}-${pkgver}"
    ./gradlew :linux_app:installDist
}

package() {
    cd "${srcdir}/${pkgname}-${pkgver}"
    
    # Install application
    install -dm755 "${pkgdir}/usr/lib/openscale"
    cp -r linux_app/build/install/linux_app/* "${pkgdir}/usr/lib/openscale/"
    
    # Create symlink to binary
    install -dm755 "${pkgdir}/usr/bin"
    ln -s /usr/lib/openscale/bin/linux_app "${pkgdir}/usr/bin/openscale"
    
    # Install license
    install -Dm644 LICENSE "${pkgdir}/usr/share/licenses/${pkgname}/LICENSE"
    
    # Install documentation
    install -Dm644 README.md "${pkgdir}/usr/share/doc/${pkgname}/README.md"
}
