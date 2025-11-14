# Weather Dashboard Backend

## Description

The Weather Dashboard fetches data from an external weather API, caches results for performance, and provides enriched endpoints with current weather, forecasts, and historical data. Implement rate limiting to avoid exceeding provider quotas and scheduled tasks to keep popular cities' data fresh.

This challenge introduces you to external API integration, caching strategies, and scheduled jobs. You'll learn how to use RestTemplate or WebClient to consume third-party APIs, implement caching layers to reduce API calls and improve response times, and schedule background tasks to pre-populate cache. The application demonstrates how to build APIs that aggregate and enhance data from external sources.

You'll practice handling API failures gracefully, managing rate limits, and designing cache invalidation strategies. This teaches you how to build reliable systems that depend on external services while maintaining good performance.

## Features

- **External API integration**: Fetch from OpenWeatherMap or similar services
- **Current weather**: Get real-time weather by city or coordinates
- **5-day forecast**: Detailed predictions with hourly breakdowns
- **Caching layer**: 10-minute cache for current weather, 1-hour for forecasts
- **Data enrichment**: Multiple temperature units, descriptive conditions
- **Historical tracking**: Store hourly snapshots for 7-day history
- **Rate limiting**: Prevent exceeding API provider quotas
- **Scheduled updates**: Auto-refresh popular cities every 15 minutes
- **Fallback handling**: Return cached data when API is unavailable

## How to Run

Set your weather API key in `application.properties`:
```properties
weather.api.key=your_api_key_here
weather.api.url=https://api.openweathermap.org/data/2.5
```

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/weather-dashboard.jar
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
# Get current weather by city
curl http://localhost:8080/api/weather/current?city=London

# Response:
{
  "city": "London",
  "temperature": {
    "celsius": 15.5,
    "fahrenheit": 59.9,
    "kelvin": 288.65
  },
  "condition": "Partly cloudy",
  "humidity": 72,
  "windSpeed": 12.5,
  "feelsLike": 14.2,
  "sunrise": "2024-01-15T07:45:00Z",
  "sunset": "2024-01-15T16:30:00Z",
  "cached": true
}

# Get 5-day forecast
curl http://localhost:8080/api/weather/forecast?city=London

# Get historical data
curl http://localhost:8080/api/weather/history?city=London&days=7

# Weather by coordinates
curl "http://localhost:8080/api/weather/current?lat=51.5074&lon=-0.1278"
```

