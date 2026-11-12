import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    kotlin("jvm") version "2.3.21" apply false
    kotlin("plugin.spring") version "2.3.21" apply false
    kotlin("plugin.jpa") version "2.3.21" apply false
    id("org.springframework.boot") version "4.1.2" apply false
    id("io.spring.dependency-management") version "1.1.7"
}

allprojects {
    group = "com.example"
    version = "0.0.1-SNAPSHOT"
    description = "demo"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    configure<JavaPluginExtension> {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    dependencies {
        "implementation"(platform("org.springframework.boot:spring-boot-dependencies:4.1.2"))
        "implementation"("org.jetbrains.kotlin:kotlin-reflect")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}