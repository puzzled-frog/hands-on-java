# Challenge List

A progressive sequence of 29 challenges covering the full Java enterprise stack, from basic syntax to production-ready applications.

---

## Phase 1: Core Java Fundamentals (1-5)

### 1. [Hello Calculator](01-hello-calculator/)
Build a command-line calculator that reads two numbers and an operator (+, -, *, /) from the user, performs the calculation, and prints the result. Focus: basic syntax, variables, conditionals, console I/O, and compilation.

### 2. [Number Guesser](02-number-guesser/)
Create a guessing game where the program picks a random number (1-100) and the user has limited attempts to guess it. The program provides hints (too high/too low) after each guess. Focus: loops, random numbers, user input validation, and control flow.

### 3. [Todo List Manager](03-todo-list-manager/)
Build a console application to manage a todo list. Users can add, view, complete, and delete tasks. Tasks are stored in memory during runtime. Focus: ArrayList, object-oriented design, basic encapsulation, and working with collections.

### 4. [CSV Data Analyzer](04-csv-data-analyzer/)
Read a CSV file containing sales data (date, product, quantity, price) and compute statistics: total revenue, best-selling product, average sale value. Focus: file I/O, String manipulation, data processing, and working with structured data.

### 5. [Contact Book](05-contact-book/)
Create a contact management system that stores contacts (name, phone, email) in a file and allows search, add, update, and delete operations. Focus: file persistence, data serialization, Maps, and CRUD operations.

---

## Phase 2: Intermediate Java & Design (6-11)

### 6. [Temperature Converter](06-temperature-converter/)
Build a command-line application that converts temperatures between Celsius, Fahrenheit, and Kelvin scales. Includes menu selection, input validation, absolute zero checking, and proper error handling. Focus: enums with fields, records, try-catch, Optional, Maps, switch expressions, and modern Java features.

### 7. [Text-Based RPG Battle System](07-rpg-battle-system/)
Build a turn-based combat system with different character classes (Warrior, Mage, Archer), each with unique abilities. Include inventory management and simple save/load functionality. Focus: inheritance, polymorphism, abstract classes, interfaces, and game state management.

### 8. [Expense Tracker with Reports](08-expense-tracker/)
Create an expense tracking application that categorizes expenses and generates monthly reports. Export reports to CSV and JSON formats. Focus: LocalDate/Time API, generics, multiple file formats, and data transformation.

### 9. [Library Management System](09-library-management/)
Build a system to manage books, members, and lending. Track due dates, calculate fines for late returns, and search by multiple criteria. Focus: complex object relationships, enums, Optional, custom exceptions, and business logic.

### 10. [HTTP Log Parser](10-http-log-parser/)
Parse web server access logs (Apache/Nginx format) and generate analytics: requests per hour, most visited pages, error rates, user agents. Handle malformed log entries gracefully. Focus: regex, Stream API, functional programming, error handling, and data aggregation.

### 11. [Unit Testing & Refactoring Challenge](11-unit-testing-refactoring/)
Take a provided codebase (a simple e-commerce cart system) with no tests and poor design. Add comprehensive unit tests, refactor for better design, and improve testability. Focus: JUnit, test-driven development, mocking, refactoring techniques, and code quality.

---

## Phase 3: Spring Boot & Web Fundamentals (12-16)

### 12. [RESTful Note-Taking API](12-note-taking-api/)
Build a REST API for managing notes with endpoints for CRUD operations. Include request validation, proper HTTP status codes, and error handling. Focus: Spring Boot basics, REST controllers, DTOs, validation annotations, and API design.

### 13. [Book Catalog API with Database](13-book-catalog-api/)
Extend a book catalog API to use PostgreSQL. Implement search with filters (title, author, genre, year), pagination, and sorting. Focus: Spring Data JPA, database design, queries, pagination, and relational data.

### 14. [Recipe Sharing Platform](14-recipe-sharing-platform/)
Create an API where users can share recipes with ingredients, steps, and ratings. Include recipe search with multiple filters and average rating calculation. Focus: complex entity relationships, custom queries, transactions, and data integrity.

### 15. [Weather Dashboard Backend](15-weather-dashboard/)
Build an API that fetches data from an external weather API, caches it, and provides enriched endpoints (current weather, forecast, historical data). Include rate limiting for the external API. Focus: RestTemplate/WebClient, caching (Caffeine/Redis), scheduled tasks, and external API integration.

### 16. [Job Board API](16-job-board-api/)
Create a job posting platform with companies, jobs, and applications. Implement role-based access (companies can post, users can apply) and application status tracking. Focus: Spring Security basics, authentication, authorization, user roles, and access control.

