# Build stage
FROM gradle:9.2.1-jdk21 AS build
WORKDIR /app

# Copy Gradle files first for better layer caching
COPY gradle/ gradle/
COPY gradlew settings.gradle.kts build.gradle.kts ./
COPY gradle/libs.versions.toml gradle/

# Download dependencies (this layer will be cached if dependencies don't change)
RUN gradle dependencies --no-daemon

# Copy source code
COPY src/ src/

# Build the application with incremental compilation
RUN gradle bootJar --no-daemon --build-cache

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring

# Create logs directory with proper permissions
RUN mkdir -p /app/logs && chown spring:spring /app/logs

# Copy the built JAR from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Switch to non-root user
USER spring:spring

# Expose application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
