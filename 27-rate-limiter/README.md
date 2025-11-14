# Advanced Rate Limiter

## Description

The Advanced Rate Limiter protects APIs from abuse with multiple rate limiting algorithms (token bucket, sliding window, fixed window). Implement per-user, per-IP, and per-endpoint limits with Redis for distributed enforcement. Provide clear rate limit headers in responses and handle limit exceeded scenarios gracefully.

This challenge teaches you API protection and traffic management. You'll learn how different rate limiting algorithms work, their tradeoffs, and when to use each. The system demonstrates how to build production APIs that stay available under heavy load by preventing individual users or attackers from consuming all resources.

You'll practice implementing distributed counters with Redis, designing rate limit tiers (free, premium, enterprise), and providing good developer experience with clear limit information. This is essential for any public or high-traffic API.

## Features

- **Multiple algorithms**: Token bucket, sliding window, fixed window
- **Multi-level limits**: Per-user, per-IP, and per-endpoint
- **Distributed enforcement**: Redis-based counters for multi-instance apps
- **Rate limit tiers**: Different limits for free, premium, enterprise
- **Clear headers**: X-RateLimit-Limit, X-RateLimit-Remaining, X-RateLimit-Reset
- **Graceful degradation**: 429 status with retry-after header
- **Bypass mechanism**: Whitelist for internal services
- **Dynamic limits**: Update limits without deployment
- **Analytics**: Track rate limit hits and violators

## How to Run

Ensure Redis is running:
```bash
docker run -p 6379:6379 redis:7-alpine
```

Configure in `application.properties`:
```properties
spring.data.redis.host=localhost
ratelimit.default.requests-per-minute=60
ratelimit.default.algorithm=TOKEN_BUCKET
```

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/rate-limiter.jar
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
# Normal request (within limits)
curl -i http://localhost:8080/api/products \
  -H "X-API-Key: user_api_key"

# Response headers:
HTTP/1.1 200 OK
X-RateLimit-Limit: 60
X-RateLimit-Remaining: 59
X-RateLimit-Reset: 1705318860

# After exceeding limit:
curl -i http://localhost:8080/api/products \
  -H "X-API-Key: user_api_key"

# Response:
HTTP/1.1 429 Too Many Requests
X-RateLimit-Limit: 60
X-RateLimit-Remaining: 0
X-RateLimit-Reset: 1705318860
Retry-After: 45

{
  "error": "Rate limit exceeded",
  "message": "You have exceeded your rate limit of 60 requests per minute",
  "retryAfter": 45
}

# Check current rate limit status
curl http://localhost:8080/api/ratelimit/status \
  -H "X-API-Key: user_api_key"

# Response:
{
  "tier": "FREE",
  "limits": {
    "requestsPerMinute": 60,
    "requestsPerHour": 1000,
    "requestsPerDay": 10000
  },
  "usage": {
    "currentMinute": {
      "used": 45,
      "remaining": 15,
      "resetAt": "2024-01-15T10:31:00Z"
    },
    "currentHour": {
      "used": 523,
      "remaining": 477
    }
  }
}

# Admin: Update user's rate limit tier
curl -X PUT http://localhost:8080/api/admin/users/user123/tier \
  -H "Authorization: Bearer <admin_token>" \
  -H "Content-Type: application/json" \
  -d '{"tier": "PREMIUM"}'

# Admin: View rate limit analytics
curl http://localhost:8080/api/admin/ratelimit/analytics \
  -H "Authorization: Bearer <admin_token>"

# Response:
{
  "period": "last_24h",
  "totalRequests": 1250000,
  "limitExceeded": 45230,
  "topViolators": [
    {"userId": "user456", "violations": 523},
    {"ip": "192.168.1.100", "violations": 312}
  ]
}

# Admin: Temporarily bypass rate limiting
curl -X POST http://localhost:8080/api/admin/ratelimit/bypass \
  -H "Authorization: Bearer <admin_token>" \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "user789",
    "duration": 3600,
    "reason": "Load testing"
  }'
```

