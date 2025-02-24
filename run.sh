#!/bin/bash

# Exit script on first error
set -e

# Load environment variables from .env file
if [ -f .env ]; then
    export $(grep -v '^#' .env | xargs)
    echo "✅ Environment variables loaded from .env"
else
    echo "⚠️ .env file not found. Ensure you create one."
    exit 1
fi

# Clean and build the Spring Boot application
echo "🛠️ Building the Subscription Manager Application..."
if ./gradlew clean build; then
    echo "✅ Build successful!"
else
    echo "❌ Build failed! Check Gradle logs above."
    exit 1
fi

# Run Docker Compose
echo "🚀 Starting Docker Build & Containers..."
if docker-compose up --build; then
    echo "✅ Docker containers are running!"
else
    echo "❌ Docker build failed! Check logs above."
    exit 1
fi
