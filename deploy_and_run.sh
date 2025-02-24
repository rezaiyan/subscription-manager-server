#!/bin/bash

# Exit on first error
set -e

echo "🚀 Deploying and Running Subscription Manager..."

# Check if .env file exists
if [ ! -f .env ]; then
    echo "⚠️ .env file not found! Ensure environment variables are set."
    exit 1
fi

# Load environment variables
# shellcheck disable=SC2046
export $(grep -v '^#' .env | xargs)
echo "✅ Environment variables loaded from .env"

# Pull the latest Docker image (if using Docker Hub)
echo "🔄 Pulling the latest Docker image..."
docker pull mydockerhub/myapp:latest

# Stop existing containers (if running)
echo "🛑 Stopping existing containers..."
docker compose down || true

# Build & Run new containers
echo "🚀 Starting Docker Build & Containers..."
if docker compose up --build -d; then
    echo "✅ Application is running!"
else
    echo "❌ Deployment failed! Check logs above."
    exit 1
fi

# Show running containers
docker ps

echo "🎉 Deployment completed successfully!"
