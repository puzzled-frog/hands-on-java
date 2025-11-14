# Real-time Notification System

## Description

The Notification System sends messages through multiple channels (email, SMS, push notifications) using message queues for asynchronous processing. Implement notification templates, user preferences, retry logic, and delivery tracking to build a reliable communication platform.

This challenge introduces you to event-driven architecture and message queues (RabbitMQ or Kafka). You'll learn how to decouple services using async messaging, scale notification processing independently, and handle delivery failures gracefully with retry mechanisms. The system demonstrates how modern applications handle high-volume notifications without blocking main application flows.

You'll practice designing systems for scalability, implementing at-least-once delivery semantics, and respecting user preferences. This teaches you how to build reliable background processing systems that are essential for production applications.

## Features

- **Multi-channel delivery**: Email, SMS, and push notifications
- **Message queue integration**: Async processing with RabbitMQ/Kafka
- **Template system**: Variable substitution in notification templates
- **User preferences**: Opt-in/opt-out per notification type and channel
- **Delivery tracking**: Monitor status and delivery timestamps
- **Retry logic**: Exponential backoff for failed deliveries
- **Dead letter queue**: Handle permanently failed notifications
- **Quiet hours**: Respect user-defined no-disturb times
- **Rate limiting**: Prevent notification spam

## How to Run

Ensure RabbitMQ or Kafka is running, then configure in `application.properties`:
```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
notification.email.provider=smtp
notification.sms.provider=twilio
```

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/notification-system.jar
```

API will be available at: `http://localhost:8080`

## How to Test

Run all tests:

```bash
./gradlew test
```

View test report:

```bash
open build/reports/tests/test/index.html
```

## Usage Example

```bash
# Send a notification
curl -X POST http://localhost:8080/api/notifications \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "user123",
    "type": "ORDER_UPDATE",
    "channels": ["EMAIL", "PUSH"],
    "title": "Order Shipped",
    "message": "Your order {{orderNumber}} has been shipped!",
    "data": {
      "orderNumber": "ORD-12345",
      "trackingUrl": "https://..."
    }
  }'

# Response:
{
  "id": "notif-789",
  "status": "PENDING",
  "channels": {
    "EMAIL": "QUEUED",
    "PUSH": "QUEUED"
  }
}

# Check notification status
curl http://localhost:8080/api/notifications/notif-789

# Response:
{
  "id": "notif-789",
  "status": "SENT",
  "channels": {
    "EMAIL": {"status": "DELIVERED", "deliveredAt": "2024-01-15T10:31:05Z"},
    "PUSH": {"status": "DELIVERED", "deliveredAt": "2024-01-15T10:31:02Z"}
  }
}

# Update user preferences
curl -X PUT http://localhost:8080/api/notifications/preferences \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "user123",
    "preferences": {
      "MARKETING": {"email": false, "sms": false, "push": true},
      "ORDER_UPDATE": {"email": true, "sms": true, "push": true}
    },
    "quietHours": {
      "start": "22:00",
      "end": "08:00"
    }
  }'

# Get user notification history
curl http://localhost:8080/api/notifications/user/user123?page=0&size=20
```

