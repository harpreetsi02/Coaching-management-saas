# Coaching Management SaaS

A backend-focused Coaching Management SaaS built with Spring Boot, MariaDB, Spring Security, JWT, JPA/Hibernate, and REST APIs.

## Current Features

- User registration and login
- JWT-based authentication
- Role-based authorization
  - ADMIN
  - TEACHER
  - STUDENT
- BCrypt password hashing
- Admin user management
- Teacher creation by Admin
- Student management
- Course management
- Teacher-course ownership
- Student-course enrollment
- Batch management
- Batch creation and retrieval
- Batch pagination
- Batch filtering by course and teacher
- Batch student enrollment
- Batch capacity management
- Global exception handling
- Bean Validation
- DTO-based request/response architecture
- Entity-to-DTO mapping using Mapper classes
- Pagination using Spring Data JPA
- Dynamic filtering using JPA Specifications
- Transaction management
- JPA/Hibernate dirty checking
- Many-to-Many and Many-to-One relationships
- Database referential integrity handling

## API Concepts Implemented

- REST Endpoints
- HTTP Methods
- Request & Response
- HTTP Status Codes
- Authentication
- Authorization
- JWT Access Tokens
- Pagination
- Dynamic Filtering
- Error Handling

## Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Spring Security
- JWT
- MariaDB
- Bean Validation
- Maven

## Architecture

The project follows a layered architecture:

Controller
↓
Service
↓
Repository
↓
Database

DTOs and Mapper classes are used to keep API models separated from persistence entities.

## Future API Concepts

- Rate Limiting / Throttling
- OAuth 2.0
- Caching
- Idempotency
- Webhooks
- API Versioning
- OpenAPI / Swagger
- API Gateway

## Future Coaching Features

- Complete Batch Management
- Class Scheduling
- Attendance Management
- Fee & Payment Management
- Exams & Results
- Assignments
- Notifications
- Dashboards & Analytics
- Automated Notifications
- Testing
- Docker & Deployment