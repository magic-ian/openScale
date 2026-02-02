pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "openScale-multiplatform"

// Include the shared Kotlin Multiplatform module (commented out due to Android dependencies)
// include(":shared")

// Include the Android app (commented out due to network restrictions)
// include(":android_app")
// include(":android_app:app")

// Include the Linux app
include(":linux_app")
