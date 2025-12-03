plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.0"
    kotlin("jvm") version "2.2.0"
}

group = "com.mechanicus.theme"
version = "1.0.1"

repositories {
    mavenCentral()
}

sourceSets {
    main {
        resources {
            srcDirs("src/main/resources")
        }
    }
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
    processResources {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from(projectDir) {
            include("LICENSE","README.md")
        }
        from("src/main/resources/META-INF") {
            include("pluginIcon.svg", "pluginIcon.png")
            into("META-INF")
        }
    }

    buildPlugin {
        archiveFileName.set("adeptus-mechanicus-theme.jar")
    }
}
