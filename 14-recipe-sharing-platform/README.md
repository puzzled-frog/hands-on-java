# Recipe Sharing Platform

## Description

The Recipe Sharing Platform is an API where users can share recipes with detailed ingredients, step-by-step instructions, and ratings. Search for recipes by various criteria and see average ratings to find the best dishes. This platform demonstrates complex entity relationships and data integrity management.

This challenge teaches you to model and manage relationships between multiple entities: recipes have many ingredients, many steps, and many ratings. You'll learn how to structure these relationships in a database, use transactions to maintain consistency, and write custom queries for aggregations like average ratings. The platform shows you how to handle one-to-many and many-to-many relationships effectively.

You'll practice designing APIs that manage hierarchical data, ensuring referential integrity across related tables, and calculating derived values efficiently. This is essential knowledge for building any application with interconnected data.

## Features

- **Recipe management**: Create recipes with title, description, times, and servings
- **Ingredient tracking**: Multiple ingredients per recipe with quantities and units
- **Step-by-step instructions**: Sequential preparation steps
- **Rating system**: 1-5 star ratings with average calculation
- **Duplicate prevention**: Users can't rate the same recipe twice
- **Advanced search**: Filter by prep time, cooking time, and minimum rating
- **Cascade operations**: Deleting a recipe removes ingredients and steps
- **Transaction safety**: Ensure data consistency across related entities

## How to Run

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/recipe-sharing-platform.jar
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
# Create a recipe
curl -X POST http://localhost:8080/api/recipes \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Chocolate Chip Cookies",
    "description": "Classic homemade cookies",
    "prepTime": 15,
    "cookTime": 12,
    "servings": 24,
    "ingredients": [
      {"name": "Flour", "quantity": 2.25, "unit": "cups"},
      {"name": "Butter", "quantity": 1, "unit": "cup"},
      {"name": "Sugar", "quantity": 0.75, "unit": "cup"}
    ],
    "steps": [
      {"order": 1, "instruction": "Preheat oven to 375°F"},
      {"order": 2, "instruction": "Mix butter and sugars until creamy"},
      {"order": 3, "instruction": "Add flour gradually"}
    ]
  }'

# Get full recipe details
curl http://localhost:8080/api/recipes/1

# Add a rating
curl -X POST http://localhost:8080/api/recipes/1/ratings \
  -H "Content-Type: application/json" \
  -d '{"userId": "user123", "rating": 5}'

# Search recipes
curl "http://localhost:8080/api/recipes/search?maxPrepTime=30&minRating=4&sort=rating,desc"
```

