# Requirements: Recipe Sharing Platform

## Overview
Create an API where users can share recipes with ingredients, preparation steps, and ratings.

## Functional Requirements

### Recipe Management
- Create recipes with title, description, preparation time, cooking time, servings
- Add multiple ingredients with quantities and units
- Add preparation steps in sequential order
- Update recipe information
- Delete recipes (cascade delete ingredients and steps)
- Retrieve recipe with all details (ingredients and steps)

### Ingredient Management
- Each ingredient has name, quantity, and unit
- Support optional ingredients
- Maintain ingredient order within recipe
- Validate quantity is positive

### Step Management
- Each step has order number and instruction text
- Steps must be numbered sequentially (1, 2, 3...)
- Support updating step order
- Instructions must be non-empty

### Rating System
- Allow users to rate recipes (1-5 stars)
- Calculate and display average rating per recipe
- Track number of ratings
- Prevent duplicate ratings from same user
- Update average rating when new ratings added

### Search and Discovery
- Search recipes by title or description (partial match)
- Filter by preparation time (e.g., under 30 minutes)
- Filter by cooking time
- Filter by minimum rating
- Sort by rating (highest first)
- Sort by total time (prep + cook)
- Support combining filters

### API Endpoints
- `POST /api/recipes` - Create recipe
- `GET /api/recipes` - List all recipes with filters and sorting
- `GET /api/recipes/{id}` - Get full recipe details
- `PUT /api/recipes/{id}` - Update recipe
- `DELETE /api/recipes/{id}` - Delete recipe
- `POST /api/recipes/{id}/ratings` - Add rating
- `GET /api/recipes/{id}/ratings` - Get recipe ratings

## Non-Functional Requirements

### Data Integrity
- Use transactions for operations affecting multiple tables
- Ensure referential integrity between recipes, ingredients, and steps
- Average ratings must be accurately calculated

### Performance
- Efficiently load recipes with all related data
- Optimize queries to avoid N+1 problems
- Index frequently searched fields

