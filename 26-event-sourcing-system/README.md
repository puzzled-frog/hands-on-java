# Event Sourcing System

## Description

The Event Sourcing System implements a banking application where all state changes are stored as immutable events. Rebuild account state by replaying events, implement event versioning for schema evolution, and use CQRS to separate read and write models. Create projections optimized for different query patterns.

This challenge introduces you to event sourcing and CQRS patterns. You'll learn how to model domain events, store them as the source of truth, and derive current state through event replay. The system demonstrates how event sourcing provides complete audit trails, enables time travel, and supports complex business requirements that traditional CRUD models struggle with.

You'll practice designing event schemas, handling event versioning, and building read models that stay eventually consistent with the event store. This is advanced knowledge for building systems where audit, compliance, and historical analysis are critical.

## Features

- **Event store**: Immutable append-only event log
- **Event sourcing**: Rebuild state by replaying events
- **CQRS**: Separate write (commands) and read (queries) models
- **Account management**: Open accounts, deposits, withdrawals, transfers
- **Event replay**: Rebuild account state from any point in time
- **Projections**: Optimized read models for different queries
- **Event versioning**: Handle schema changes gracefully
- **Snapshots**: Periodic snapshots for performance
- **Consistency**: Ensure balance never goes negative
- **Audit trail**: Complete history of all transactions

## How to Run

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/event-sourcing-system.jar
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
# Open account (creates AccountOpenedEvent)
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "accountHolder": "John Doe",
    "initialDeposit": 1000.00
  }'

# Response:
{
  "accountId": "ACC-123",
  "eventId": "evt-001",
  "timestamp": "2024-01-15T10:30:00Z"
}

# Deposit money (creates MoneyDepositedEvent)
curl -X POST http://localhost:8080/api/accounts/ACC-123/deposit \
  -H "Content-Type: application/json" \
  -d '{"amount": 500.00, "description": "Paycheck"}'

# Withdraw money (creates MoneyWithdrawnEvent)
curl -X POST http://localhost:8080/api/accounts/ACC-123/withdraw \
  -H "Content-Type: application/json" \
  -d '{"amount": 200.00, "description": "ATM withdrawal"}'

# Transfer between accounts (creates MoneyTransferredEvent)
curl -X POST http://localhost:8080/api/accounts/ACC-123/transfer \
  -H "Content-Type: application/json" \
  -d '{
    "toAccountId": "ACC-456",
    "amount": 300.00,
    "description": "Rent payment"
  }'

# Query current balance (from read model/projection)
curl http://localhost:8080/api/accounts/ACC-123/balance

# Response:
{
  "accountId": "ACC-123",
  "balance": 1000.00,
  "asOf": "2024-01-15T10:35:00Z"
}

# Get full account history (all events)
curl http://localhost:8080/api/accounts/ACC-123/events

# Response:
{
  "accountId": "ACC-123",
  "events": [
    {
      "eventId": "evt-001",
      "type": "AccountOpened",
      "timestamp": "2024-01-15T10:30:00Z",
      "data": {"accountHolder": "John Doe", "initialDeposit": 1000.00}
    },
    {
      "eventId": "evt-002",
      "type": "MoneyDeposited",
      "timestamp": "2024-01-15T10:32:00Z",
      "data": {"amount": 500.00, "description": "Paycheck"}
    },
    {
      "eventId": "evt-003",
      "type": "MoneyWithdrawn",
      "timestamp": "2024-01-15T10:33:00Z",
      "data": {"amount": 200.00, "description": "ATM withdrawal"}
    }
  ]
}

# Replay events to get balance at specific time
curl "http://localhost:8080/api/accounts/ACC-123/balance?asOf=2024-01-15T10:32:00Z"

# Response:
{
  "accountId": "ACC-123",
  "balance": 1500.00,
  "asOf": "2024-01-15T10:32:00Z"
}

# Get transaction summary (from projection)
curl http://localhost:8080/api/accounts/ACC-123/summary

# Response:
{
  "accountId": "ACC-123",
  "totalDeposits": 1500.00,
  "totalWithdrawals": 500.00,
  "currentBalance": 1000.00,
  "transactionCount": 3
}
```

