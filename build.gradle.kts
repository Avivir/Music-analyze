plugins {
    kotlin("jvm") version "1.9.0" // Wersja Kotlin
    id("java") // Plugin do Java
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral() // Źródło zależności
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17)) // Używamy JDK 17
    }
}

dependencies {
    // JUnit 5 for testing
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // PostgreSQL JDBC Driver
    implementation("org.postgresql:postgresql:42.6.0")

    // Jackson dla JSON
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
}

tasks.test {
    useJUnitPlatform() // Używamy JUnit 5
}
