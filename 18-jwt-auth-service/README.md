# JWT Authentication Service

## Description

The JWT Authentication Service implements a complete authentication system with registration, login, token refresh, password reset, and email verification. Use JWT tokens for stateless authentication, implement token blacklisting for logout, and handle the full authentication lifecycle securely.

This challenge teaches you comprehensive authentication patterns and security best practices. You'll learn how to generate and validate JWT tokens, implement refresh token rotation, handle email verification flows, and manage password resets safely. The service demonstrates industry-standard authentication that you'd find in production applications.

You'll practice cryptographic operations like password hashing with BCrypt, secure token generation, and protecting against common vulnerabilities. This is critical knowledge for building secure applications that handle user credentials and sensitive data.

## Features

- **User registration**: Email, password, and profile information
- **Email verification**: Activate accounts via token links
- **Login system**: Authenticate and receive JWT tokens
- **Token refresh**: Get new access tokens without re-authentication
- **Logout**: Blacklist tokens to invalidate them
- **Password reset**: Secure flow with time-limited reset tokens
- **Password strength**: Enforce complexity requirements
- **Token management**: Automatic cleanup of expired blacklist entries
- **Security headers**: Protection against common web vulnerabilities

## How to Run

Configure email settings in `application.properties`:
```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
jwt.secret=your_secret_key_min_256_bits
jwt.access-token-expiration=900000
jwt.refresh-token-expiration=604800000
```

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/jwt-auth-service.jar
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
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "SecurePass123!",
    "firstName": "John",
    "lastName": "Doe"
  }'

# Verify email (click link in email or use token)
curl -X POST http://localhost:8080/api/auth/verify-email \
  -H "Content-Type: application/json" \
  -d '{"token": "verification_token_here"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "user@example.com", "password": "SecurePass123!"}'

# Response:
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
  "tokenType": "Bearer",
  "expiresIn": 900
}

# Refresh access token
curl -X POST http://localhost:8080/api/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{"refreshToken": "eyJhbGciOiJIUzI1NiIs..."}'

# Request password reset
curl -X POST http://localhost:8080/api/auth/forgot-password \
  -H "Content-Type: application/json" \
  -d '{"email": "user@example.com"}'

# Reset password with token
curl -X POST http://localhost:8080/api/auth/reset-password \
  -H "Content-Type: application/json" \
  -d '{"token": "reset_token", "newPassword": "NewSecurePass123!"}'

# Logout
curl -X POST http://localhost:8080/api/auth/logout \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIs..."
```

