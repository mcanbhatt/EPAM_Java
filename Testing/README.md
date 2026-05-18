# Java Testing Framework Examples

This repository contains four comprehensive Java testing projects demonstrating different testing frameworks and approaches commonly used in enterprise Java development.

## 📁 Projects Overview

| Project | Framework | Description | Key Features |
|---------|-----------|-------------|--------------|
| [junit4-demo](./junit4-demo/) | JUnit 4 | Legacy testing framework | Annotations, lifecycle methods, assertions |
| [junit5-demo](./junit5-demo/) | JUnit 5 (Jupiter) | Modern testing framework | Parameterized tests, nested tests, display names |
| [mockito-demo](./mockito-demo/) | Mockito + JUnit 5 | Mocking framework | Mock objects, verification, argument capture |
| [springboot-testing-demo](./springboot-testing-demo/) | Spring Boot Test | Integration testing | REST API, JPA, MockMvc, integration tests |

## 🚀 Quick Start

### Prerequisites
- **Java Development Kit (JDK)**: 
  - Java 11+ for junit4-demo, junit5-demo, mockito-demo
  - Java 17+ for springboot-testing-demo
- **Maven**: 3.6 or higher
- **IDE** (optional): IntelliJ IDEA, Eclipse, or VS Code with Java extensions

### Running Tests

Navigate to any project directory and run:

```bash
cd <project-name>
mvn clean test
```

Example:
```bash
cd junit4-demo
mvn clean test
```

### Running Spring Boot Application

```bash
cd springboot-testing-demo
mvn spring-boot:run
```

Access the application at: http://localhost:8080

## 📚 Learning Path

### Beginner
1. **Start with junit4-demo** - Learn basic testing concepts
2. **Move to junit5-demo** - Understand modern JUnit features

### Intermediate
3. **Explore mockito-demo** - Master mocking and test isolation

### Advanced
4. **Study springboot-testing-demo** - Complete integration testing

## 🔍 What You'll Learn

### JUnit 4 (junit4-demo)
- Test lifecycle management (`@Before`, `@After`, `@BeforeClass`, `@AfterClass`)
- Basic assertions (`assertEquals`, `assertTrue`, `assertNotNull`)
- Exception testing (`expected` parameter)
- Timeout testing
- Ignoring tests (`@Ignore`)

### JUnit 5 (junit5-demo)
- Modern annotations (`@BeforeEach`, `@AfterEach`, `@BeforeAll`, `@AfterAll`)
- Display names for better readability
- Parameterized tests with multiple data sources
- Nested test classes for organization
- Repeated tests
- Advanced assertions and timeouts

### Mockito (mockito-demo)
- Creating mock objects (`@Mock`)
- Dependency injection in tests (`@InjectMocks`)
- Stubbing method calls (`when().thenReturn()`)
- Verifying interactions (`verify()`)
- Argument captors
- Custom behavior with `doAnswer()`

### Spring Boot Testing (springboot-testing-demo)
- Unit testing with `@WebMvcTest`
- Repository testing with `@DataJpaTest`
- Integration testing with `@SpringBootTest`
- REST API testing with MockMvc
- Database testing with H2
- Service layer mocking with `@MockBean`

## 🛠️ Technology Stack

- **Build Tool**: Maven
- **Testing Frameworks**: JUnit 4, JUnit 5 (Jupiter)
- **Mocking**: Mockito
- **Spring Boot**: 3.2.5
- **Database**: H2 (in-memory)
- **JPA**: Hibernate
- **Java Version**: 11-17

## 📖 Additional Resources

### Official Documentation
- [JUnit 4 Documentation](https://junit.org/junit4/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)

### Best Practices
- Write tests before or alongside production code (TDD/BDD)
- Keep tests independent and isolated
- Use meaningful test names
- Follow AAA pattern: Arrange, Act, Assert
- Mock external dependencies
- Test edge cases and error conditions

## 📝 Project Structure

```
Testing/
├── junit4-demo/
│   ├── src/
│   │   ├── main/java/com/example/
│   │   └── test/java/com/example/
│   ├── pom.xml
│   └── README.md
├── junit5-demo/
│   ├── src/
│   │   ├── main/java/com/example/
│   │   └── test/java/com/example/
│   ├── pom.xml
│   └── README.md
├── mockito-demo/
│   ├── src/
│   │   ├── main/java/com/example/
│   │   └── test/java/com/example/
│   ├── pom.xml
│   └── README.md
├── springboot-testing-demo/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/
│   │   │   └── resources/
│   │   └── test/java/com/example/
│   ├── pom.xml
│   └── README.md
└── README.md (this file)
```

## 🤝 Contributing

Feel free to enhance these examples by:
- Adding more test scenarios
- Implementing additional testing patterns
- Adding documentation improvements
- Creating example exercises

## 📄 License

This project is for educational purposes.

## 💡 Tips for Success

1. **Read the code**: Understand what the production code does before reading tests
2. **Run tests**: Execute tests to see them pass/fail
3. **Modify tests**: Change assertions to understand behavior
4. **Write your own**: Practice by adding new test cases
5. **Debug tests**: Use breakpoints to understand test execution flow

---

**Happy Testing! 🧪**
