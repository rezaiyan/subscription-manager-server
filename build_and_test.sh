#!/bin/bash

# Exit on first error
set -e

echo "🚀 Starting Build & Test Process..."

# Check if .env file exists
if [ ! -f .env ]; then
    echo "⚠️ .env file not found! Ensure environment variables are set."
    exit 1
fi

# Load environment variables
# shellcheck disable=SC2046
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

# Run tests to verify functionality
echo "🛠️ Running Tests..."
if ./gradlew test; then
    echo "✅ All tests passed!"
else
    echo "❌ Tests failed! Check logs above."
    exit 1
fi

echo "🎉 Build & Test process completed successfully!"
