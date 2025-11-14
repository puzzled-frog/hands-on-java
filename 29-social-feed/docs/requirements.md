# Requirements: Full-Stack Social Feed

## Overview
Build a Twitter-like social feed platform with posts, follows, likes, comments, and real-time updates via WebSocket, bringing together all learned concepts.

## Functional Requirements

### User Management
- Register with email, username, password, display name, bio
- Login with JWT authentication
- Update profile (display name, bio, avatar URL)
- View user profiles with post count, follower count, following count
- Search users by username or display name

### Following System
- Follow other users
- Unfollow users
- View followers list
- View following list
- Prevent following yourself
- Track follow/unfollow timestamps

### Post Management
- Create posts with text content (max 280 characters)
- Upload images with posts (store URLs, integrate with S3 or similar)
- Edit own posts within 5 minutes of posting
- Delete own posts
- Posts show author, content, image URL, timestamp, like count, comment count

### Timeline Algorithm
- Home feed shows posts from followed users (chronological)
- Support infinite scroll with pagination
- Cache recent timeline data
- Refresh timeline efficiently without full reload
- Show "new posts" indicator when updates available

### Like System
- Like posts
- Unlike posts
- View list of users who liked a post
- Track like count per post
- Prevent duplicate likes

### Comment System
- Add comments to posts
- Comments have text content (max 500 characters)
- Delete own comments
- View all comments for a post (chronological)
- Track comment count per post
- Support nested replies (one level deep)

### Real-Time Updates
- Receive new posts in real-time via WebSocket
- Receive new likes in real-time
- Receive new comments in real-time
- Receive notifications in real-time
- Support reconnection when connection drops
- Scale WebSocket connections across instances

### Notifications
- Notify when someone follows you
- Notify when someone likes your post
- Notify when someone comments on your post
- Mark notifications as read
- View unread notification count
- List all notifications with pagination

### Trending and Discovery
- Trending posts (most liked in last 24 hours)
- Suggested users to follow
- Hashtag support in posts
- Search posts by hashtag

### API Endpoints
- Authentication: `/api/auth/register`, `/api/auth/login`
- Users: `/api/users/{username}`, `/api/users/search`
- Following: `/api/users/{username}/follow`, `/api/users/{username}/followers`
- Posts: `/api/posts`, `/api/posts/{id}`, `/api/timeline`
- Likes: `/api/posts/{id}/like`, `/api/posts/{id}/likes`
- Comments: `/api/posts/{id}/comments`
- Notifications: `/api/notifications`
- WebSocket: `/ws` with `/topic/timeline`, `/topic/notifications`

## Non-Functional Requirements

### Performance
- Timeline loads in under 500ms
- Support thousands of concurrent users
- Real-time updates with minimal latency
- Optimize database queries for large datasets
- Cache frequently accessed data

### Scalability
- Horizontal scaling of API servers
- WebSocket connection management across instances
- Database indexing for performance
- Consider read replicas for read-heavy operations

### Security
- Secure JWT implementation
- Protect against SQL injection and XSS
- Rate limiting per user
- Validate file uploads
- CORS configuration for frontend

### User Experience
- Real-time feel with WebSocket
- Optimistic UI updates (like/unlike immediately)
- Smooth infinite scroll
- Responsive to user actions
- Handle offline scenarios gracefully

