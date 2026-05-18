# JUnit 4 Demo Project

A comprehensive demonstration of JUnit 4 testing framework with a simple Calculator application.

## 📋 Table of Contents
- [Overview](#overview)
- [Project Structure](#project-structure)
- [Key Concepts](#key-concepts)
- [Running Tests](#running-tests)
- [Test Explanations](#test-explanations)
- [Common Patterns](#common-patterns)

## Overview

This project demonstrates the fundamentals of JUnit 4, the traditional testing framework for Java applications. It includes a `Calculator` class with basic arithmetic operations and comprehensive test coverage.

### What's Included
- **Calculator.java**: Simple calculator with add, subtract, multiply, divide operations
- **CalculatorTest.java**: Complete test suite demonstrating JUnit 4 features

## Project Structure

```
junit4-demo/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   └── java/
    │       └── com/example/
    │           └── Calculator.java
    └── test/
        └── java/
            └── com/example/
                └── CalculatorTest.java
```

## Key Concepts

### JUnit 4 Annotations

| Annotation | Description | Runs |
|------------|-------------|------|
| `@BeforeClass` | Setup method for entire test class | Once before all tests |
| `@Before` | Setup method for each test | Before each test method |
| `@Test` | Marks a method as a test | Once per test method |
| `@After` | Cleanup method for each test | After each test method |
| `@AfterClass` | Cleanup method for entire test class | Once after all tests |
| `@Ignore` | Skip this test | Never (test is disabled) |

### Test Lifecycle Example

```
@BeforeClass (once)
    @Before
    @Test - testAdd()
    @After
    
    @Before
    @Test - testSubtract()
    @After
    
    @Before
    @Test - testMultiply()
    @After
@AfterClass (once)
```

## Running Tests

### Using Maven

```bash
# Run all tests
mvn test

# Run with verbose output
mvn test -X

# Run specific test class
mvn test -Dtest=CalculatorTest

# Run specific test method
mvn test -Dtest=CalculatorTest#testAdd
```

### Using IDE

**IntelliJ IDEA:**
- Right-click on `CalculatorTest.java` → Run 'CalculatorTest'
- Or click the green play button next to the class or individual test methods

**Eclipse:**
- Right-click on `CalculatorTest.java` → Run As → JUnit Test

### Expected Output

```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.example.CalculatorTest
@BeforeClass - runs once before all tests
@Before - runs before each test
@After - runs after each test
@Before - runs before each test
@After - runs after each test
@Before - runs before each test
@After - runs after each test
@Before - runs before each test
@After - runs after each test
@Before - runs before each test
@After - runs after each test
@Before - runs before each test
@After - runs after each test
@AfterClass - runs once after all tests
Tests run: 6, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.123 sec

Results :

Tests run: 6, Failures: 0, Errors: 0, Skipped: 1
```

## Test Explanations

### 1. Basic Test (`testAdd`)

```java
@Test
public void testAdd() {
    assertEquals(5, calculator.add(2, 3));
    assertEquals(0, calculator.add(-1, 1));
}
```

**Explanation:**
- Tests addition operation
- `assertEquals(expected, actual)` - checks if values are equal
- Multiple assertions in one test

### 2. Exception Testing (`testDivideByZero`)

```java
@Test(expected = IllegalArgumentException.class)
public void testDivideByZero() {
    calculator.divide(10, 0);
}
```

**Explanation:**
- Tests that the correct exception is thrown
- Test passes if `IllegalArgumentException` is thrown
- Test fails if no exception or wrong exception is thrown

### 3. Timeout Testing (`testPerformance`)

```java
@Test(timeout = 1000)
public void testPerformance() {
    for (int i = 0; i < 1000; i++) {
        calculator.add(i, i);
    }
}
```

**Explanation:**
- Ensures test completes within 1000 milliseconds
- Useful for performance testing
- Fails if execution takes longer than specified timeout

### 4. Ignored Test (`testComplexOperation`)

```java
@Ignore("Not ready yet")
@Test
public void testComplexOperation() {
    fail("This test is not implemented");
}
```

**Explanation:**
- Test is skipped during execution
- Useful for work-in-progress tests
- Shows in test report as "Skipped"

## Common Patterns

### AAA Pattern (Arrange-Act-Assert)

```java
@Test
public void testDivide() {
    // Arrange
    Calculator calc = new Calculator();
    int dividend = 6;
    int divisor = 3;
    
    // Act
    double result = calc.divide(dividend, divisor);
    
    // Assert
    assertEquals(2.0, result, 0.001);
}
```

### Common Assertions

```java
// Equality
assertEquals(expected, actual);
assertEquals(expected, actual, delta); // For floating point

// Boolean conditions
assertTrue(condition);
assertFalse(condition);

// Null checks
assertNull(object);
assertNotNull(object);

// Same object reference
assertSame(object1, object2);
assertNotSame(object1, object2);

// Array equality
assertArrayEquals(expectedArray, actualArray);

// Force failure
fail("Failure message");
```

### Setup and Teardown Example

```java
private Calculator calculator;
private List<String> testData;

@Before
public void setUp() {
    calculator = new Calculator();
    testData = new ArrayList<>();
    System.out.println("Test started");
}

@After
public void tearDown() {
    calculator = null;
    testData.clear();
    System.out.println("Test finished");
}
```

## Best Practices

### ✅ Do's

1. **One assertion per test** (when possible)
   ```java
   @Test
   public void testAddPositiveNumbers() {
       assertEquals(5, calculator.add(2, 3));
   }
   ```

2. **Use descriptive test names**
   ```java
   @Test
   public void testDivideByZeroThrowsException() { ... }
   ```

3. **Test edge cases**
   ```java
   @Test
   public void testAddWithNegativeNumbers() { ... }
   
   @Test
   public void testAddWithZero() { ... }
   ```

4. **Keep tests independent**
   - Each test should work in isolation
   - Don't rely on test execution order

### ❌ Don'ts

1. **Don't test private methods directly**
   - Test through public API
   
2. **Don't use random data in tests**
   - Tests should be deterministic
   
3. **Don't share state between tests**
   - Use `@Before` to reset state

## Troubleshooting

### Issue: Tests not running

**Solution:**
```bash
mvn clean test
```

### Issue: Cannot find JUnit

**Solution:** Check pom.xml has correct dependency:
```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>
```

### Issue: Assertion errors

**Solution:** Check the assertion order:
- First parameter: expected value
- Second parameter: actual value

## Migration to JUnit 5

If you want to migrate to JUnit 5, see mapping:

| JUnit 4 | JUnit 5 |
|---------|---------|
| `@Before` | `@BeforeEach` |
| `@After` | `@AfterEach` |
| `@BeforeClass` | `@BeforeAll` |
| `@AfterClass` | `@AfterAll` |
| `@Ignore` | `@Disabled` |
| `@Test(expected = Exception.class)` | `assertThrows(Exception.class, ...)` |
| `@Test(timeout = 1000)` | `@Timeout(1)` |

## Further Reading

- [JUnit 4 Documentation](https://junit.org/junit4/)
- [JUnit 4 GitHub Wiki](https://github.com/junit-team/junit4/wiki)
- [Effective Unit Testing with JUnit](https://www.baeldung.com/junit-4-rules)

## Next Steps

After mastering JUnit 4:
1. Explore **junit5-demo** for modern JUnit features
2. Learn **mockito-demo** for mocking dependencies
3. Study **springboot-testing-demo** for integration testing

---

**Happy Testing! 🧪**
