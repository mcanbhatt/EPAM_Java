package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Demonstrates @Spy annotation
 *
 * @Spy creates a partial mock - a real object with some methods stubbed
 * Unlike @Mock which creates a fake object, @Spy wraps a real object
 */
@ExtendWith(MockitoExtension.class)
class SpyExampleTest {

    // ===========================================
    // SPY vs MOCK
    // ===========================================

    // @Mock - All methods return default values (null, 0, false)
    // @Spy - Real methods are called unless explicitly stubbed

    @Test
    void testSpyVsMock() {
        // Mock - returns empty list (default)
        List<String> mockList = mock(ArrayList.class);
        assertEquals(0, mockList.size());
        mockList.add("item");
        assertEquals(0, mockList.size()); // Still 0! Mock doesn't actually add

        // Spy - real ArrayList behavior
        List<String> spyList = spy(ArrayList.class);
        assertEquals(0, spyList.size());
        spyList.add("item");
        assertEquals(1, spyList.size()); // Real method called!
        assertTrue(spyList.contains("item"));
    }

    // ===========================================
    // BASIC SPY USAGE
    // ===========================================

    @Test
    void testBasicSpy() {
        // Create spy of real object
        List<String> spyList = spy(new ArrayList<>());

        // Real methods work
        spyList.add("one");
        spyList.add("two");
        assertEquals(2, spyList.size());

        // But we can still verify
        verify(spyList).add("one");
        verify(spyList, times(2)).add(anyString());
    }

    // ===========================================
    // STUBBING SPY METHODS
    // ===========================================

    @Test
    void testStubbingSpy() {
        List<String> spyList = spy(new ArrayList<>());

        // Stub specific method
        when(spyList.size()).thenReturn(100);

        // Stubbed method returns our value
        assertEquals(100, spyList.size());

        // But other methods still work normally
        spyList.add("item");
        assertTrue(spyList.contains("item"));
    }

    @Test
    void testDoReturnForSpy() {
        List<String> spyList = spy(new ArrayList<>());

        // IMPORTANT: Use doReturn() for spy, not when()
        // when() calls the real method which may have side effects

        // ❌ Wrong - calls real get(0) which throws IndexOutOfBoundsException
        // when(spyList.get(0)).thenReturn("first");

        // ✅ Correct - doesn't call real method
        doReturn("first").when(spyList).get(0);

        assertEquals("first", spyList.get(0));
    }

    // ===========================================
    // SPY WITH REAL CLASS
    // ===========================================

    static class Calculator {
        public int add(int a, int b) {
            return a + b;
        }

        public int multiply(int a, int b) {
            return a * b;
        }

        public int complexCalculation(int a, int b) {
            // Uses other methods
            return multiply(add(a, b), 2);
        }
    }

    @Test
    void testSpyWithRealClass() {
        Calculator calculator = spy(new Calculator());

        // Real method works
        assertEquals(5, calculator.add(2, 3));

        // Stub one method
        doReturn(10).when(calculator).multiply(anyInt(), anyInt());

        // Stubbed method returns our value
        assertEquals(10, calculator.multiply(2, 3));

        // Real method still works
        assertEquals(7, calculator.add(4, 3));

        // Complex calculation uses stubbed multiply
        assertEquals(10, calculator.complexCalculation(2, 3)); // add(2,3)=5, multiply(5,2)=10
    }

    // ===========================================
    // SPY WITH @Spy ANNOTATION
    // ===========================================

    @Spy
    private List<String> spyList = new ArrayList<>();

    @Test
    void testSpyAnnotation() {
        // Spy created by annotation
        spyList.add("item");
        assertEquals(1, spyList.size());

        verify(spyList).add("item");
    }

    // ===========================================
    // PARTIAL MOCKING USE CASE
    // ===========================================

    static class UserValidator {
        public boolean validateEmail(String email) {
            return email != null && email.contains("@");
        }

        public boolean validatePassword(String password) {
            return password != null && password.length() >= 8;
        }

        public boolean validateUser(String email, String password) {
            return validateEmail(email) && validatePassword(password);
        }
    }

    @Test
    void testPartialMocking() {
        UserValidator validator = spy(new UserValidator());

        // Use real email validation
        assertTrue(validator.validateEmail("test@example.com"));
        assertFalse(validator.validateEmail("invalid"));

        // But stub password validation for testing
        doReturn(true).when(validator).validatePassword(anyString());

        // Now any password passes (for testing purposes)
        assertTrue(validator.validateUser("test@example.com", "short"));

        verify(validator).validateEmail("test@example.com");
        verify(validator).validatePassword("short");
    }

