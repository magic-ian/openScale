// Top-level build file for openScale multiplatform project
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        // Android dependencies - uncomment when building Android app
        // classpath("com.android.tools.build:gradle:8.5.0")
        // classpath("com.google.dagger:hilt-android-gradle-plugin:2.57.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.3.0")
    }
}

plugins {
    kotlin("multiplatform") version "2.3.0" apply false
    kotlin("android") version "2.3.0" apply false
}
