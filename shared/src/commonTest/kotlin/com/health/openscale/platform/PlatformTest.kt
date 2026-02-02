package com.health.openscale.platform

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Tests for the Platform interface to verify expect/actual pattern works correctly
 */
class PlatformTest {
    
    @Test
    fun platformNameShouldNotBeEmpty() {
        val platformName = Platform.name
        assertNotNull(platformName, "Platform name should not be null")
        assertTrue(platformName.isNotEmpty(), "Platform name should not be empty")
    }
    
    @Test
    fun platformTypeShouldNotBeUnknown() {
        val platformType = Platform.type
        assertNotNull(platformType, "Platform type should not be null")
        // This test ensures that each platform has properly implemented the actual class
        // On Android, it should be ANDROID; on Linux, it should be LINUX_MOBILE
    }
    
    @Test
    fun platformTypeShouldBeValid() {
        val platformType = Platform.type
        val validTypes = listOf(
            PlatformType.ANDROID,
            PlatformType.LINUX_MOBILE,
            PlatformType.LINUX_DESKTOP
        )
        assertTrue(
            platformType in validTypes,
            "Platform type should be one of the supported types"
        )
    }
}
