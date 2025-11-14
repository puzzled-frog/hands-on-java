# Observability Platform

## Description

The Observability Platform instruments an application with structured logging, metrics collection, distributed tracing, and health checks. Integrate with Prometheus for metrics, Zipkin for tracing, and use SLF4J with Logback for structured logs. Build dashboards that give complete visibility into application behavior and performance.

This challenge teaches you operational excellence and production monitoring. You'll learn how to instrument code with meaningful metrics, trace requests across services, and structure logs for easy searching. The platform demonstrates how modern applications provide observability to detect, diagnose, and resolve issues quickly.

You'll practice defining SLIs and SLOs, creating alerting rules, and designing dashboards that surface critical information. This is essential knowledge for running reliable services in production.

## Features

- **Structured logging**: JSON logs with correlation IDs and context
- **Custom metrics**: Business and technical metrics with Micrometer
- **Prometheus integration**: Expose metrics at `/actuator/prometheus`
- **Distributed tracing**: End-to-end request tracking with Zipkin
- **Health checks**: Liveness and readiness probes
- **Application metadata**: Build info, git commit, and version
- **Error tracking**: Structured error logs with stack traces
- **Performance monitoring**: Request duration, database query times
- **Custom dashboards**: Pre-built Grafana dashboards
- **Alert definitions**: Example Prometheus alerting rules

## How to Run

Start Prometheus and Zipkin:
```bash
docker run -p 9090:9090 -v $PWD/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus
docker run -p 9411:9411 openzipkin/zipkin
```

Configure in `application.properties`:
```properties
management.endpoints.web.exposure.include=health,info,metrics,prometheus
management.tracing.sampling.probability=1.0
management.zipkin.tracing.endpoint=http://localhost:9411/api/v2/spans
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
```

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/observability-platform.jar
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
# Make some requests to generate metrics
curl http://localhost:8080/api/products
curl http://localhost:8080/api/products/123
curl http://localhost:8080/api/orders

# View metrics
curl http://localhost:8080/actuator/metrics

# View specific metric
curl http://localhost:8080/actuator/metrics/http.server.requests

# Response:
{
  "name": "http.server.requests",
  "measurements": [
    {"statistic": "COUNT", "value": 150},
    {"statistic": "TOTAL_TIME", "value": 4.523},
    {"statistic": "MAX", "value": 0.245}
  ],
  "availableTags": [
    {"tag": "uri", "values": ["/api/products", "/api/orders"]},
    {"tag": "status", "values": ["200", "404", "500"]}
  ]
}

# Check health
curl http://localhost:8080/actuator/health

# Response:
{
  "status": "UP",
  "components": {
    "db": {"status": "UP", "details": {"database": "PostgreSQL"}},
    "redis": {"status": "UP"},
    "diskSpace": {"status": "UP", "details": {"free": "50GB"}}
  }
}

# View build info
curl http://localhost:8080/actuator/info

# View Prometheus metrics
curl http://localhost:8080/actuator/prometheus

# Sample output:
# http_server_requests_seconds_count{uri="/api/products",status="200"} 125
# http_server_requests_seconds_sum{uri="/api/products",status="200"} 2.5
# jvm_memory_used_bytes{area="heap"} 512000000

# View traces in Zipkin UI
# Open http://localhost:9411

# View metrics in Prometheus UI
# Open http://localhost:9090
# Query: rate(http_server_requests_seconds_count[5m])

# Sample structured log output:
{
  "timestamp": "2024-01-15T10:30:45.123Z",
  "level": "INFO",
  "traceId": "abc123",
  "spanId": "def456",
  "service": "observability-platform",
  "logger": "com.example.ProductController",
  "message": "Product retrieved",
  "productId": 123,
  "userId": "user456",
  "duration": 15
}
```

