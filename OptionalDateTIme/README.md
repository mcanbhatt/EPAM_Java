# Java Optional and Date Time API - Spring Boot Practice Project

This Spring Boot application provides comprehensive examples and demonstrations of **Java Optional** and **Java Date Time API** with all methods, real-world scenarios, and REST API endpoints.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Optional API Examples](#optional-api-examples)
- [Date Time API Examples](#date-time-api-examples)
- [Testing the APIs](#testing-the-apis)

## Features

### Java Optional Coverage
- All Optional creation methods (`of`, `ofNullable`, `empty`)
- Presence checking (`isPresent`, `isEmpty`)
- Conditional actions (`ifPresent`, `ifPresentOrElse`)
- Default value methods (`orElse`, `orElseGet`, `orElseThrow`)
- Transformation methods (`map`, `flatMap`, `filter`)
- Stream integration (`stream`, `or`)
- Real-world scenarios (safe navigation, chaining operations)

### Java Date Time API Coverage
- **LocalDate**: All methods for date manipulation, parsing, formatting, and comparison
- **LocalTime**: Time-specific operations and transformations
- **LocalDateTime**: Combined date-time operations
- **ZonedDateTime**: Timezone-aware date-time handling
- **Instant**: Machine-readable timestamps
- **Duration**: Time-based amounts (hours, minutes, seconds)
- **Period**: Date-based amounts (years, months, days)
- **DateTimeFormatter**: Custom and predefined formatting patterns
- **Real-world scenarios**: Age calculation, business days, timezone meetings, deadlines

## Technologies Used
- Java 17
- Spring Boot 3.2.5
- Maven
- Lombok
- Spring Web

## Project Structure
```
OptionalDateTIme/
├── src/
│   ├── main/
│   │   ├── java/com/epam/practice/
│   │   │   ├── OptionalDateTimeApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── OptionalController.java
│   │   │   │   └── DateTimeController.java
│   │   │   ├── service/
│   │   │   │   ├── OptionalService.java
│   │   │   │   └── DateTimeService.java
│   │   │   └── model/
│   │   │       ├── User.java
│   │   │       └── Address.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Running the Application

1. **Navigate to the project directory:**
   ```bash
   cd C:\Users\NaveenBhatt\Project\EpamPractice\OptionalDateTIme
   ```

2. **Build the project:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application:**
   - Application runs on: `http://localhost:8080`
   - Optional API Methods: `http://localhost:8080/api/optional/methods`
   - DateTime API Methods: `http://localhost:8080/api/datetime/methods`

## API Endpoints

### Optional API Endpoints

#### List All Methods
```
GET http://localhost:8080/api/optional/methods
```

#### Core Optional Methods
1. **Optional.of()** - Creates Optional with non-null value
   ```
   GET http://localhost:8080/api/optional/demo/of
   ```

2. **Optional.ofNullable()** - Creates Optional that can handle null
   ```
   GET http://localhost:8080/api/optional/demo/ofNullable
   ```

3. **Optional.empty()** - Creates empty Optional
   ```
   GET http://localhost:8080/api/optional/demo/empty
   ```

4. **isPresent()** - Check if value exists
   ```
   GET http://localhost:8080/api/optional/demo/isPresent/{userId}
   Example: http://localhost:8080/api/optional/demo/isPresent/1
   ```

5. **isEmpty()** - Check if Optional is empty (Java 11+)
   ```
   GET http://localhost:8080/api/optional/demo/isEmpty/{userId}
   Example: http://localhost:8080/api/optional/demo/isEmpty/99
   ```

6. **ifPresent()** - Execute action if value present
   ```
   GET http://localhost:8080/api/optional/demo/ifPresent/{userId}
   ```

7. **ifPresentOrElse()** - Execute action if present, else execute empty action
   ```
   GET http://localhost:8080/api/optional/demo/ifPresentOrElse/{userId}
   ```

8. **orElse()** - Return value or default
   ```
   GET http://localhost:8080/api/optional/demo/orElse/{userId}
   ```

9. **orElseGet()** - Return value or compute default lazily
   ```
   GET http://localhost:8080/api/optional/demo/orElseGet/{userId}
   ```

10. **orElseThrow()** - Throw exception if empty
    ```
    GET http://localhost:8080/api/optional/demo/orElseThrow/{userId}
    ```

11. **map()** - Transform value if present
    ```
    GET http://localhost:8080/api/optional/demo/map/{userId}
    ```

12. **flatMap()** - Transform value to Optional (avoid nested Optionals)
    ```
    GET http://localhost:8080/api/optional/demo/flatMap/{userId}
    ```

13. **filter()** - Filter value based on predicate
    ```
    GET http://localhost:8080/api/optional/demo/filter/{userId}
    ```

14. **or()** - Return alternative Optional if empty (Java 9+)
    ```
    GET http://localhost:8080/api/optional/demo/or/{userId}
    ```

15. **stream()** - Convert Optional to Stream (Java 9+)
    ```
    GET http://localhost:8080/api/optional/demo/stream
    ```

#### Real-world Scenarios
```
GET http://localhost:8080/api/optional/scenario/formatted-info/{userId}
GET http://localhost:8080/api/optional/scenario/phone/{userId}?defaultPhone=000-000-0000
GET http://localhost:8080/api/optional/scenario/contact/{userId}
GET http://localhost:8080/api/optional/users
```

### Date Time API Endpoints

#### List All Methods
```
GET http://localhost:8080/api/datetime/methods
```

#### LocalDate Methods
```
GET http://localhost:8080/api/datetime/localdate/now
GET http://localhost:8080/api/datetime/localdate/of
GET http://localhost:8080/api/datetime/localdate/parse
GET http://localhost:8080/api/datetime/localdate/manipulation
GET http://localhost:8080/api/datetime/localdate/with
GET http://localhost:8080/api/datetime/localdate/adjusters
GET http://localhost:8080/api/datetime/localdate/comparison
GET http://localhost:8080/api/datetime/localdate/query
```

#### LocalTime Methods
```
GET http://localhost:8080/api/datetime/localtime/basic
GET http://localhost:8080/api/datetime/localtime/manipulation
GET http://localhost:8080/api/datetime/localtime/with
GET http://localhost:8080/api/datetime/localtime/query
```

#### LocalDateTime Methods
```
GET http://localhost:8080/api/datetime/localdatetime/basic
GET http://localhost:8080/api/datetime/localdatetime/manipulation
GET http://localhost:8080/api/datetime/localdatetime/conversion
```

#### ZonedDateTime Methods
```
GET http://localhost:8080/api/datetime/zoneddatetime/basic
GET http://localhost:8080/api/datetime/zoneddatetime/conversion
GET http://localhost:8080/api/datetime/zoneddatetime/zones
```

#### Instant Methods
```
GET http://localhost:8080/api/datetime/instant/basic
GET http://localhost:8080/api/datetime/instant/operations
GET http://localhost:8080/api/datetime/instant/conversion
```

#### Duration Methods
```
GET http://localhost:8080/api/datetime/duration/basic
GET http://localhost:8080/api/datetime/duration/between
GET http://localhost:8080/api/datetime/duration/arithmetic
```

#### Period Methods
```
GET http://localhost:8080/api/datetime/period/basic
GET http://localhost:8080/api/datetime/period/between
GET http://localhost:8080/api/datetime/period/arithmetic
```

#### DateTimeFormatter Methods
```
GET http://localhost:8080/api/datetime/formatter/predefined
GET http://localhost:8080/api/datetime/formatter/custom
GET http://localhost:8080/api/datetime/formatter/parsing
```

#### Real-world Scenarios
```
GET http://localhost:8080/api/datetime/scenario/age?birthDate=1990-01-15
GET http://localhost:8080/api/datetime/scenario/days-until?eventDate=2024-12-25
GET http://localhost:8080/api/datetime/scenario/business-days?start=2024-06-01&end=2024-06-30
GET http://localhost:8080/api/datetime/scenario/meeting?hour=14&minute=30
GET http://localhost:8080/api/datetime/scenario/deadline?deadline=2024-12-31T23:59:59
```

## Testing the APIs

### Using Browser
Simply open any GET endpoint in your browser:
```
http://localhost:8080/api/optional/methods
http://localhost:8080/api/datetime/methods
```

### Using cURL
```bash
# Optional API
curl http://localhost:8080/api/optional/demo/isPresent/1
curl http://localhost:8080/api/optional/demo/map/2

# DateTime API
curl http://localhost:8080/api/datetime/localdate/now
curl "http://localhost:8080/api/datetime/scenario/age?birthDate=1990-01-15"
```

### Using Postman
1. Import the base URL: `http://localhost:8080`
2. Create requests for each endpoint
3. Send GET requests and observe responses

## Sample Data

The application includes sample users for Optional demonstrations:

| User ID | Name | Email | Phone | Address |
|---------|------|-------|-------|---------|
| 1 | John Doe | john@example.com | 123-456-7890 | New York |
| 2 | Jane Smith | jane@example.com | null | Los Angeles |
| 3 | Bob Johnson | null | 555-555-5555 | null |

Use these IDs to test Optional methods with different scenarios (present values, null values, missing data).

## Key Learnings

### Optional Best Practices
1. Use `Optional.ofNullable()` for potentially null values
2. Prefer `orElseGet()` over `orElse()` for expensive operations
3. Use `map()` and `flatMap()` for safe transformations
4. Avoid `get()` without checking `isPresent()`
5. Chain operations for cleaner code

### Date Time API Best Practices
1. Use `LocalDate` for dates without time
2. Use `LocalDateTime` for date-time without timezone
3. Use `ZonedDateTime` for timezone-aware operations
4. Use `Instant` for machine timestamps
5. Use `Duration` for time-based amounts (hours/minutes)
6. Use `Period` for date-based amounts (years/months/days)
7. Always format dates for display using `DateTimeFormatter`

## Contributing
Feel free to extend this project with additional examples and scenarios!

## License
This project is for educational purposes as part of EPAM practice.
