plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "2.3.0"
    id("com.android.library")
}

group = "com.health.openscale"
version = "3.0.2"

kotlin {
    // Android target
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "21"
            }
        }
    }
    
    // Linux target (JVM-based for now, can be native in future)
    jvm("linux") {
        compilations.all {
            kotlinOptions {
                jvmTarget = "21"
            }
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
                
                // DateTime
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")
                
                // Serialization
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
            }
        }
        
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        
        val androidMain by getting {
            dependencies {
                // Android-specific dependencies will be added here
            }
        }
        
        val linuxMain by getting {
            dependencies {
                // Linux-specific dependencies will be added here
            }
        }
    }
}

android {
    namespace = "com.health.openscale.shared"
    compileSdk = 36
    
    defaultConfig {
        minSdk = 31
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}
