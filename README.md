# Student Task Manager REST API

A backend REST API built with Spring Boot, PostgreSQL, and JWT authentication.
Allows users to register, log in, and manage their personal tasks.

## Technologies

- Java 17
- Spring Boot 4.x
- Spring Security + JWT
- PostgreSQL
- Spring Data JPA (Hibernate)
- Lombok
- Swagger / OpenAPI

## Architecture

src/main/java/com/safiyat/taskmanager/
├── config/         # Security, Swagger, App configuration
├── controller/     # REST endpoints
├── dto/            # Request and Response DTOs
├── entity/         # JPA entities (User, Task)
├── exception/      # Global exception handler
├── repository/     # Spring Data JPA repositories
├── security/       # JWT filter and service
└── service/        # Business logic (interfaces + impl)

## Database Schema

**users**: id, username, email, password, role  
**tasks**: id, title, description, status, deadline, created_at, updated_at, user_id

## Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /auth/register | Register new user |
| POST | /auth/login | Login and get JWT token |

### Tasks (requires Bearer token)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /tasks | Get all tasks (paginated, filterable) |
| GET | /tasks/{id} | Get task by ID |
| POST | /tasks | Create new task |
| PUT | /tasks/{id} | Update task |
| DELETE | /tasks/{id} | Delete task |
| PATCH | /tasks/{id}/complete | Mark task as completed |

### Query Parameters for GET /tasks
- `page` (default: 0)
- `size` (default: 10)
- `sortBy` (default: createdAt)
- `status` (PENDING / IN_PROGRESS / COMPLETED)

## How to Run

### Prerequisites
- Java 17+
- PostgreSQL running locally
- Database named `taskdb` created

### Setup

1. Clone the repository
```bash
   git clone https://github.com/wellkjz/student_task_manager.git
```

2. Update `src/main/resources/application.yml` with your PostgreSQL credentials

3. Run the application
```bash
   ./mvnw spring-boot:run
```

4. Open Swagger UI
http://localhost:8080/swagger-ui.html

## How to Test

1. Register via `POST /auth/register`
2. Login via `POST /auth/login` — copy the token
3. Click **Authorize** in Swagger UI, enter `Bearer YOUR_TOKEN`
4. Use any `/tasks` endpoint
