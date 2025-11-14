# API Gateway & Service Orchestration

## Description

The API Gateway aggregates data from multiple microservices (user, product, order services) and provides unified endpoints to clients. Implement circuit breakers for resilience, handle timeouts gracefully, and provide fallback responses when services are unavailable. Orchestrate calls efficiently with parallel execution where possible.

This challenge introduces you to microservices patterns, service orchestration, and resilience. You'll learn how to use Resilience4j for circuit breakers, combine data from multiple services in single responses, and handle partial failures without cascading them. The gateway demonstrates how to build robust systems that remain operational even when dependencies fail.

You'll practice designing APIs that shield clients from backend complexity, implementing timeout and retry strategies, and monitoring service health. This is essential for building reliable distributed systems.

## Features

- **Service aggregation**: Combine data from multiple backend services
- **Circuit breakers**: Per-service circuit breakers with Resilience4j
- **Parallel execution**: Fetch from multiple services simultaneously
- **Timeout management**: Configurable timeouts per service
- **Fallback strategies**: Return cached or default data when services fail
- **Partial responses**: Return available data with indicators for missing parts
- **Retry logic**: Automatic retries with exponential backoff
- **Health checks**: Monitor backend service availability
- **Request routing**: Forward requests to appropriate services

## How to Run

Configure backend service URLs in `application.properties`:
```properties
services.user.url=http://localhost:8081
services.product.url=http://localhost:8082
services.order.url=http://localhost:8083
resilience4j.circuitbreaker.instances.user.failure-rate-threshold=50
resilience4j.circuitbreaker.instances.user.wait-duration-in-open-state=10000
```

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/api-gateway.jar
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
# Get user profile with order history (aggregated)
curl http://localhost:8080/api/users/123/profile

# Response (success - all services available):
{
  "user": {
    "id": 123,
    "name": "John Doe",
    "email": "john@example.com"
  },
  "orders": [
    {"id": 456, "total": 99.99, "status": "DELIVERED"},
    {"id": 457, "total": 149.99, "status": "SHIPPED"}
  ],
  "metadata": {
    "userService": "OK",
    "orderService": "OK"
  }
}

# Response (partial failure - order service down):
{
  "user": {
    "id": 123,
    "name": "John Doe",
    "email": "john@example.com"
  },
  "orders": null,
  "metadata": {
    "userService": "OK",
    "orderService": "CIRCUIT_OPEN",
    "fallback": true
  }
}

# Get product with related products
curl http://localhost:8080/api/products/789/details

# Check gateway health
curl http://localhost:8080/actuator/health

# Response:
{
  "status": "UP",
  "components": {
    "userService": {"status": "UP"},
    "productService": {"status": "UP"},
    "orderService": {"status": "CIRCUIT_OPEN"}
  }
}
```

