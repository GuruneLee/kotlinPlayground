plugins {
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
}

configurations.create("asciidoctorExt")

group = "com.gurunelee"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "mockito-core")
    }
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("com.ninja-squad:springmockk:4.0.2")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    "asciidoctorExt"("org.springframework.restdocs:spring-restdocs-asciidoctor")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val snippetsDir = file("build/generated-snippets")

tasks.test {
    outputs.dir(snippetsDir)
}

/**
 * asciidoctor 의 sourceDir, outputDir 의 기본값은 'src/docs/asciidoc', 'build/docs/asciidoc' 이다.
 * dir 변경을 위해선 setSourceDir, setOutputDir를 사용해야 한다.
 *
 * @see org.asciidoctor.gradle.jvm.AsciidoctorTask
 */
tasks.asciidoctor {
    dependsOn(tasks.test)
    configurations("asciidoctorExt")
}

tasks.bootJar {
    dependsOn(tasks.asciidoctor)
    from("${tasks.asciidoctor.get().outputDir}") {
        into("BOOT-INF/classes/static/docs")
    }
}

tasks.bootRun {
    dependsOn(tasks.asciidoctor)
    copy {
        from("${tasks.asciidoctor.get().outputDir}")
        into("src/main/resources/static/docs")
    }
}