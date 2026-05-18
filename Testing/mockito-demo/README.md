# Mockito Demo Project

A comprehensive demonstration of Mockito mocking framework integrated with JUnit 5, showcasing unit testing with mock objects and dependency isolation.

## 📋 Table of Contents
- [Overview](#overview)
- [Project Structure](#project-structure)
- [Key Concepts](#key-concepts)
- [Running Tests](#running-tests)
- [Mock Patterns](#mock-patterns)
- [Advanced Techniques](#advanced-techniques)

## Overview

This project demonstrates Mockito, the most popular mocking framework for Java. It shows how to test classes with dependencies by creating mock objects, making your tests fast, isolated, and reliable.

### What is Mocking?

**Without Mocking:**
```java
// UserService depends on actual database and email server
UserService service = new UserService(
    new DatabaseRepository(),  // Needs real database
    new SmtpEmailService()     // Sends real emails
);
```

**With Mocking:**
```java
// UserService uses mock objects - no real database or emails
@Mock UserRepository mockRepo;
@Mock EmailService mockEmail;
@InjectMocks UserService service;  // Uses mocks automatically
```

### Benefits of Mocking
- ✅ Fast tests (no database/network calls)
- ✅ Isolated tests (test one class at a time)
- ✅ Reliable tests (no external dependencies)
- ✅ Test error scenarios easily

## Project Structure

```
mockito-demo/
├── pom.xml
├── README.md
├── MOCKITO_FEATURES.md            # Complete features reference
└── src/
    ├── main/java/com/example/
    │   ├── User.java                  # Entity class
    │   ├── UserRepository.java        # Interface (will be mocked)
    │   ├── EmailService.java          # Interface (will be mocked)
    │   ├── UserService.java           # Class under test
    │   ├── Order.java                 # Order entity
    │   ├── OrderService.java          # Complex service
    │   ├── PaymentProcessor.java      # Payment interface
    │   ├── InventoryService.java      # Inventory interface
    │   └── NotificationService.java   # Notification interface
    └── test/java/com/example/
        ├── UserServiceTest.java       # Basic mocking
        ├── AdvancedMockitoTest.java   # 19 advanced patterns
        └── SpyExampleTest.java        # Complete @Spy usage
```

## Key Concepts

### Mockito Annotations

| Annotation | Purpose | Example |
|------------|---------|---------|
| `@Mock` | Create a mock object | `@Mock UserRepository repo;` |
| `@InjectMocks` | Inject mocks into this object | `@InjectMocks UserService service;` |
| `@ExtendWith(MockitoExtension.class)` | Enable Mockito for JUnit 5 | Class-level annotation |
| `@Captor` | Capture method arguments | `@Captor ArgumentCaptor<String> captor;` |

### Core Methods

| Method | Purpose |
|--------|---------|
| `when().thenReturn()` | Stub method to return value |
| `when().thenThrow()` | Stub method to throw exception |
| `verify()` | Verify method was called |
| `never()` | Verify method was NOT called |
| `times(n)` | Verify method called n times |
| `any()`, `eq()` | Argument matchers |
| `doAnswer()` | Custom behavior for void methods |

## Running Tests

### Using Maven

```bash
# Run all tests
mvn test

# Run with verbose output
mvn clean test -X

# Run specific test class
mvn test -Dtest=UserServiceTest

# Run specific test method
mvn test -Dtest=UserServiceTest#testGetUserById
```

### Expected Output

```
[INFO] Running com.example.UserServiceTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0

Results:
Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

## Mock Patterns

### 1. Basic Mocking and Stubbing

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private EmailService emailService;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    void testGetUserById() {
        // Arrange: Define mock behavior
        User testUser = new User(1L, "John", "john@example.com");
        when(userRepository.findById(1L)).thenReturn(testUser);
        
        // Act: Call the method under test
        User result = userService.getUserById(1L);
        
        // Assert: Verify the result
        assertNotNull(result);
        assertEquals("John", result.getName());
        
        // Verify: Check mock was called
        verify(userRepository, times(1)).findById(1L);
    }
}
```

**Key Points:**
- `@Mock` creates fake objects
- `@InjectMocks` automatically injects mocks into `UserService`
- `when().thenReturn()` defines what mock should return
- `verify()` confirms the mock method was called

### 2. Verification Modes

```java
@Test
void testMultipleInteractions() {
    when(userRepository.findById(1L)).thenReturn(testUser);
    
    // Call method multiple times
    userService.getUserById(1L);
    userService.getUserById(1L);
    userService.getUserById(1L);
    
    // Different verification modes
    verify(userRepository, times(3)).findById(1L);        // Exactly 3 times
    verify(userRepository, atLeast(2)).findById(1L);      // At least 2 times
    verify(userRepository, atMost(5)).findById(1L);       // At most 5 times
    verify(userRepository, never()).delete(anyLong());     // Never called
}
```

### 3. Argument Matchers

```java
@Test
void testCreateUser() {
    // Call the method
    userService.createUser(testUser);
    
    // Verify with specific argument
    verify(userRepository).save(testUser);
    
    // Verify with matchers
    verify(emailService).sendEmail(
        eq("john@example.com"),    // Exact match
        eq("Welcome"),              // Exact match
        anyString()                 // Any string
    );
}
```

**Common Matchers:**
- `any()` / `any(Class.class)` - Any argument
- `anyString()`, `anyInt()`, `anyLong()` - Any primitive/String
- `eq(value)` - Exact match
- `isNull()`, `isNotNull()` - Null checks
- `contains("text")` - String contains

**⚠️ Important:** When using matchers, use them for ALL arguments:
```java
// ❌ Wrong - mixing matcher and literal
verify(emailService).sendEmail("john@example.com", anyString());

// ✅ Correct - all matchers
verify(emailService).sendEmail(eq("john@example.com"), anyString());
```

### 4. Argument Captors

```java
@Test
void testCreateUserWithArgumentCaptor() {
    // Create captors
    ArgumentCaptor<String> emailCaptor = 
        ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> subjectCaptor = 
        ArgumentCaptor.forClass(String.class);
    
    // Execute method
    userService.createUser(testUser);
    
    // Capture arguments
    verify(emailService).sendEmail(
        emailCaptor.capture(),
        subjectCaptor.capture(),
        anyString()
    );
    
    // Assert on captured values
    assertEquals("john@example.com", emailCaptor.getValue());
    assertEquals("Welcome", subjectCaptor.getValue());
}
```

**Use Cases:**
- Verify exact argument values
- Complex object verification
- Multiple method calls

### 5. Testing Exceptions

```java
@Test
void testCreateUserWithEmptyName() {
    // Arrange
    User invalidUser = new User(2L, "", "test@example.com");
    
    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> {
        userService.createUser(invalidUser);
    });
    
    // Verify dependencies were NOT called
    verify(userRepository, never()).save(any(User.class));
    verify(emailService, never()).sendEmail(
        anyString(), 
        anyString(), 
        anyString()
    );
}
```

### 6. Void Methods with doAnswer

```java
@Test
void testWithDoAnswer() {
    // Custom behavior: set ID when save is called
    doAnswer(invocation -> {
        User user = invocation.getArgument(0);
        user.setId(100L);
        return null;  // void method
    }).when(userRepository).save(any(User.class));
    
    User newUser = new User(null, "Jane", "jane@example.com");
    userService.createUser(newUser);
    
    // Verify ID was set
    assertEquals(100L, newUser.getId());
}
```

**Use Cases:**
- Simulate side effects
- Modify method arguments
- Custom logic for mocks

### 7. Exception Stubbing

```java
@Test
void testRepositoryThrowsException() {
    // Stub to throw exception
    when(userRepository.findById(99L))
        .thenThrow(new RuntimeException("User not found"));
    
    // Verify exception handling
    assertThrows(RuntimeException.class, () -> {
        userService.getUserById(99L);
    });
}
```

## Advanced Techniques

### Chaining Method Calls

```java
when(userRepository.findById(1L))
    .thenReturn(testUser)         // First call
    .thenReturn(null)             // Second call
    .thenThrow(new RuntimeException());  // Third call
```

### Spy Objects (Partial Mocking)

```java
@Spy
private UserService spyService;

@Test
void testSpy() {
    // Real method is called
    spyService.getUserById(1L);
    
    // But you can still verify
    verify(spyService).getUserById(1L);
}
```

### Mock Reset

```java
@Test
void testMockReset() {
    when(userRepository.findById(1L)).thenReturn(testUser);
    userService.getUserById(1L);
    
    // Reset all interactions
    reset(userRepository);
    
    // Previous stub is cleared
    when(userRepository.findById(1L)).thenReturn(null);
}
```

### InOrder Verification

```java
@Test
void testMethodCallOrder() {
    userService.deleteUser(1L);
    
    // Verify specific order
    InOrder inOrder = inOrder(userRepository, emailService);
    inOrder.verify(userRepository).existsById(1L);
    inOrder.verify(userRepository).findById(1L);
    inOrder.verify(userRepository).delete(1L);
    inOrder.verify(emailService).sendEmail(anyString(), anyString(), anyString());
}
```

## Complete Test Example

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Runs before each test
        testUser = new User(1L, "John Doe", "john@example.com");
    }

    @Test
    void testDeleteUser() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(testUser);
        
        // Act
        userService.deleteUser(1L);
        
        // Assert & Verify
        verify(userRepository).delete(1L);
        verify(emailService).sendEmail(
            eq("john@example.com"),
            eq("Account Deleted"),
            anyString()
        );
    }

    @Test
    void testDeleteNonExistentUser() {
        // Arrange
        when(userRepository.existsById(99L)).thenReturn(false);
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(99L);
        });
        
        // Verify delete was never called
        verify(userRepository, never()).delete(anyLong());
    }
}
```

## Best Practices

### ✅ Do's

1. **Mock External Dependencies**
   - Databases
   - External APIs
   - File systems
   - Email services

2. **Use @InjectMocks for Class Under Test**
   ```java
   @InjectMocks
   private UserService userService;  // Class being tested
   ```

3. **Verify Important Interactions**
   ```java
   verify(emailService).sendEmail(...);
   ```

4. **Test Edge Cases**
   - Null values
   - Empty collections
   - Exceptions

### ❌ Don'ts

1. **Don't Mock Everything**
   - Don't mock simple objects (String, Integer, DTOs)
   - Don't mock the class under test

2. **Don't Over-Verify**
   ```java
   // ❌ Too much verification
   verify(repo, times(1)).findById(1L);
   verify(repo, atLeast(1)).findById(1L);
   verify(repo, atMost(5)).findById(1L);
   
   // ✅ Just enough
   verify(repo).findById(1L);
   ```

3. **Don't Test Mock Behavior**
   ```java
   // ❌ Testing the mock, not your code
   when(repo.findById(1L)).thenReturn(user);
   assertEquals(user, repo.findById(1L));
   
   // ✅ Test your service logic
   when(repo.findById(1L)).thenReturn(user);
   User result = userService.getUserById(1L);
   assertEquals(user, result);
   ```

## Common Errors and Solutions

### Error: UnnecessaryStubbingException

```java
// Problem: Stubbed but never used
when(userRepository.findById(1L)).thenReturn(testUser);
// Method never called in test

// Solution: Remove unused stub or use lenient()
lenient().when(userRepository.findById(1L)).thenReturn(testUser);
```

### Error: MockitoAnnotations not initialized

```java
// Problem: Missing extension
class UserServiceTest { }

// Solution: Add extension
@ExtendWith(MockitoExtension.class)
class UserServiceTest { }
```

### Error: Wanted but not invoked

```java
// Problem: Method not called
verify(emailService).sendEmail("wrong@email.com", ...);

// Solution: Check actual vs expected arguments
// Use argument captors to see actual values
```

## Testing Strategies

### AAA Pattern (Arrange-Act-Assert)

```java
@Test
void testExample() {
    // Arrange: Setup test data and mocks
    when(userRepository.findById(1L)).thenReturn(testUser);
    
    // Act: Execute the method under test
    User result = userService.getUserById(1L);
    
    // Assert: Verify results and interactions
    assertNotNull(result);
    verify(userRepository).findById(1L);
}
```

### Given-When-Then (BDD Style)

```java
@Test
void shouldSendEmailWhenUserIsCreated() {
    // Given
    User newUser = new User(null, "Jane", "jane@example.com");
    
    // When
    userService.createUser(newUser);
    
    // Then
    verify(emailService).sendEmail(
        eq("jane@example.com"),
        contains("Welcome"),
        anyString()
    );
}
```

## Further Reading

- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Mockito GitHub](https://github.com/mockito/mockito)
- [Baeldung Mockito Guide](https://www.baeldung.com/mockito-series)
- [Mockito Best Practices](https://www.baeldung.com/mockito-annotations)

## Next Steps

1. Practice mocking different types of dependencies
2. Explore **springboot-testing-demo** for `@MockBean` in Spring
3. Learn about integration testing vs unit testing
4. Study test-driven development (TDD)

---

**Happy Mocking! 🎭**
