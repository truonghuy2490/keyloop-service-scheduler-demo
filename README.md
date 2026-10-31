# Keyloop Service Scheduler

Backend appointment scheduling service built with
Java, Spring Boot, PostgreSQL, and Clean Architecture.

## Features
- Appointment availability checking
- Technician qualification validation
- Transactional resource booking
- Appointment cancellation and status history

## Architecture
See [System Design](docs/system-design.md).

## Prerequisites
- JDK 21
- Docker Desktop

## Run locally
1. Start PostgreSQL using Docker Compose.
2. Configure environment variables.
3. Run database migrations.
4. Start the Spring Boot application.

## Testing
Run `./mvnw verify`.

## API documentation
Available at the configured OpenAPI endpoint when the
application is running.

## AI Collaboration
See [AI Collaboration Narrative](docs/ai-collaboration.md).

// ERROR: "FATAL: invalid value for parameter "TimeZone": "Asia/Saigon""
$env:JAVA_TOOL_OPTIONS = "-Duser.timezone=UTC"

java -XshowSettings:properties -version 2>&1 |
    Select-String "user.timezone"
