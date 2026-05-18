# JUnit 5 Demo Project

A comprehensive demonstration of JUnit 5 (Jupiter) testing framework with a StringProcessor application showcasing modern testing features.

## 📋 Table of Contents
- [Overview](#overview)
- [Project Structure](#project-structure)
- [Key Features](#key-features)
- [Running Tests](#running-tests)
- [Test Explanations](#test-explanations)
- [Advanced Features](#advanced-features)

## Overview

This project demonstrates JUnit 5 (Jupiter), the modern successor to JUnit 4. It includes a `StringProcessor` class with string manipulation methods and comprehensive test coverage using JUnit 5's advanced features.

### What's New in JUnit 5
- Lambda support for assertions
- Parameterized tests with multiple sources
- Nested test classes
- Display names for better readability
- Improved exception testing
- Better extension model

### What's Included
- **StringProcessor.java**: String manipulation utilities
- **StringProcessorTest.java**: Complete test suite with JUnit 5 features

## Project Structure

```
junit5-demo/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   └── java/
    │       └── com/example/
    │           └── StringProcessor.java
    └── test/
        └── java/
            └── com/example/
                └── StringProcessorTest.java
```

## Key Features

### JUnit 5 Annotations

| Annotation | Description | Equivalent in JUnit 4 |
|------------|-------------|----------------------|
| `@BeforeAll` | Runs once before all tests (must be static) | `@BeforeClass` |
| `@BeforeEach` | Runs before each test | `@Before` |
| `@Test` | Marks a test method | `@Test` |
| `@DisplayName` | Custom test name in reports | N/A |
| `@ParameterizedTest` | Run test with different parameters | N/A |
| `@RepeatedTest` | Repeat test multiple times | N/A |
| `@Nested` | Nested test classes | N/A |
| `@Disabled` | Skip this test | `@Ignore` |
| `@Timeout` | Test timeout limit | `@Test(timeout=...)` |
| `@AfterEach` | Runs after each test | `@After` |
| `@AfterAll` | Runs once after all tests (must be static) | `@AfterClass` |

## Running Tests

### Using Maven

```bash
# Run all tests
mvn test

# Run with verbose output
mvn clean test -X

# Run specific test class
mvn test -Dtest=StringProcessorTest

# Run tests matching pattern
mvn test -Dtest="*Test"
```

### Using IDE

**IntelliJ IDEA:**
- Right-click on test class → Run 'StringProcessorTest'
- Click ▶️ icon next to `@Test` methods
- Use Ctrl+Shift+F10 (Windows) or Cmd+Shift+R (Mac)

**Eclipse:**
- Right-click → Run As → JUnit Test

### Expected Output

```
[INFO] Running com.example.StringProcessorTest
@BeforeAll - runs once before all tests

String Processor Tests
  ✓ Should reverse a simple string
  ✓ Should throw exception for null input
  ✓ Should convert to uppercase
  ✓ Should identify palindromes [4 tests]
  ✓ Should count vowels correctly [5 tests]
  ✓ Should handle null and empty strings [2 tests]
  ✓ Should complete within timeout
  Grouped tests for special characters
    ✓ Should handle special characters in reverse
    ✓ Should handle special characters in vowel count
  ✓ Repeated test example [3 repetitions]

Tests run: 20, Failures: 0, Errors: 0, Skipped: 1
```

## Test Explanations

### 1. Display Names (`@DisplayName`)

```java
@Test
@DisplayName("Should reverse a simple string")
void testReverse() {
    assertEquals("olleh", processor.reverse("hello"));
}
```

**Benefits:**
- More readable test reports
- Can use spaces and special characters
- Describes test intent clearly

### 2. Parameterized Tests

#### With `@ValueSource`

```java
@ParameterizedTest
@DisplayName("Should identify palindromes")
@ValueSource(strings = {"racecar", "madam", "noon"})
void testPalindromes(String input) {
    assertTrue(processor.isPalindrome(input));
}
```

**Explanation:**
- Runs the same test with different inputs
- Each value is passed to the test method
- Test runs 3 times (once per value)

#### With `@CsvSource`

```java
@ParameterizedTest
@CsvSource({
    "hello, 2",
    "world, 1",
    "aeiou, 5"
})
void testCountVowels(String input, int expected) {
    assertEquals(expected, processor.countVowels(input));
}
```

**Explanation:**
- Provides multiple parameters per test
- Format: "param1, param2, param3"
- Great for testing inputs and expected outputs

#### With `@NullSource` and `@EmptySource`

```java
@ParameterizedTest
@NullSource
@EmptySource
void testPalindromeEdgeCases(String input) {
    assertFalse(processor.isPalindrome(input));
}
```

**Explanation:**
- `@NullSource` - tests with null value
- `@EmptySource` - tests with empty string
- Perfect for edge case testing

### 3. Nested Tests (`@Nested`)

```java
@Nested
@DisplayName("Grouped tests for special characters")
class SpecialCharacterTests {
    
    @Test
    @DisplayName("Should handle special characters in reverse")
    void testReverseSpecialChars() {
        assertEquals("!@#", processor.reverse("#@!"));
    }
}
```

**Benefits:**
- Organize related tests together
- Create hierarchical test structure
- Better test organization and readability

### 4. Repeated Tests (`@RepeatedTest`)

```java
@RepeatedTest(3)
@DisplayName("Repeated test example")
void testRepeated(RepetitionInfo repetitionInfo) {
    System.out.println("Execution " + 
        repetitionInfo.getCurrentRepetition());
    assertNotNull(processor);
}
```

**Use Cases:**
- Test randomized operations
- Verify consistency
- Stress testing

### 5. Timeout Tests

```java
@Test
@Timeout(1)  // 1 second
void testTimeout() {
    assertTimeout(Duration.ofMillis(100), () -> {
        processor.reverse("test");
    });
}
```

**Explanation:**
- `@Timeout` - method-level timeout
- `assertTimeout()` - assertion-level timeout
- Fails if execution exceeds time limit

### 6. Exception Testing

```java
@Test
void testReverseNull() {
    assertThrows(IllegalArgumentException.class, 
        () -> processor.reverse(null));
}
```

**Benefits over JUnit 4:**
- Returns the exception for further assertions
- Uses lambda expressions
- More flexible

**Advanced usage:**
```java
Exception exception = assertThrows(
    IllegalArgumentException.class,
    () -> processor.reverse(null)
);
assertEquals("Input cannot be null", exception.getMessage());
```

## Advanced Features

### Multiple Assertions

```java
@Test
void testMultipleAssertions() {
    assertAll("processor",
        () -> assertEquals("olleh", processor.reverse("hello")),
        () -> assertEquals("HELLO", processor.toUpperCase("hello")),
        () -> assertTrue(processor.isPalindrome("noon"))
    );
}
```

**Benefits:**
- All assertions execute even if one fails
- Better error reporting
- Group related assertions

### Assumptions

```java
@Test
void testOnlyOnLinux() {
    assumeTrue(System.getProperty("os.name").contains("Linux"));
    // Test only runs on Linux
}
```

### Dynamic Tests

```java
@TestFactory
Stream<DynamicTest> dynamicTests() {
    return Stream.of("hello", "world", "test")
        .map(word -> dynamicTest("Test: " + word, () -> {
            assertTrue(processor.toUpperCase(word).length() > 0);
        }));
}
```

## Parameterized Test Sources

### 1. `@ValueSource`
```java
@ValueSource(strings = {"a", "b", "c"})
@ValueSource(ints = {1, 2, 3})
@ValueSource(doubles = {1.0, 2.0, 3.0})
```

### 2. `@CsvSource`
```java
@CsvSource({
    "apple, 2",
    "banana, 3",
    "cherry, 1"
})
```

### 3. `@CsvFileSource`
```java
@CsvFileSource(resources = "/test-data.csv")
```

### 4. `@MethodSource`
```java
@MethodSource("stringProvider")
void test(String argument) { }

static Stream<String> stringProvider() {
    return Stream.of("apple", "banana");
}
```

### 5. `@EnumSource`
```java
@EnumSource(TimeUnit.class)
void testWithEnums(TimeUnit unit) { }
```

## Test Lifecycle

```
@BeforeAll (once - static method)
│
├── @BeforeEach
│   ├── @Test
│   └── @AfterEach
│
├── @BeforeEach
│   ├── @Test
│   └── @AfterEach
│
└── @AfterAll (once - static method)
```

## Best Practices

### ✅ Do's

1. **Use Display Names**
   ```java
   @DisplayName("Should calculate discount correctly for premium users")
   ```

2. **Leverage Parameterized Tests**
   ```java
   @ParameterizedTest
   @CsvSource({"10, 20, 30", "5, 5, 10"})
   ```

3. **Group Related Tests**
   ```java
   @Nested
   class ValidationTests { }
   ```

4. **Use assertAll for Multiple Assertions**
   ```java
   assertAll(
       () -> assertEquals(expected1, actual1),
       () -> assertEquals(expected2, actual2)
   );
   ```

### ❌ Don'ts

1. Don't mix JUnit 4 and JUnit 5 annotations
2. Don't forget `static` for `@BeforeAll` and `@AfterAll`
3. Don't overuse nested classes (keep it simple)

## Comparison: JUnit 4 vs JUnit 5

| Feature | JUnit 4 | JUnit 5 |
|---------|---------|---------|
| Exception Testing | `@Test(expected=...)` | `assertThrows(...)` |
| Timeout | `@Test(timeout=1000)` | `@Timeout(1)` |
| Ignored Tests | `@Ignore` | `@Disabled` |
| Assertions | Static imports | Static imports |
| Parameterized | Separate runner | `@ParameterizedTest` |
| Extension | `@Rule`, `@ClassRule` | `@ExtendWith` |

## Troubleshooting

### Maven Surefire Plugin Issue

If tests don't run, ensure you have:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0-M9</version>
</plugin>
```

### IDE Not Recognizing Tests

- IntelliJ: File → Invalidate Caches / Restart
- Eclipse: Install JUnit 5 support from marketplace

## Further Reading

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [JUnit 5 Migration](https://junit.org/junit5/docs/current/user-guide/#migrating-from-junit4)
- [Parameterized Tests Guide](https://www.baeldung.com/parameterized-tests-junit-5)

## Next Steps

1. Explore **mockito-demo** for mocking dependencies
2. Study **springboot-testing-demo** for integration testing
3. Practice writing parameterized tests for your own code

---

**Happy Testing! 🧪**
