# Advanced Mockito Features

This document covers advanced Mockito features including static method mocking, static field handling, and test ordering.

## 📋 Table of Contents
1. [Static Method Mocking](#static-method-mocking)
2. [Static Field Testing](#static-field-testing)
3. [Test Ordering](#test-ordering)
4. [Setup Requirements](#setup-requirements)

---

## 1. Static Method Mocking

### Requirements
```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-inline</artifactId>
    <version>5.2.0</version>
    <scope>test</scope>
</dependency>
```

### Basic Usage

```java
import static org.mockito.Mockito.*;

@Test
void testStaticMethod() {
    // Mock static method within try-with-resources
    try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {
        
        // Stub the static method
        LocalDateTime fixedDate = LocalDateTime.of(2024, 1, 1, 10, 0);
        mockedDateUtils.when(DateUtils::getCurrentDateTime).thenReturn(fixedDate);
        
        // Call static method - returns stubbed value
        LocalDateTime result = DateUtils.getCurrentDateTime();
        assertEquals(fixedDate, result);
        
        // Verify static method was called
        mockedDateUtils.verify(DateUtils::getCurrentDateTime);
    }
    // Static method returns to normal behavior after try block
}
```

### Key Patterns Demonstrated

| Pattern | Example | Use Case |
|---------|---------|----------|
| Basic mocking | `mockStatic(DateUtils.class)` | Mock entire static class |
| Method with params | `when(() -> DateUtils.format(any()))` | Stub methods with arguments |
| Partial mocking | `thenCallRealMethod()` | Mix mocked and real methods |
| Multiple methods | Mock multiple static methods | Complex utility classes |
| Verification | `mockedStatic.verify()` | Check static method calls |
| Exception stubbing | `thenThrow(exception)` | Test error handling |
| Consecutive calls | `thenReturn(val1).thenReturn(val2)` | Different values per call |

### Complete Example

```java
@Test
void testServiceWithStaticDependencies() {
    ReportService service = new ReportService();
    
    try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class);
         MockedStatic<ConfigManager> mockedConfig = mockStatic(ConfigManager.class)) {
        
        // Mock static methods from multiple classes
        LocalDateTime fixedDate = LocalDateTime.of(2024, 6, 15, 10, 0);
        mockedDateUtils.when(DateUtils::getCurrentDateTime).thenReturn(fixedDate);
        mockedDateUtils.when(() -> DateUtils.formatDate(any()))
                      .thenReturn("2024-06-15 10:00:00");
        
        mockedConfig.when(ConfigManager::getApplicationInfo)
                   .thenReturn("TestApp v1.0");
        
        // Test service method
        String report = service.generateReport("Test Report");
        
        // Verify
        assertTrue(report.contains("2024-06-15 10:00:00"));
        assertTrue(report.contains("TestApp"));
        
        mockedDateUtils.verify(DateUtils::getCurrentDateTime);
        mockedConfig.verify(ConfigManager::getApplicationInfo);
    }
}
```

### Important Notes

⚠️ **Always use try-with-resources**
- Ensures proper cleanup
- Prevents affecting other tests
- Required for correct behavior

⚠️ **Performance Impact**
- Static mocking is slower than regular mocking
- Use only when necessary
- Consider refactoring to avoid static dependencies

⚠️ **Thread Safety**
- MockedStatic is NOT thread-safe
- Don't use in parallel test execution
- Keep scope minimal

---

## 2. Static Field Testing

### Why Not Direct Mocking?

Mockito doesn't directly "mock" static fields. Instead, we:
1. Modify fields directly (if mutable)
2. Use reflection for private fields
3. Mock methods that use static fields
4. Design better code to avoid static fields

### Direct Modification

```java
@Test
void testWithModifiedStaticField() {
    // Save original
    String original = ConfigManager.APPLICATION_NAME;
    
    try {
        // Modify static field
        ConfigManager.APPLICATION_NAME = "TestApp";
        
        // Test
        assertEquals("TestApp", ConfigManager.APPLICATION_NAME);
        assertEquals("TestApp v1.0.0", ConfigManager.getApplicationInfo());
        
    } finally {
        // Restore original
        ConfigManager.APPLICATION_NAME = original;
    }
}
```

### Using Reflection

```java
@Test
void testWithReflection() throws Exception {
    // Access private static field
    Field field = ConfigManager.class.getDeclaredField("DEBUG_MODE");
    field.setAccessible(true);
    
    // Save original
    boolean original = (boolean) field.get(null);
    
    try {
        // Modify
        field.set(null, true);
        assertTrue(ConfigManager.isDebugEnabled());
        
    } finally {
        // Restore
        field.set(null, original);
    }
}
```

### Helper Method Pattern

```java
class TestBase {
    private void setStaticField(Class<?> clazz, String fieldName, Object value) 
            throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(null, value);
    }
}

@Test
void testWithHelper() throws Exception {
    setStaticField(ConfigManager.class, "MAX_CONNECTIONS", 50);
    assertEquals(50, ConfigManager.MAX_CONNECTIONS);
}
```

### Best Practices

```java
class StaticFieldTest {
    
    private String originalAppName;
    private int originalMaxConnections;
    
    @BeforeEach
    void saveOriginalValues() {
        // Save all static fields that will be modified
        originalAppName = ConfigManager.APPLICATION_NAME;
        originalMaxConnections = ConfigManager.MAX_CONNECTIONS;
    }
    
    @AfterEach
    void restoreOriginalValues() {
        // Restore to prevent test pollution
        ConfigManager.APPLICATION_NAME = originalAppName;
        ConfigManager.MAX_CONNECTIONS = originalMaxConnections;
    }
    
    @Test
    void testWithModifiedFields() {
        ConfigManager.APPLICATION_NAME = "TestApp";
        ConfigManager.MAX_CONNECTIONS = 10;
        
        // Your test code
        assertTrue(ConfigManager.canAcceptConnection(5));
    }
}
```

### Static Field Limitations

❌ **Cannot Mock:**
- Static final fields (without unsafe operations)
- Immutable static objects
- Enum constants

✅ **Can Modify:**
- Non-final static fields
- Public static fields
- Private static fields (via reflection)

### Design Alternatives

Instead of static fields, consider:

```java
// ❌ Static field - hard to test
public class Config {
    public static String APP_NAME = "MyApp";
}

// ✅ Injectable configuration - easy to test
public class AppConfig {
    private String appName;
    
    public AppConfig(String appName) {
        this.appName = appName;
    }
    
    public String getAppName() {
        return appName;
    }
}

// ✅ Spring-style configuration
@Configuration
public class AppConfig {
    @Value("${app.name}")
    private String appName;
}
```

---

## 3. Test Ordering

### Why Order Tests?

**✅ Use ordering for:**
- Integration tests with dependencies
- End-to-end scenario tests
- Performance tests with warm-up
- Database migration tests
- Demo/documentation tests

**❌ Avoid ordering for:**
- Unit tests (should be independent)
- Tests that can run in parallel
- Regular business logic tests

### Method 1: @Order Annotation

```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderedTest {
    
    @Test
    @Order(1)
    void firstTest() {
        System.out.println("First");
    }
    
    @Test
    @Order(2)
    void secondTest() {
        System.out.println("Second");
    }
    
    @Test
    @Order(3)
    void thirdTest() {
        System.out.println("Third");
    }
}
```

### Method 2: Method Name

```java
@TestMethodOrder(MethodOrderer.MethodName.class)
class AlphabeticalTest {
    
    @Test
    void test_A_First() {
        // Runs first
    }
    
    @Test
    void test_B_Second() {
        // Runs second
    }
    
    @Test
    void test_C_Third() {
        // Runs third
    }
}
```

### Method 3: Display Name

```java
@TestMethodOrder(MethodOrderer.DisplayName.class)
class DisplayNameOrderTest {
    
    @Test
    @DisplayName("A - First Test")
    void method3() { }
    
    @Test
    @DisplayName("B - Second Test")
    void method1() { }
    
    @Test
    @DisplayName("C - Third Test")
    void method2() { }
}
```

### Method 4: Random Order

```java
@TestMethodOrder(MethodOrderer.Random.class)
class RandomOrderTest {
    // Tests run in random order each time
    // Good for detecting test dependencies
}
```

### Real-World Example: User Lifecycle

```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserLifecycleTest {
    
    private static User testUser;
    
    @Test
    @Order(1)
    @DisplayName("1. Create user")
    void createUser() {
        testUser = new User("John", "john@example.com");
        assertNotNull(testUser);
    }
    
    @Test
    @Order(2)
    @DisplayName("2. Activate user")
    void activateUser() {
        assertNotNull(testUser, "User should be created first");
        testUser.setActive(true);
        assertTrue(testUser.isActive());
    }
    
    @Test
    @Order(3)
    @DisplayName("3. Update user")
    void updateUser() {
        testUser.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", testUser.getEmail());
    }
    
    @Test
    @Order(4)
    @DisplayName("4. Delete user")
    void deleteUser() {
        // Cleanup
        testUser = null;
    }
}
```

### Integration Test Scenario

```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderProcessingTest {
    
    private static Order order;
    
    @Test
    @Order(1)
    void checkInventory() {
        // Step 1: Verify items in stock
    }
    
    @Test
    @Order(2)
    void createOrder() {
        order = new Order(userId, items);
        assertEquals("PENDING", order.getStatus());
    }
    
    @Test
    @Order(3)
    void processPayment() {
        // Step 3: Process payment
        order.setStatus("PAID");
    }
    
    @Test
    @Order(4)
    void shipOrder() {
        // Step 4: Ship order
        order.setStatus("SHIPPED");
    }
}
```

### Global Configuration

Create `src/test/resources/junit-platform.properties`:

```properties
# Set default test ordering for all tests
junit.jupiter.testmethod.order.default = \
    org.junit.jupiter.api.MethodOrderer$OrderAnnotation

# Or use random to detect dependencies
junit.jupiter.testmethod.order.default = \
    org.junit.jupiter.api.MethodOrderer$Random
```

### Available Orderers

| Orderer | Use Case | Pros | Cons |
|---------|----------|------|------|
| `OrderAnnotation` | Explicit control | Clear, readable | Manual numbering |
| `MethodName` | Simple alphabetical | No annotations needed | Awkward naming |
| `DisplayName` | Readable ordering | Nice display names | Requires @DisplayName |
| `Random` | Detect dependencies | Finds coupling | Non-deterministic |
| Custom | Special requirements | Full control | More complex |

---

## 4. Setup Requirements

### Maven Dependencies

```xml
<dependencies>
    <!-- JUnit 5 -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.2</version>
        <scope>test</scope>
    </dependency>
    
    <!-- Mockito Core -->
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <version>5.11.0</version>
        <scope>test</scope>
    </dependency>
    
    <!-- Mockito JUnit 5 Integration -->
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-junit-jupiter</artifactId>
        <version>5.11.0</version>
        <scope>test</scope>
    </dependency>
    
    <!-- For Static Method Mocking -->
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-inline</artifactId>
        <version>5.2.0</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### Import Statements

```java
// JUnit 5
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

// Mockito
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

// For static mocking
import org.mockito.MockedStatic;

// For reflection
import java.lang.reflect.Field;
```

---

## Complete Example: Combining All Features

```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Complete Advanced Testing Example")
class CompleteAdvancedTest {
    
    private static ReportService service;
    private String originalAppName;
    
    @BeforeAll
    static void setupClass() {
        service = new ReportService();
    }
    
    @BeforeEach
    void saveState() {
        originalAppName = ConfigManager.APPLICATION_NAME;
    }
    
    @AfterEach
    void restoreState() {
        ConfigManager.APPLICATION_NAME = originalAppName;
    }
    
    @Test
    @Order(1)
    @DisplayName("Test with static method mocking")
    void testWithStaticMethodMock() {
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {
            LocalDateTime fixedDate = LocalDateTime.of(2024, 1, 1, 10, 0);
            mockedDateUtils.when(DateUtils::getCurrentDateTime).thenReturn(fixedDate);
            
            // Test code
        }
    }
    
    @Test
    @Order(2)
    @DisplayName("Test with static field modification")
    void testWithStaticFieldModification() {
        ConfigManager.APPLICATION_NAME = "TestApp";
        
        String info = service.getSystemInfo();
        assertTrue(info.contains("TestApp"));
    }
    
    @Test
    @Order(3)
    @DisplayName("Test combining both techniques")
    void testCombined() {
        ConfigManager.DEBUG_MODE = true;
        
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {
            mockedDateUtils.when(DateUtils::getCurrentDateTime)
                          .thenReturn(LocalDateTime.now());
            
            // Test with both static field and static method mocking
            String report = service.generateReport("Test");
            assertNotNull(report);
        }
    }
}
```

---

## Best Practices Summary

### Static Method Mocking
✅ Always use try-with-resources  
✅ Keep mock scope minimal  
✅ Consider refactoring instead  
❌ Don't share MockedStatic between tests  
❌ Avoid in parallel test execution  

### Static Field Testing
✅ Always save and restore values  
✅ Use @BeforeEach/@AfterEach  
✅ Consider dependency injection instead  
❌ Don't modify final fields (without good reason)  
❌ Avoid parallel execution with static fields  

### Test Ordering
✅ Use for integration/scenario tests  
✅ Document why ordering is needed  
✅ Use @DisplayName for clarity  
❌ Don't order unit tests  
❌ Tests should be independent when possible  

---

## Further Reading

- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Mockito Static Mocking](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#static_mocks)
- [JUnit 5 Test Order](https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-execution-order)

---

**See test files for complete working examples!**
