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

        // Базовый тестовый стартер (JUnit 5, AssertJ, Mockito)
        "testImplementation"("org.springframework.boot:spring-boot-starter-test")

        // Для @WebMvcTest и @AutoConfigureMockMvc (Spring Boot 4)
        "testImplementation"("org.springframework.boot:spring-boot-starter-webmvc-test")
        "testImplementation"("io.mockk:mockk:1.13.17")

        // Для @DataJpaTest (Spring Boot 4)
        "testImplementation"("org.springframework.boot:spring-boot-starter-data-jpa-test")

        // Testcontainers 2.x для Spring Boot 4
        "testImplementation"("org.springframework.boot:spring-boot-testcontainers")
        "testImplementation"("org.testcontainers:testcontainers-postgresql")
        "testImplementation"("org.testcontainers:testcontainers-junit-jupiter")
        "testRuntimeOnly"("org.postgresql:postgresql")

        // Gradle 9 требует launcher явно
        "testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}