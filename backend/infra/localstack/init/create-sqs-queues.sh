#!/bin/bash
set -e

echo "📨 Creating SQS queues..."

aws --endpoint-url=http://localhost:4566 sqs create-queue \
  --queue-name sale-activation-queue \
  --region us-east-1

echo "✅ SQS queues created"