    // ===========================================
    // SPY - CALLING REAL METHODS
    // ===========================================

    @Test
    void testCallRealMethod() {
        List<String> spyList = spy(new ArrayList<>());

        // Stub a method
        when(spyList.size()).thenReturn(100);

        // Call real method explicitly
        doCallRealMethod().when(spyList).size();

        spyList.add("item");
        assertEquals(1, spyList.size()); // Real method called
    }

    // ===========================================
    // SPY WITH VOID METHODS
    // ===========================================

    @Test
    void testSpyVoidMethods() {
        List<String> spyList = spy(new ArrayList<>());

        // Stub void method to do nothing
        doNothing().when(spyList).clear();

        spyList.add("item1");
        spyList.add("item2");
        spyList.clear();

        // Real clear was not called, items still there
        assertEquals(2, spyList.size());

        verify(spyList).clear();
    }

    @Test
    void testSpyVoidMethodThrows() {
        List<String> spyList = spy(new ArrayList<>());

        // Stub void method to throw exception
        doThrow(new RuntimeException("Cannot clear")).when(spyList).clear();

        spyList.add("item");

        assertThrows(RuntimeException.class, () -> spyList.clear());

        // Item still there because exception thrown
        assertEquals(1, spyList.size());
    }

    // ===========================================
    // SPY BEST PRACTICES
    // ===========================================

    @Test
    void testSpyBestPractices() {
        // ✅ Good: Spy on concrete class
        ArrayList<String> concreteList = spy(new ArrayList<>());
        concreteList.add("item");
        assertEquals(1, concreteList.size());

        // ✅ Good: Use doReturn for spy
        doReturn(10).when(concreteList).size();
        assertEquals(10, concreteList.size());

        // ✅ Good: Verify interactions
        verify(concreteList).add("item");
    }

    // ===========================================
    // WHEN TO USE SPY
    // ===========================================

    /*
     * Use @Spy when:
     * 1. You need mostly real behavior with some stubs
     * 2. Testing legacy code with hard dependencies
     * 3. You want to verify method calls on real objects
     * 4. Partial mocking is necessary
     *
     * Use @Mock when:
     * 1. You want complete control over behavior
     * 2. Dependencies are interfaces
     * 3. Full isolation is needed
     * 4. Testing with fake implementations
     *
     * Generally prefer @Mock over @Spy for cleaner tests
     */

    // ===========================================
    // SPY LIMITATIONS
    // ===========================================

    @Test
    void testSpyLimitations() {
        List<String> spyList = spy(new ArrayList<>());

        // ❌ Cannot spy on final classes (without special configuration)
        // ❌ Cannot spy on final methods
        // ❌ Cannot spy on static methods
        // ❌ May cause issues with lazy initialization

        // ✅ Spy works well with concrete classes and non-final methods
        spyList.add("item");
        verify(spyList).add("item");
    }

    // ===========================================
    // COMPLEX SPY EXAMPLE
    // ===========================================

    static class OrderProcessor {
        private PaymentProcessor paymentProcessor;

        public OrderProcessor(PaymentProcessor paymentProcessor) {
            this.paymentProcessor = paymentProcessor;
        }

        public boolean processPayment(Long userId, double amount) {
            return paymentProcessor.processPayment(userId, amount);
        }

        public double calculateTotal(List<Double> prices) {
            return prices.stream().mapToDouble(Double::doubleValue).sum();
        }

        public boolean processOrder(Long userId, List<Double> prices) {
            double total = calculateTotal(prices);
            return processPayment(userId, total);
        }
    }

    @Test
    void testSpyComplexScenario() {
        PaymentProcessor mockPayment = mock(PaymentProcessor.class);
        OrderProcessor processor = spy(new OrderProcessor(mockPayment));

        // Use real calculateTotal but stub processPayment
        when(mockPayment.processPayment(anyLong(), anyDouble())).thenReturn(true);
        doReturn(true).when(processor).processPayment(anyLong(), anyDouble());

        List<Double> prices = Arrays.asList(10.0, 20.0, 30.0);

        // Real calculateTotal is called
        assertEquals(60.0, processor.calculateTotal(prices));

        // Stubbed processPayment is called
        assertTrue(processor.processOrder(1L, prices));

        verify(processor).calculateTotal(prices);
        verify(processor).processPayment(1L, 60.0);
    }
}
