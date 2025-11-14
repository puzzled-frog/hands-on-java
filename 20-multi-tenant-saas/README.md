# Multi-tenant SaaS Application

## Description

The Multi-tenant SaaS Application is a blogging platform where each organization has completely isolated data. Support tenant identification via subdomain or header, ensure absolute data isolation, and implement features that work across thousands of independent tenants without code changes.

This challenge introduces you to multi-tenancy patterns and data isolation strategies. You'll learn how to partition data by tenant, automatically filter queries to prevent data leaks, and design APIs that work in multi-tenant contexts. The platform demonstrates how SaaS applications serve multiple customers from a single codebase while keeping their data strictly separated.

You'll practice implementing tenant resolution, securing cross-tenant boundaries, and designing schemas that scale to many tenants. This is essential knowledge for building modern SaaS products that serve multiple customers efficiently.

## Features

- **Tenant management**: Register and configure organizations
- **Subdomain identification**: Each tenant gets a unique subdomain
- **Header-based access**: API access via X-Tenant-ID header
- **Complete data isolation**: Zero cross-tenant data leaks
- **User management**: Users scoped to specific tenants
- **Blog platform**: Create and publish posts per tenant
- **Role system**: Owner, admin, editor, viewer roles per tenant
- **Super admin**: Cross-tenant access for support
- **Tenant suspension**: Activate/deactivate tenants

## How to Run

Configure domain in `application.properties`:
```properties
app.domain=blogplatform.com
app.multi-tenancy.strategy=SCHEMA_PER_TENANT
```

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/multi-tenant-saas.jar
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
# Register a new tenant (public endpoint)
curl -X POST http://localhost:8080/api/tenants \
  -H "Content-Type: application/json" \
  -d '{
    "subdomain": "acme",
    "companyName": "Acme Corporation",
    "ownerEmail": "admin@acme.com",
    "plan": "PROFESSIONAL"
  }'

# Access via subdomain (automatic tenant resolution)
curl -X POST https://acme.blogplatform.com/api/posts \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Welcome to Acme Blog",
    "content": "This is our first post...",
    "status": "PUBLISHED"
  }'

# Access via header (for API clients)
curl -X GET http://localhost:8080/api/acme/posts \
  -H "X-Tenant-ID: acme" \
  -H "Authorization: Bearer <token>"

# Register user within tenant
curl -X POST https://acme.blogplatform.com/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@acme.com",
    "password": "SecurePass123!",
    "role": "EDITOR"
  }'

# Super admin: View all tenants
curl http://localhost:8080/api/admin/tenants \
  -H "Authorization: Bearer <super_admin_token>"

# Super admin: Access specific tenant's data
curl http://localhost:8080/api/admin/tenants/acme/posts \
  -H "Authorization: Bearer <super_admin_token>"
```

