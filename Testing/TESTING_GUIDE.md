# Complete Java Testing Guide

A comprehensive guide to testing in Java, covering JUnit 4, JUnit 5, Mockito, and Spring Boot testing.

## 📚 Projects in This Repository

### 1. junit4-demo
**Focus:** Legacy JUnit 4 framework basics

**Key Topics:**
- Basic annotations (@Test, @Before, @After, @BeforeClass, @AfterClass)
- Assertions (assertEquals, assertTrue, assertFalse)
- Exception testing with expected parameter
- Timeout testing
- Ignoring tests with @Ignore

**Best For:** Understanding testing fundamentals and legacy code

---

### 2. junit5-demo
**Focus:** Modern JUnit 5 (Jupiter) features

**Key Topics:**
- Modern annotations (@BeforeEach, @AfterEach, @DisplayName)
- Parameterized tests (@ParameterizedTest, @ValueSource, @CsvSource)
- Nested test classes (@Nested)
- Repeated tests (@RepeatedTest)
- Advanced assertions (assertThrows, assertTimeout, assertAll)
- Dynamic tests

**Best For:** Modern Java testing and new projects

---

### 3. mockito-demo
**Focus:** Comprehensive Mockito mocking framework

**Key Topics:**
- Basic mocking (@Mock, @InjectMocks, @Spy, @Captor)
- Stubbing (when().thenReturn(), doReturn(), doThrow())
- Verification (verify(), times(), never(), atLeast(), atMost())
- Argument captors and matchers
- InOrder verification
- BDD style (given/when/then)
- Answer and custom behavior
- Spy vs Mock comparison
- 19 advanced Mockito patterns

**Test Files:**
- `UserServiceTest.java` - Basic patterns
- `AdvancedMockitoTest.java` - 19 comprehensive patterns
- `SpyExampleTest.java` - Complete @Spy usage

**Best For:** Unit testing with dependencies and isolation

---

### 4. springboot-testing-demo
**Focus:** Complete Spring Boot testing strategies

**Key Topics:**
- Repository testing (@DataJpaTest, TestEntityManager)
- Service testing (Unit tests with @Mock)
- Controller testing (@WebMvcTest, MockMvc)
- Integration testing (@SpringBootTest, @AutoConfigureMockMvc)
- REST API testing
- Database testing with H2
- @MockBean for Spring context

**Test Layers:**
- `ProductRepositoryTest` - JPA layer testing
- `ProductServiceTest` - Business logic with mocks
- `ProductControllerTest` - REST endpoints
- `ProductIntegrationTest` - End-to-end flows

**Best For:** Testing Spring Boot applications

---

## 🎯 Learning Path

### Beginner Path
```
1. junit4-demo (2-3 hours)
   ↓
2. junit5-demo (3-4 hours)
   ↓
3. mockito-demo basics (2-3 hours)
   - UserServiceTest.java only
```

### Intermediate Path
```
1. Review junit5-demo
   ↓
2. mockito-demo complete (4-5 hours)
   - All test files
   - MOCKITO_FEATURES.md reference
   ↓
3. springboot-testing-demo (3-4 hours)
   - Repository and Service layers
```

### Advanced Path
```
1. All mockito-demo patterns (5-6 hours)
   - AdvancedMockitoTest.java
   - SpyExampleTest.java
   ↓
2. springboot-testing-demo complete (5-6 hours)
   - All test layers
   - Integration tests
   ↓
3. Build your own test suite
```

---

## 📊 Testing Pyramid

```
       /\
      /  \  E2E Tests
     /____\  (Integration Tests - Few, Slow)
    /      \
   /  API   \ API/Controller Tests
  /  Tests  \  (Medium number, Medium speed)
 /__________\
/            \
/    Unit     \ Unit Tests
/    Tests     \ (Many, Fast)
/______________\
```

### Test Distribution
- **70%** Unit Tests (mockito-demo patterns)
- **20%** Integration Tests (controller/repository tests)
- **10%** End-to-End Tests (full integration tests)

---

## 🔧 Testing Strategies

