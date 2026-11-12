
# ==========================================
# Stage 1: Build
# ==========================================
FROM gradle:9.8.2-jdk21 AS builder

WORKDIR /workspace

# Gradle Wrapper
COPY gradlew ./
COPY gradle/ gradle/

# Gradle configuration
COPY settings.gradle.kts build.gradle.kts ./

# Project sources
COPY src/ src/

RUN chmod +x gradlew && \
    ./gradlew clean bootJar --no-daemon -x test


# ==========================================
# Stage 2: Extract Spring Boot layers
# ==========================================
FROM eclipse-temurin:21-jre-jammy AS extractor

WORKDIR /app

COPY --from=builder /workspace/build/libs/*.jar application.jar

RUN java -Djarmode=tools -jar application.jar \
    extract --layers --destination extracted


# ==========================================
# Stage 3: Runtime
# ==========================================
FROM eclipse-temurin:21-jre-jammy AS runtime

WORKDIR /application

# Create non-root demo
RUN groupadd --system spring && \
    useradd --system --gid spring spring

# Dependencies
COPY --from=extractor --chown=spring:spring \
    /app/extracted/dependencies/ ./

# Spring Boot loader
COPY --from=extractor --chown=spring:spring \
    /app/extracted/spring-boot-loader/ ./

# Snapshot dependencies
COPY --from=extractor --chown=spring:spring \
    /app/extracted/snapshot-dependencies/ ./

# Application classes and resources
COPY --from=extractor --chown=spring:spring \
    /app/extracted/application/ ./

USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "application.jar"]
