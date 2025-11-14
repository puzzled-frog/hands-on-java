# Full-Stack Social Feed

## Description

The Social Feed is a Twitter-like platform with posts, follows, likes, comments, and real-time updates via WebSocket. Build a complete social media backend with timeline algorithms, notifications, media uploads, and all the features users expect from modern social platforms.

This is the capstone challenge that brings together everything you've learned: Spring Boot, databases, security, real-time communication, caching, file storage, and performance optimization. You'll design a system that handles complex relationships (users, posts, follows, likes), implements efficient queries for timelines, and delivers updates instantly through WebSocket connections.

This challenge teaches you how to architect scalable applications, handle concurrent users, optimize database queries, and create engaging user experiences. It's your opportunity to demonstrate mastery of the full Java enterprise stack by building something real people would want to use.

## Features

- **User management**: Registration, profiles, avatars, and bios
- **Following system**: Follow/unfollow users, view followers and following
- **Post creation**: Text posts with optional images
- **Timeline algorithm**: Chronological feed from followed users
- **Like system**: Like posts with real-time count updates
- **Comment system**: Add comments and nested replies
- **Real-time updates**: WebSocket for instant feed updates
- **Notifications**: Follow, like, and comment notifications
- **Media uploads**: Image hosting integration (S3 or similar)
- **Search and discovery**: Find users and trending posts
- **Infinite scroll**: Pagination for smooth browsing

## How to Run

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/social-feed.jar
```

API will be available at: `http://localhost:8080`
WebSocket endpoint: `ws://localhost:8080/ws`

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
# Register and login
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email": "user@example.com", "username": "johndoe", "password": "SecurePass123!"}'

# Create a post
curl -X POST http://localhost:8080/api/posts \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"content": "Just deployed my first Java app!", "imageUrl": "https://..."}'

# Get timeline
curl http://localhost:8080/api/timeline?page=0&size=20 \
  -H "Authorization: Bearer <token>"

# Like a post
curl -X POST http://localhost:8080/api/posts/123/like \
  -H "Authorization: Bearer <token>"

# Follow a user
curl -X POST http://localhost:8080/api/users/johndoe/follow \
  -H "Authorization: Bearer <token>"

# WebSocket connection (JavaScript example)
const ws = new WebSocket('ws://localhost:8080/ws');
ws.send(JSON.stringify({
  type: 'SUBSCRIBE',
  channel: 'timeline'
}));
```

