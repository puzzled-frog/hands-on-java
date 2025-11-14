# Requirements: Weather Dashboard Backend

## Overview
Build an API that fetches data from an external weather API, caches results, and provides enriched endpoints with rate limiting.

## Functional Requirements

### External API Integration
- Integrate with OpenWeatherMap or similar weather API
- Fetch current weather data by city name or coordinates
- Fetch 5-day forecast data
- Handle API errors gracefully (network failures, invalid responses, rate limits)
- Include API key management through configuration

### Weather Endpoints
- `GET /api/weather/current?city={cityName}` - Get current weather
- `GET /api/weather/current?lat={lat}&lon={lon}` - Get weather by coordinates
- `GET /api/weather/forecast?city={cityName}` - Get 5-day forecast
- `GET /api/weather/history?city={cityName}` - Get historical data (last 7 days from cache)

### Caching Strategy
- Cache current weather data for 10 minutes
- Cache forecast data for 1 hour
- Store historical weather snapshots every hour
- Provide cache hit/miss metrics
- Support manual cache invalidation for a city

### Data Enrichment
- Convert temperature to multiple units (Celsius, Fahrenheit, Kelvin)
- Add descriptive weather condition text
- Calculate "feels like" temperature
- Provide sunrise/sunset times in local timezone
- Include air quality index if available

### Rate Limiting
- Limit external API calls to avoid exceeding provider quotas
- Implement request queuing for multiple simultaneous requests to same city
- Return cached data when rate limit approached
- Log rate limit warnings

### Scheduled Tasks
- Fetch and cache weather for popular cities every 15 minutes
- Clean up old historical data (keep only 7 days)
- Monitor external API health

## Non-Functional Requirements

### Performance
- Response time should be under 200ms for cached data
- External API calls should timeout after 5 seconds

### Reliability
- System should function with degraded data when external API is down
- Cache should prevent stampeding herd problem

### Configuration
- External API credentials should be externalized
- Cache durations should be configurable
- Rate limits should be configurable