### Unit Testing (Mockito)
**When:** Testing business logic in isolation
**Speed:** Fast (milliseconds)
**Dependencies:** All mocked

```java
@ExtendWith(MockitoExtension.class)
class ServiceTest {
    @Mock private Repository repo;
    @InjectMocks private Service service;
    
    @Test void test() {
        when(repo.find(1L)).thenReturn(entity);
        // Test service logic
    }
}
```

### Integration Testing (Spring Boot)
**When:** Testing layers together
**Speed:** Medium (seconds)
**Dependencies:** Some real, some mocked

```java
@WebMvcTest(Controller.class)
class ControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private Service service;
    
    @Test void test() throws Exception {
        mockMvc.perform(get("/api/endpoint"))
            .andExpect(status().isOk());
    }
}
```

### E2E Testing (Full Stack)
**When:** Testing complete user flows
**Speed:** Slow (seconds to minutes)
**Dependencies:** All real

```java
@SpringBootTest
@AutoConfigureMockMvc
class IntegrationTest {
    @Autowired private MockMvc mockMvc;
    // Real database, all components loaded
}
```

---

## 📖 Feature Comparison

### JUnit 4 vs JUnit 5

| Feature | JUnit 4 | JUnit 5 |
|---------|---------|---------|
| Annotations | `@Before` | `@BeforeEach` |
| | `@BeforeClass` | `@BeforeAll` |
| | `@Ignore` | `@Disabled` |
| Exception Testing | `@Test(expected=...)` | `assertThrows(...)` |
| Timeout | `@Test(timeout=1000)` | `@Timeout(1)` |
| Parameterized | `@RunWith(Parameterized)` | `@ParameterizedTest` |
| Display Names | Not available | `@DisplayName` |
| Nested Tests | Not available | `@Nested` |
| Repeated Tests | Not available | `@RepeatedTest` |

### Mock vs Spy

| Aspect | @Mock | @Spy |
|--------|-------|------|
| Object Type | Fake object | Real object |
| Default Behavior | Returns null/0/false | Calls real methods |
| Stubbing | Stub all methods | Stub selected methods |
| Performance | Fastest | Slightly slower |
| Use Case | Full isolation | Partial mocking |
| Recommendation | Prefer this | Use sparingly |

---

## 🎓 Key Concepts

### AAA Pattern (Arrange-Act-Assert)
```java
@Test
void test() {
    // Arrange: Setup test data
    User user = new User("John");
    when(repo.findById(1L)).thenReturn(user);
    
    // Act: Execute the method
    User result = service.getUser(1L);
    
    // Assert: Verify results
    assertEquals("John", result.getName());
    verify(repo).findById(1L);
}
```

### Given-When-Then (BDD)
```java
@Test
void shouldReturnUserWhenUserExists() {
    // Given
    given(repo.findById(1L)).willReturn(user);
    
    // When
    User result = service.getUser(1L);
    
    // Then
    then(repo).should().findById(1L);
    assertNotNull(result);
}
```

---

## 🚀 Quick Start Commands

### Run All Tests
```bash
cd <project-name>
mvn clean test
```

### Run Specific Test Class
```bash
mvn test -Dtest=UserServiceTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=UserServiceTest#testGetUserById
```

### Run Tests by Pattern
```bash
mvn test -Dtest="*ServiceTest"
```

### Generate Coverage Report
```bash
mvn clean test jacoco:report
# Open: target/site/jacoco/index.html
```

### Run Spring Boot Application
```bash
cd springboot-testing-demo
mvn spring-boot:run
```

---

## 📚 Complete Mockito Features

See `mockito-demo/MOCKITO_FEATURES.md` for comprehensive reference covering:

1. **Annotations:** @Mock, @Spy, @InjectMocks, @Captor
2. **Stubbing:** when().thenReturn(), doReturn(), doThrow(), doAnswer()
3. **Verification:** verify(), times(), never(), atLeast(), atMost()
4. **Matchers:** any(), eq(), argThat(), custom matchers
5. **Captors:** ArgumentCaptor usage and patterns
6. **Spy:** Complete @Spy vs @Mock comparison
7. **BDD:** given/when/then style
8. **Advanced:** InOrder, reset(), lenient(), timeout()

