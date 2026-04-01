plugins {
    kotlin("jvm") version "2.3.10"
}

group = "org.tlarsen.adventofcode2025.day07.part2"
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

tasks.test {
    useJUnitPlatform()
}