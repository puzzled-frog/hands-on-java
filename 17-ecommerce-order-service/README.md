# E-commerce Order Service

## Description

The E-commerce Order Service manages the complete order lifecycle with inventory tracking, a state machine for order progression, and payment status management. Handle orders from creation through confirmation, shipping, and delivery, while managing stock levels and ensuring data consistency.

This challenge introduces you to state machines, complex business logic, and transactional workflows. You'll learn how to model state transitions with validation (orders can't skip states), manage inventory with soft reservations, and coordinate multiple entities in transactions. The service demonstrates how to build systems that enforce business rules and maintain data integrity across operations.

You'll practice designing service layers that encapsulate business logic, handling concurrent modifications safely, and implementing workflows that reflect real-world processes. This teaches you how to build reliable systems for critical business operations.

## Features

- **Product catalog**: Manage products with stock tracking
- **Order creation**: Multi-item orders with automatic inventory reservation
- **State machine**: PENDING → CONFIRMED → SHIPPED → DELIVERED flow
- **Cancellation support**: Cancel from PENDING or CONFIRMED states
- **Payment integration**: Track payment status independently
- **Inventory management**: Reserve stock on creation, release on cancellation
- **Concurrent safety**: Prevent over-selling with proper locking
- **Order queries**: Search by customer, status, and date range
- **Revenue tracking**: Calculate totals by time period

## How to Run

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/ecommerce-order-service.jar
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
# Create an order
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": "C123",
    "items": [
      {"productId": "P456", "quantity": 2},
      {"productId": "P789", "quantity": 1}
    ]
  }'

# Response:
{
  "id": 1,
  "status": "PENDING",
  "paymentStatus": "PENDING",
  "total": 149.99,
  "items": [...],
  "createdAt": "2024-01-15T10:30:00Z"
}

# Confirm payment
curl -X POST http://localhost:8080/api/orders/1/payment/confirm \
  -H "Content-Type: application/json"

# Order automatically moves to CONFIRMED status

# Ship order
curl -X POST http://localhost:8080/api/orders/1/ship \
  -H "Content-Type: application/json" \
  -d '{"trackingNumber": "1Z999AA10123456784"}'

# Cancel order
curl -X POST http://localhost:8080/api/orders/1/cancel \
  -H "Content-Type: application/json" \
  -d '{"reason": "Customer requested cancellation"}'

# Get order history
curl "http://localhost:8080/api/orders?customerId=C123&status=DELIVERED"
```