---

## Phase 4: Advanced Spring & Enterprise Patterns (17-23)

### 17. [E-commerce Order Service](17-ecommerce-order-service/)
Build an order management system with inventory tracking, order state machine (pending → confirmed → shipped → delivered), and payment status. Focus: state patterns, service layer design, complex business logic, and transactional workflows.

### 18. [JWT Authentication Service](18-jwt-auth-service/)
Implement a complete authentication system with registration, login, token refresh, password reset, and email verification. Include token blacklisting for logout. Focus: JWT, Spring Security, password encryption, email sending, and security best practices.

### 19. [Real-time Notification System](19-notification-system/)
Create a notification service that sends messages through multiple channels (email, SMS, push). Use a message queue (RabbitMQ/Kafka) for async processing. Focus: event-driven architecture, message queues, async processing, and multi-channel delivery.

### 20. [Multi-tenant SaaS Application](20-multi-tenant-saas/)
Build a blogging platform where each organization has isolated data. Support tenant identification via subdomain or header, and ensure data isolation. Focus: multi-tenancy patterns, data isolation, security considerations, and scalable architecture.

### 21. [File Processing Pipeline](21-file-processing-pipeline/)
Create a system that processes uploaded CSV files asynchronously: validate, transform, store in database, and notify on completion. Handle large files and provide progress tracking. Focus: async processing, CompletableFuture, batch processing, progress tracking, and error recovery.

### 22. [API Gateway & Service Orchestration](22-api-gateway/)
Build a gateway that aggregates data from multiple microservices (user service, product service, order service) and provides unified endpoints. Include circuit breaker pattern for resilience. Focus: service orchestration, resilience patterns, circuit breakers (Resilience4j), and distributed systems.

### 23. [Search Engine with Elasticsearch](23-search-engine/)
Create a product search service using Elasticsearch. Support full-text search, filters, facets, autocomplete, and relevance scoring. Focus: Elasticsearch integration, search optimization, indexing strategies, and search relevance.

---

## Phase 5: Production-Ready & Advanced Patterns (24-29)

### 24. [Distributed Cache System](24-distributed-cache/)
Build a caching service with Redis that supports multiple eviction strategies, cache warming, and distributed locking for cache stampede prevention. Focus: Redis, distributed caching, cache patterns, performance optimization, and concurrency.

### 25. [Observability & Monitoring Platform](25-observability-platform/)
Create an application with comprehensive observability: structured logging, metrics (Prometheus), distributed tracing (Zipkin/Jaeger), and health checks. Include custom business metrics. Focus: logging (SLF4J/Logback), Micrometer, tracing, monitoring, and operational excellence.

### 26. [Event Sourcing Order System](26-event-sourcing-system/)
Rebuild an order system using event sourcing: store all changes as events, support event replay, and maintain projections for queries. Focus: event sourcing pattern, CQRS, event store, eventual consistency, and architectural patterns.

### 27. [Rate Limiter & API Throttling](27-rate-limiter/)
Implement a sophisticated rate limiting system with multiple strategies (fixed window, sliding window, token bucket). Support per-user, per-IP, and per-endpoint limits. Focus: rate limiting algorithms, distributed rate limiting, Redis, and API protection.

### 28. [Batch Processing System](28-batch-processing-system/)
Create a system that processes large datasets in batches using Spring Batch. Include job scheduling, chunk processing, error handling, restart capability, and job monitoring. Focus: Spring Batch, scheduled jobs, chunk processing, job orchestration, and data processing at scale.

### 29. [Full-Stack Social Feed](29-social-feed/)
Build a Twitter-like social feed with posts, follows, likes, comments, and real-time updates via WebSocket. Include timeline algorithms, notifications, and media uploads (S3). Focus: WebSocket, real-time communication, complex queries, performance optimization, file storage, and bringing everything together.

---

## Coverage Summary

**Core Java**: Syntax, OOP, collections, file I/O, exceptions, functional programming, streams, concurrency
**Spring Framework**: Spring Boot, MVC, Data JPA, Security, Batch, caching
**Data**: JDBC, JPA/Hibernate, Elasticsearch, Redis
**Web**: REST APIs, HTTP clients, WebSocket, validation
**Architecture**: Event-driven, CQRS, microservices patterns, multi-tenancy
**Enterprise**: JWT auth, rate limiting, monitoring, distributed systems
**Testing**: Unit tests, integration tests, mocking, TDD
**DevOps concerns**: Observability, metrics, logging, health checks

This progression ensures each challenge stretches learners appropriately while building toward production-ready, enterprise-grade Java development.

