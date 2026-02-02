package com.health.openscale.platform

/**
 * Linux implementation of the Platform interface
 */
actual object Platform {
    actual val name: String = "Linux Mobile"
    actual val type: PlatformType = PlatformType.LINUX_MOBILE
}
