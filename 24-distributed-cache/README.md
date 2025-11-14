# Distributed Cache System

## Description

The Distributed Cache System implements caching with Redis to improve application performance. Use multiple cache patterns (cache-aside, write-through, read-through), handle cache invalidation, and implement cache warming. Add a local in-memory cache (Caffeine) for the most frequently accessed data in a two-level cache hierarchy.

This challenge teaches you caching strategies and performance optimization. You'll learn when to use different cache patterns, how to handle cache consistency, and the tradeoffs between freshness and performance. The system demonstrates how to dramatically reduce database load and response times through intelligent caching.

You'll practice implementing cache eviction policies, measuring cache hit rates, and designing for cache failures. This is essential knowledge for building high-performance systems that serve thousands of requests per second.

## Features

- **Redis integration**: Distributed cache shared across instances
- **Local cache**: Caffeine for ultra-fast L1 cache
- **Two-level hierarchy**: Check local cache, then Redis, then database
- **Multiple patterns**: Cache-aside, write-through, read-through
- **Cache warming**: Pre-populate cache on startup
- **TTL management**: Different expiration times per data type
- **Intelligent invalidation**: Clear specific entries or patterns
- **Metrics**: Track hit rates, miss rates, and latency
- **Conditional caching**: Don't cache empty or error results
- **Concurrency control**: Prevent cache stampede with locking

## How to Run

Ensure Redis is running:
```bash
docker run -p 6379:6379 redis:7-alpine
```

Configure in `application.properties`:
```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379
cache.local.max-size=10000
cache.local.ttl=5m
cache.redis.ttl=1h
```

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/distributed-cache.jar
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
# Get user (cached automatically with cache-aside)
curl http://localhost:8080/api/users/123

# First call: Cache miss (slower)
# Subsequent calls: Cache hit (fast)

# Get cache statistics
curl http://localhost:8080/api/cache/stats

# Response:
{
  "local": {
    "hitRate": 0.87,
    "missRate": 0.13,
    "size": 8542,
    "maxSize": 10000,
    "evictions": 1523
  },
  "redis": {
    "hitRate": 0.92,
    "missRate": 0.08,
    "keys": 45234,
    "memoryUsed": "128MB"
  },
  "overall": {
    "hitRate": 0.95,
    "avgLatency": "2ms"
  }
}

# Update user (write-through: updates DB and cache)
curl -X PUT http://localhost:8080/api/users/123 \
  -H "Content-Type: application/json" \
  -d '{"name": "John Updated", "email": "john@example.com"}'

# Invalidate specific cache entry
curl -X DELETE http://localhost:8080/api/cache/users/123

# Invalidate all users cache
curl -X DELETE http://localhost:8080/api/cache/users

# Warm cache with popular data
curl -X POST http://localhost:8080/api/cache/warm

# Clear all caches
curl -X DELETE http://localhost:8080/api/cache/clear

# Get specific key from cache (for debugging)
curl http://localhost:8080/api/cache/inspect?key=user:123

# Response:
{
  "key": "user:123",
  "level": "LOCAL",
  "value": {"id": 123, "name": "John Doe"},
  "ttl": 245,
  "size": "256 bytes"
}
```

