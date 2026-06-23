plugins {
    kotlin("jvm") version "2.3.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(25)
}

application {
    mainClass = "org.tlarsen.adventofcode2025.day09.part2.MainKt"
}

tasks.test {
    useJUnitPlatform()
}