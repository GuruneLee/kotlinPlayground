/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * Learn more about Gradle by exploring our Samples at https://docs.gradle.org/8.13/samples
 */
plugins {
    kotlin("jvm") version "2.1.0"
}

group = "com.gurunelee.shared"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.projectreactor:reactor-core:3.6.4")

    testImplementation("io.projectreactor:reactor-test:3.6.4")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test:2.1.0")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            listOf("-Xjsr305=strict")
        )
    }
}
