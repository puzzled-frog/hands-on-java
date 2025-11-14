# Search Engine with Elasticsearch

## Description

The Search Engine creates a product search service using Elasticsearch with full-text search, filters, facets, autocomplete, and relevance tuning. Implement fuzzy matching for typos, highlighting for matched terms, and boost important fields to improve search quality. Build a search experience that helps users find what they need quickly.

This challenge introduces you to Elasticsearch and search engine concepts. You'll learn how to index documents, design search queries with multiple conditions, implement faceted navigation, and tune relevance scores. The service demonstrates how modern applications provide powerful search functionality that goes far beyond simple SQL LIKE queries.

You'll practice designing search schemas, implementing autocomplete with n-grams, and analyzing search behavior to improve results. This is essential knowledge for applications where users need to discover content through search.

## Features

- **Full-text search**: Search across product names and descriptions
- **Fuzzy matching**: Handle typos and misspellings automatically
- **Filters**: Category, brand, price range, rating, stock status
- **Faceted search**: Dynamic facet counts based on current search
- **Autocomplete**: Suggest products as users type (min 2 characters)
- **Relevance tuning**: Boost exact matches and popular products
- **Highlighting**: Show matched terms in results
- **Sorting**: By relevance, price, rating, or date
- **Pagination**: Efficient pagination for large result sets
- **Analytics**: Track searches and click-through rates

## How to Run

Ensure Elasticsearch is running:
```bash
docker run -p 9200:9200 -e "discovery.type=single-node" elasticsearch:8.11.0
```

Configure in `application.properties`:
```properties
spring.elasticsearch.uris=http://localhost:9200
```

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/search-engine.jar
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
# Index a product
curl -X POST http://localhost:8080/api/products/index \
  -H "Content-Type: application/json" \
  -d '{
    "id": "P123",
    "name": "Wireless Bluetooth Headphones",
    "description": "Premium noise-canceling headphones",
    "category": "Electronics",
    "brand": "AudioTech",
    "price": 149.99,
    "rating": 4.5,
    "stockStatus": "IN_STOCK",
    "tags": ["bluetooth", "wireless", "noise-canceling"]
  }'

# Search with filters
curl "http://localhost:8080/api/search?q=headphones&category=Electronics&minPrice=100&maxPrice=200&minRating=4&sort=relevance"

# Response:
{
  "results": [
    {
      "id": "P123",
      "name": "Wireless Bluetooth <mark>Headphones</mark>",
      "description": "Premium noise-canceling <mark>headphones</mark>",
      "price": 149.99,
      "rating": 4.5,
      "score": 12.5
    }
  ],
  "facets": {
    "categories": {
      "Electronics": 245,
      "Audio": 156
    },
    "brands": {
      "AudioTech": 45,
      "SoundPro": 38
    },
    "priceRanges": {
      "$0-$50": 89,
      "$50-$100": 124,
      "$100-$200": 98
    }
  },
  "totalResults": 245,
  "page": 0,
  "size": 20
}

# Autocomplete suggestions
curl "http://localhost:8080/api/search/suggest?q=wire"

# Response:
{
  "suggestions": [
    "Wireless Headphones",
    "Wireless Mouse",
    "Wireless Keyboard",
    "Wireless Charger"
  ]
}

# Bulk reindex all products
curl -X POST http://localhost:8080/api/products/reindex
```

