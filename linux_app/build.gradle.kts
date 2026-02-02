plugins {
    kotlin("jvm") version "2.3.0"
    application
}

group = "com.health.openscale"
version = "3.0.2"

repositories {
    mavenCentral()
}

dependencies {
    // Reference to shared module
    implementation(project(":shared"))
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    
    // Add UI framework dependencies here
    // For GTK: implementation("org.gnome:gtk4:...")
    // For Qt: implementation("org.kde:plasma-framework:...")
    
    // Testing
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

application {
    // Main class will be defined once UI is implemented
    mainClass.set("com.health.openscale.MainKt")
}

tasks.test {
    useJUnitPlatform()
}

// Task to create distributable package
tasks.register<Zip>("packageDistribution") {
    archiveBaseName.set("openscale-linux")
    archiveVersion.set(version.toString())
    
    from(tasks.distZip)
    
    doLast {
        println("Linux distribution package created")
        println("Note: Flatpak and AppImage packaging will be added in future updates")
    }
}
