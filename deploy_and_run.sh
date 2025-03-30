#!/bin/bash

set -e

echo "🚀 Deploying and Running Subscription Manager..."

if [ ! -f .env ]; then
    echo "⚠️  .env file not found! Please create one before deploying."
    exit 1
fi

echo "📦 Loading environment variables from .env..."
# shellcheck disable=SC2046
export $(grep -v '^#' .env | xargs)
echo "✅ Environment variables loaded."

echo "🧹 Stopping and cleaning up existing containers..."
docker compose down || true

echo "🚀 Building and starting containers..."
if docker compose up --build; then
    echo "✅ Application is running."
else
    echo "❌ Failed to deploy. Please check the logs."
    exit 1
fi

echo "📦 Running containers:"
docker ps

echo "🎉 Deployment completed successfully!"
