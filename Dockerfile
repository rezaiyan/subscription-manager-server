# Use official OpenJDK 17 runtime as a base image
FROM openjdk:17-jdk-slim AS build

# Set the working directory inside the container
WORKDIR /app

# Copy Gradle files and build dependencies
COPY gradlew gradlew.bat gradle/ ./
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src

# Make Gradle wrapper executable
RUN chmod +x gradlew

# Build the application inside Docker
RUN ./gradlew clean build

# Use a new container for running the app
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file from the previous step
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
