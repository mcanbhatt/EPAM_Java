# Complete Mockito Features Reference

This document provides a comprehensive overview of ALL Mockito features demonstrated in this project.

## 📋 Table of Contents
1. [Annotations](#annotations)
2. [Stubbing Methods](#stubbing-methods)
3. [Verification](#verification)
4. [Argument Matchers](#argument-matchers)
5. [Argument Captors](#argument-captors)
6. [Spy Objects](#spy-objects)
7. [BDD Style](#bdd-style)
8. [Advanced Features](#advanced-features)

---

## 1. Annotations

### @Mock
Creates a mock object (completely fake).

```java
@Mock
private UserRepository userRepository;
```

**When to use:** Testing with dependencies you want complete control over.

### @InjectMocks
Injects mocks into the tested object.

```java
@InjectMocks
private UserService userService;
```

**When to use:** Automatically inject @Mock dependencies into the class under test.

### @Spy
Creates a spy object (real object with ability to stub methods).

```java
@Spy
private List<String> spyList = new ArrayList<>();
```

**When to use:** Need mostly real behavior but want to stub/verify specific methods.

### @Captor
Creates an ArgumentCaptor.

```java
@Captor
private ArgumentCaptor<String> stringCaptor;
```

**When to use:** Need to capture and inspect arguments passed to mocks.

### @ExtendWith(MockitoExtension.class)
Enables Mockito for JUnit 5.

```java
@ExtendWith(MockitoExtension.class)
class MyTest { }
```

---

## 2. Stubbing Methods

### when().thenReturn()
Basic stubbing - return a value.

```java
when(repository.findById(1L)).thenReturn(user);
```

### when().thenThrow()
Stub to throw an exception.

```java
when(repository.findById(99L)).thenThrow(new NotFoundException());
```

### doReturn().when()
Alternative stubbing syntax (preferred for spies).

```java
doReturn(user).when(repository).findById(1L);
```

### doThrow().when()
Stub void methods to throw exceptions.

```java
doThrow(new RuntimeException()).when(service).deleteUser(1L);
```

### doNothing().when()
Explicitly do nothing (for void methods).

```java
doNothing().when(emailService).sendEmail(anyString());
```

### doAnswer().when()
Custom behavior with Answer.

```java
doAnswer(invocation -> {
    String arg = invocation.getArgument(0);
    return "Hello " + arg;
}).when(service).greet(anyString());
```

### doCallRealMethod().when()
Call the real method on a spy/mock.

```java
doCallRealMethod().when(spyList).clear();
```

### Consecutive Calls

```java
when(repository.findById(1L))
    .thenReturn(user1)   // First call
    .thenReturn(user2)   // Second call
    .thenThrow(new RuntimeException()); // Third call
```

---

## 3. Verification

### verify()
Verify method was called.

```java
verify(repository).save(user);
```

### verify(times())
Verify exact number of calls.

```java
verify(repository, times(2)).save(any());
```

### verify(never())
Verify method was never called.

```java
verify(repository, never()).delete(anyLong());
```

### verify(atLeast())
Verify minimum number of calls.

```java
verify(repository, atLeast(1)).save(any());
```

### verify(atMost())
Verify maximum number of calls.

```java
verify(repository, atMost(3)).save(any());
```

### verify(timeout())
Verify within timeout (for async).

```java
verify(service, timeout(1000)).sendEmail(anyString());
```

### verifyNoInteractions()
Verify no methods called on mock.

```java
verifyNoInteractions(emailService);
```

### verifyNoMoreInteractions()
Verify no other methods called.

```java
verify(repository).findById(1L);
verifyNoMoreInteractions(repository);
```

### InOrder Verification
Verify methods called in specific order.

```java
InOrder inOrder = inOrder(repository, emailService);
inOrder.verify(repository).findById(1L);
inOrder.verify(emailService).sendEmail(anyString());
```

---

## 4. Argument Matchers

### Basic Matchers

```java
// Any type
any()
any(User.class)

// Primitives
anyInt()
anyLong()
anyDouble()
anyBoolean()
anyString()

// Collections
anyList()
anySet()
anyMap()
anyCollection()

// Exact match
eq(value)

// Null checks
isNull()
isNotNull()
```

### String Matchers

```java
contains("text")
startsWith("prefix")
endsWith("suffix")
matches("regex")
```

### Collection Matchers

```java
anyList()
anySet()
anyCollection()
```

### Custom Matchers

```java
argThat(argument -> argument.length() > 5)
argThat(user -> user.getAge() > 18)
```

### Important Rule

⚠️ **When using matchers, use them for ALL arguments:**

```java
// ❌ Wrong - mixing literal and matcher
verify(service).method("literal", anyString());

// ✅ Correct - all matchers
verify(service).method(eq("literal"), anyString());
```

---

## 5. Argument Captors

### Basic Usage

```java
@Captor
private ArgumentCaptor<String> stringCaptor;

@Test
void test() {
    service.sendEmail("test@example.com");
    
    verify(emailService).sendEmail(stringCaptor.capture());
    assertEquals("test@example.com", stringCaptor.getValue());
}
```

### Multiple Captures

```java
service.sendEmail("email1@example.com");
service.sendEmail("email2@example.com");

verify(emailService, times(2)).sendEmail(stringCaptor.capture());

List<String> allEmails = stringCaptor.getAllValues();
assertEquals(2, allEmails.size());
```

### Creating Captors

```java
// Method 1: Annotation
@Captor
private ArgumentCaptor<User> userCaptor;

// Method 2: Manual creation
ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
```

---

## 6. Spy Objects

### Spy vs Mock

| Feature | @Mock | @Spy |
|---------|-------|------|
| Default behavior | Returns null/0/false | Calls real method |
| Use case | Full isolation | Partial mocking |
| Method stubbing | All methods | Selected methods |
| Real object | No | Yes |

### Creating Spies

```java
// Method 1: Annotation
@Spy
private List<String> spyList = new ArrayList<>();

// Method 2: Manual
List<String> spyList = spy(new ArrayList<>());

// Method 3: Spy on existing object
Calculator calc = new Calculator();
Calculator spyCalc = spy(calc);
```

### Stubbing Spies

```java
List<String> spyList = spy(new ArrayList<>());

// ✅ Correct - use doReturn
doReturn(10).when(spyList).size();

// ❌ Wrong - when() calls real method
when(spyList.size()).thenReturn(10); // May have side effects
```

### When to Use Spy

✅ **Use Spy when:**
- Need mostly real behavior
- Testing legacy code
- Partial mocking necessary
- Want to verify calls on real object

❌ **Avoid Spy when:**
- Can use @Mock instead
- Testing with interfaces
- Need full isolation

---

## 7. BDD Style

BDD (Behavior-Driven Development) style uses `given/when/then` instead of traditional stubbing.

### BDDMockito

```java
import static org.mockito.BDDMockito.*;

@Test
void testBDD() {
    // Given (Arrange)
    given(repository.findById(1L)).willReturn(user);
    
    // When (Act)
    User result = service.getUser(1L);
    
    // Then (Assert)
    then(repository).should().findById(1L);
    assertNotNull(result);
}
```

### BDD Methods

```java
// Stubbing
given(mock.method()).willReturn(value);
given(mock.method()).willThrow(exception);

// Verification
then(mock).should().method();
then(mock).should(times(2)).method();
then(mock).should(never()).method();
then(mock).shouldHaveNoMoreInteractions();
```

---

## 8. Advanced Features

### Reset Mocks

```java
reset(repository);
```

Clears all interactions and stubs. Use sparingly - usually indicates test design issues.

### Clear Invocations

```java
clearInvocations(repository);
```

Clears invocation history but keeps stubs.

### Lenient Stubbing

```java
lenient().when(repository.findById(1L)).thenReturn(user);
```

Prevents `UnnecessaryStubbingException` for unused stubs.

### Mock Settings

```java
List<String> mock = mock(ArrayList.class, withSettings()
    .name("My Mock")
    .verboseLogging()
    .serializable());
```

### Answer Interface

```java
when(service.calculate(anyInt(), anyInt())).thenAnswer(invocation -> {
    Integer arg1 = invocation.getArgument(0);
    Integer arg2 = invocation.getArgument(1);
    return arg1 + arg2;
});
```

### Partial Mocking Example

```java
class Calculator {
    public int add(int a, int b) { return a + b; }
    public int multiply(int a, int b) { return a * b; }
}

@Test
void test() {
    Calculator calc = spy(new Calculator());
    
    // Use real add
    assertEquals(5, calc.add(2, 3));
    
    // Stub multiply
    doReturn(100).when(calc).multiply(anyInt(), anyInt());
    assertEquals(100, calc.multiply(2, 3));
}
```

---

## Complete Feature Matrix

| Feature | Code | Use Case |
|---------|------|----------|
| Mock | `@Mock` | Create fake object |
| Spy | `@Spy` | Partial mocking |
| Inject | `@InjectMocks` | Auto-inject dependencies |
| Captor | `@Captor` | Capture arguments |
| Stub Return | `when().thenReturn()` | Return value |
| Stub Exception | `when().thenThrow()` | Throw exception |
| Stub Void | `doThrow().when()` | Stub void method |
| Custom Behavior | `doAnswer().when()` | Custom logic |
| Verify Called | `verify()` | Check method called |
| Verify Times | `verify(times(n))` | Exact call count |
| Verify Never | `verify(never())` | Not called |
| Verify Order | `InOrder` | Ordered calls |
| Matchers | `any(), eq()` | Flexible matching |
| BDD Style | `given().willReturn()` | BDD syntax |
| Reset | `reset()` | Clear mock |
| Timeout | `verify(timeout())` | Async verification |

---

## Test Files Reference

| File | Demonstrates |
|------|-------------|
| `UserServiceTest.java` | Basic mocking, verification, argument captors |
| `AdvancedMockitoTest.java` | All 19 Mockito patterns and techniques |
| `SpyExampleTest.java` | Complete @Spy usage and patterns |
| `StaticMethodMockingTest.java` | Static method mocking (12 patterns) |
| `StaticFieldMockingTest.java` | Static field testing and modification |
| `TestOrderingExampleTest.java` | Test execution ordering strategies |

---

## Quick Reference Card

### Most Common Patterns

```java
// 1. Create mocks
@Mock private Repository repo;
@InjectMocks private Service service;

// 2. Stub
when(repo.findById(1L)).thenReturn(entity);

// 3. Execute
Entity result = service.getEntity(1L);

// 4. Verify
verify(repo).findById(1L);

// 5. Assert
assertEquals(expected, result);
```

### Exception Testing

```java
// Assert exception thrown
assertThrows(Exception.class, () -> service.method());

// Stub to throw
when(repo.method()).thenThrow(new RuntimeException());
```

### Void Methods

```java
// Stub void to do nothing
doNothing().when(service).voidMethod();

// Stub void to throw
doThrow(new RuntimeException()).when(service).voidMethod();
```

---

**For detailed examples, see the test files in this project!** 🎯
