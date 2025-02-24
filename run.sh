#!/bin/bash

# Exit script on first error
set -e

# Check if .env exists, otherwise exit
if [ ! -f .env ]; then
    echo "⚠️ .env file not found! Ensure environment variables are set."
    exit 1
fi

# Load environment variables
export $(grep -v '^#' .env | xargs)
echo "✅ Environment variables loaded from .env"

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
