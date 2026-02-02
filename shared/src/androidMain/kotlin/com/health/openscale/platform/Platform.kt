package com.health.openscale.platform

/**
 * Android implementation of the Platform interface
 */
actual object Platform {
    actual val name: String = "Android"
    actual val type: PlatformType = PlatformType.ANDROID
}
