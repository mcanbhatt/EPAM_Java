# Spring Boot Practice Project

A Spring Boot web application with Spring Data JPA and Spring Security.

## Technologies Used

- Spring Boot 3.2.5
- Spring Web
- Spring Data JPA
- Spring Security
- H2 Database (in-memory)
- Lombok
- Java 17

## How to Run

1. Navigate to the project directory
2. Run the application using Maven:
   ```bash
   mvn spring-boot:run
   ```
3. The application will start on `http://localhost:8080`

## Default Security

Spring Security is enabled with default configuration:
- Default username: `user`
- Password: Check console logs for auto-generated password, or set it in `application.properties`

## H2 Console

Access H2 database console at: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (empty)

## Endpoints

- `GET /` - Home endpoint
- `GET /api/hello` - Secured API endpoint