**19 comprehensive patterns demonstrated in AdvancedMockitoTest.java**

---

## 🏆 Best Practices

### ✅ DO

1. **Write tests first** (TDD)
2. **Test behavior, not implementation**
3. **Keep tests independent**
4. **Use meaningful test names**
   ```java
   testGetUser_WhenUserExists_ReturnsUser()
   ```
5. **One assertion per test** (when possible)
6. **Mock external dependencies**
7. **Use appropriate test annotations**
8. **Test edge cases and exceptions**
9. **Maintain test code quality**
10. **Run tests frequently**

### ❌ DON'T

1. **Don't test framework code**
2. **Don't share state between tests**
3. **Don't use sleep() in tests**
4. **Don't ignore failing tests**
5. **Don't mock everything** (mock only external dependencies)
6. **Don't test private methods directly**
7. **Don't write tests without assertions**
8. **Don't skip exception testing**
9. **Don't have side effects in tests**
10. **Don't use random data** (tests should be deterministic)

---

## 🔍 Testing Checklist

### Unit Test Checklist
- [ ] Test happy path
- [ ] Test edge cases (null, empty, boundary values)
- [ ] Test exceptions
- [ ] Verify all dependencies called correctly
- [ ] No external dependencies (all mocked)
- [ ] Tests run fast (< 1 second each)
- [ ] Tests are independent

### Integration Test Checklist
- [ ] Test component interactions
- [ ] Test with real database/services where appropriate
- [ ] Test HTTP endpoints (status, headers, body)
- [ ] Test JSON serialization/deserialization
- [ ] Clean test data before/after tests
- [ ] Use test profiles/configurations

### General Checklist
- [ ] Tests have meaningful names
- [ ] Tests follow AAA pattern
- [ ] Code coverage > 80%
- [ ] No commented-out tests
- [ ] Tests pass consistently
- [ ] Tests run in CI/CD pipeline

---

## 📦 Dependencies Required

### JUnit 4
```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>
```

### JUnit 5
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.2</version>
    <scope>test</scope>
</dependency>
```

### Mockito
```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.11.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>5.11.0</version>
    <scope>test</scope>
</dependency>
```

### Spring Boot Test
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

---

## 🌟 Advanced Topics

### TestContainers
Use real databases in tests via Docker containers.

### AssertJ
Fluent assertion library for more readable tests.

### REST Assured
Testing REST APIs with fluent syntax.

### WireMock
Mock external HTTP services.

### Awaitility
Testing asynchronous code.

### ArchUnit
Test architecture and design rules.

---

## 📖 Additional Resources

### Official Documentation
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)

### Books
- "Effective Unit Testing" by Lasse Koskela
- "Test Driven Development: By Example" by Kent Beck
- "Growing Object-Oriented Software, Guided by Tests" by Steve Freeman

### Online Courses
- Baeldung: [Spring Testing](https://www.baeldung.com/spring-testing)
- Baeldung: [Mockito Series](https://www.baeldung.com/mockito-series)

---

## 💡 Practice Exercises

### Beginner
1. Write tests for a Calculator class (junit4-demo style)
2. Create parameterized tests for a StringValidator (junit5-demo)
3. Mock a simple UserService with one dependency (mockito-demo)

### Intermediate
4. Test a complex OrderService with multiple dependencies
5. Write REST controller tests with MockMvc
6. Create integration tests for a CRUD API

### Advanced
7. Implement BDD-style tests using given/when/then
8. Create custom argument matchers
9. Test asynchronous operations with timeout verification
10. Build a complete test suite for a microservice

---

## 🎯 Conclusion

This repository provides a complete testing education path from basic JUnit 4 to advanced Spring Boot integration testing. Work through each project in order, practice the examples, and refer to the comprehensive documentation.

**Remember:** Testing is not just about code coverage - it's about confidence in your code!

---

**Happy Testing! 🧪**
