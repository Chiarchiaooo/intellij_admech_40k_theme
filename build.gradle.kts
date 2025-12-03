plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.0"
    kotlin("jvm") version "2.0.21"
}

group = "com.mechanicus.theme"
version = "1.0.1"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

intellij {
    version.set("2025.2") // Target IntelliJ version
    type.set("IC") // Community edition
}

tasks {
    buildPlugin {
        archiveFileName.set("adeptus-mechanicus-theme.jar")
    }
}
