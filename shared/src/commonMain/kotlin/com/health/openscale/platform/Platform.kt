package com.health.openscale.platform

/**
 * Platform-specific interface for getting platform information.
 * This demonstrates the expect/actual pattern used throughout the shared module.
 */
expect object Platform {
    /**
     * Returns the name of the current platform (e.g., "Android", "Linux")
     */
    val name: String
    
    /**
     * Returns the platform type identifier
     */
    val type: PlatformType
}

/**
 * Enum representing the different platform types supported by openScale
 */
enum class PlatformType {
    ANDROID,
    LINUX_MOBILE,
    LINUX_DESKTOP,
    UNKNOWN
}
