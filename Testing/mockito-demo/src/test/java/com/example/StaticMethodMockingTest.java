package com.example;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Comprehensive guide to mocking STATIC METHODS in Mockito
 *
 * Requirements:
 * - mockito-inline dependency (included in pom.xml)
 * - JUnit 5
 *
 * IMPORTANT: Static mocking requires mockito-inline, not mockito-core alone
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Static Method Mocking Examples")
class StaticMethodMockingTest {

    // ===========================================
    // 1. BASIC STATIC METHOD MOCKING
    // ===========================================

    @Test
    @Order(1)
    @DisplayName("1. Basic static method mocking")
    void testBasicStaticMethodMocking() {
        // Mock static method within try-with-resources
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {

            // Define behavior for static method
            LocalDateTime fixedDate = LocalDateTime.of(2024, 1, 1, 10, 0);
            mockedDateUtils.when(DateUtils::getCurrentDateTime).thenReturn(fixedDate);

            // Call the static method
            LocalDateTime result = DateUtils.getCurrentDateTime();

            // Assert
            assertEquals(fixedDate, result);

            // Verify static method was called
            mockedDateUtils.verify(DateUtils::getCurrentDateTime);
        }
        // After try block, static method returns to normal behavior
    }

    // ===========================================
    // 2. MOCKING WITH PARAMETERS
    // ===========================================

    @Test
    @Order(2)
    @DisplayName("2. Mock static method with parameters")
    void testStaticMethodWithParameters() {
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {

            LocalDateTime testDate = LocalDateTime.of(2024, 6, 15, 14, 30);

            // Mock method with parameters using argument matchers
            mockedDateUtils.when(() -> DateUtils.formatDate(any(LocalDateTime.class)))
                          .thenReturn("2024-06-15 14:30:00");

            String result = DateUtils.formatDate(testDate);

            assertEquals("2024-06-15 14:30:00", result);

            // Verify with specific argument
            mockedDateUtils.verify(() -> DateUtils.formatDate(testDate));
        }
    }

    // ===========================================
    // 3. PARTIAL MOCKING - Some methods mocked, others real
    // ===========================================

    @Test
    @Order(3)
    @DisplayName("3. Partial static mocking")
    void testPartialStaticMocking() {
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {

            // Mock only getCurrentDateTime, let others use real implementation
            LocalDateTime fixedDate = LocalDateTime.of(2024, 1, 1, 10, 0);
            mockedDateUtils.when(DateUtils::getCurrentDateTime).thenReturn(fixedDate);

            // This calls the MOCKED method
            LocalDateTime current = DateUtils.getCurrentDateTime();
            assertEquals(fixedDate, current);

            // Call real implementation for other methods
            mockedDateUtils.when(() -> DateUtils.formatDate(any(LocalDateTime.class)))
                          .thenCallRealMethod();

            String formatted = DateUtils.formatDate(fixedDate);
            assertTrue(formatted.contains("2024-01-01"));
        }
    }

    // ===========================================
    // 4. MULTIPLE STATIC METHODS
    // ===========================================

    @Test
    @Order(4)
    @DisplayName("4. Mock multiple static methods")
    void testMultipleStaticMethods() {
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {

            LocalDateTime fixedDate = LocalDateTime.of(2024, 1, 1, 10, 0);

            // Mock multiple methods
            mockedDateUtils.when(DateUtils::getCurrentDateTime).thenReturn(fixedDate);
            mockedDateUtils.when(() -> DateUtils.formatDate(any())).thenReturn("Fixed Date");
            mockedDateUtils.when(() -> DateUtils.isInFuture(any())).thenReturn(false);

            // Test all mocked methods
            assertEquals(fixedDate, DateUtils.getCurrentDateTime());
            assertEquals("Fixed Date", DateUtils.formatDate(fixedDate));
            assertFalse(DateUtils.isInFuture(fixedDate));
        }
    }

    // ===========================================
    // 5. VERIFICATION WITH TIMES
    // ===========================================

    @Test
    @Order(5)
    @DisplayName("5. Verify static method call count")
    void testStaticMethodVerification() {
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {

            LocalDateTime fixedDate = LocalDateTime.of(2024, 1, 1, 10, 0);
            mockedDateUtils.when(DateUtils::getCurrentDateTime).thenReturn(fixedDate);

            // Call multiple times
            DateUtils.getCurrentDateTime();
            DateUtils.getCurrentDateTime();
            DateUtils.getCurrentDateTime();

            // Verify call count
            mockedDateUtils.verify(DateUtils::getCurrentDateTime, times(3));
            mockedDateUtils.verify(() -> DateUtils.formatDate(any()), never());
        }
    }

    // ===========================================
    // 6. TESTING CLASS THAT USES STATIC METHODS
    // ===========================================

    @Test
    @Order(6)
    @DisplayName("6. Test service using static methods")
    void testServiceWithStaticDependencies() {
        ReportService service = new ReportService();

        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {

            // Mock static dependencies
            LocalDateTime fixedDate = LocalDateTime.of(2024, 6, 15, 10, 0);
            mockedDateUtils.when(DateUtils::getCurrentDateTime).thenReturn(fixedDate);
            mockedDateUtils.when(() -> DateUtils.formatDate(any()))
                          .thenReturn("2024-06-15 10:00:00");

            // Test service method
            String report = service.generateReport("Monthly Report");

            assertTrue(report.contains("Monthly Report"));
            assertTrue(report.contains("2024-06-15 10:00:00"));

            // Verify static methods were called
            mockedDateUtils.verify(DateUtils::getCurrentDateTime);
            mockedDateUtils.verify(() -> DateUtils.formatDate(fixedDate));
        }
    }

    // ===========================================
    // 7. EXCEPTION HANDLING
    // ===========================================

    @Test
    @Order(7)
    @DisplayName("7. Mock static method to throw exception")
    void testStaticMethodException() {
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {

            // Mock to throw exception
            mockedDateUtils.when(DateUtils::getCurrentDateTime)
                          .thenThrow(new RuntimeException("Time service unavailable"));

            // Assert exception is thrown
            assertThrows(RuntimeException.class, DateUtils::getCurrentDateTime);
        }
    }

    // ===========================================
    // 8. MOCKING MULTIPLE STATIC CLASSES
    // ===========================================

    @Test
    @Order(8)
    @DisplayName("8. Mock multiple static classes")
    void testMultipleStaticClasses() {
        // You can mock multiple static classes simultaneously
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class);
             MockedStatic<ConfigManager> mockedConfig = mockStatic(ConfigManager.class)) {

            // Mock DateUtils
            LocalDateTime fixedDate = LocalDateTime.of(2024, 1, 1, 10, 0);
            mockedDateUtils.when(DateUtils::getCurrentDateTime).thenReturn(fixedDate);

            // Mock ConfigManager
            mockedConfig.when(ConfigManager::getApplicationInfo).thenReturn("TestApp v2.0");

            // Test both
            assertEquals(fixedDate, DateUtils.getCurrentDateTime());
            assertEquals("TestApp v2.0", ConfigManager.getApplicationInfo());
        }
    }

    // ===========================================
    // 9. CONSECUTIVE CALLS
    // ===========================================

    @Test
    @Order(9)
    @DisplayName("9. Different return values for consecutive calls")
    void testConsecutiveStaticCalls() {
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {

            LocalDateTime date1 = LocalDateTime.of(2024, 1, 1, 10, 0);
            LocalDateTime date2 = LocalDateTime.of(2024, 1, 2, 10, 0);
            LocalDateTime date3 = LocalDateTime.of(2024, 1, 3, 10, 0);

            // Different return values for consecutive calls
            mockedDateUtils.when(DateUtils::getCurrentDateTime)
                          .thenReturn(date1)
                          .thenReturn(date2)
                          .thenReturn(date3);

            assertEquals(date1, DateUtils.getCurrentDateTime());
            assertEquals(date2, DateUtils.getCurrentDateTime());
            assertEquals(date3, DateUtils.getCurrentDateTime());
        }
    }

    // ===========================================
    // 10. ARGUMENT MATCHING
    // ===========================================

    @Test
    @Order(10)
    @DisplayName("10. Static method with argument matching")
    void testArgumentMatching() {
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {

            LocalDateTime pastDate = LocalDateTime.of(2020, 1, 1, 10, 0);
            LocalDateTime futureDate = LocalDateTime.of(2030, 1, 1, 10, 0);

            // Mock with specific argument matching
            mockedDateUtils.when(() -> DateUtils.isInFuture(pastDate)).thenReturn(false);
            mockedDateUtils.when(() -> DateUtils.isInFuture(futureDate)).thenReturn(true);

            assertFalse(DateUtils.isInFuture(pastDate));
            assertTrue(DateUtils.isInFuture(futureDate));
        }
    }

    // ===========================================
    // 11. CLEARING INVOCATIONS
    // ===========================================

    @Test
    @Order(11)
    @DisplayName("11. Clear static method invocations")
    void testClearInvocations() {
        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {

            LocalDateTime fixedDate = LocalDateTime.of(2024, 1, 1, 10, 0);
            mockedDateUtils.when(DateUtils::getCurrentDateTime).thenReturn(fixedDate);

            // Call method
            DateUtils.getCurrentDateTime();
            mockedDateUtils.verify(DateUtils::getCurrentDateTime, times(1));

            // Clear invocations
            mockedDateUtils.clearInvocations();

            // Call again
            DateUtils.getCurrentDateTime();

            // Only 1 invocation counted after clear
            mockedDateUtils.verify(DateUtils::getCurrentDateTime, times(1));
        }
    }

    // ===========================================
    // 12. REAL WORLD EXAMPLE - Testing with Clock
    // ===========================================

    @Test
    @Order(12)
    @DisplayName("12. Real world example - Time-dependent logic")
    void testTimeDependentLogic() {
        ReportService service = new ReportService();

        try (MockedStatic<DateUtils> mockedDateUtils = mockStatic(DateUtils.class)) {

            // Fix time to past
            LocalDateTime pastDate = LocalDateTime.of(2020, 1, 1, 10, 0);
            mockedDateUtils.when(() -> DateUtils.isInFuture(any())).thenReturn(false);

            assertTrue(service.isReportTimely(pastDate));

            // Change behavior - now it's future
            mockedDateUtils.when(() -> DateUtils.isInFuture(any())).thenReturn(true);

            assertFalse(service.isReportTimely(pastDate));
        }
    }

    // ===========================================
    // IMPORTANT NOTES
    // ===========================================

    /*
     * BEST PRACTICES:
     *
     * 1. Always use try-with-resources for MockedStatic
     *    - Ensures proper cleanup
     *    - Avoids affecting other tests
     *
     * 2. Keep static mock scope minimal
     *    - Only mock within the test that needs it
     *    - Don't share MockedStatic across tests
     *
     * 3. Consider design alternatives
     *    - Static methods are hard to test
     *    - Consider dependency injection instead
     *    - Use static methods only for pure utility functions
     *
     * 4. Performance impact
     *    - Static mocking is slower than regular mocking
     *    - Use sparingly
     *
     * 5. Thread safety
     *    - MockedStatic is not thread-safe
     *    - Don't use in parallel test execution
     */

    // ===========================================
    // COMMON PITFALLS
    // ===========================================

    /*
     * ❌ DON'T:
     *
     * // 1. Don't forget try-with-resources
     * MockedStatic<DateUtils> mock = mockStatic(DateUtils.class);
     * // Forgot to close - will affect other tests!
     *
     * // 2. Don't mock final static methods (requires additional config)
     *
     * // 3. Don't share MockedStatic between tests
     * private static MockedStatic<DateUtils> sharedMock; // Bad!
     *
     * ✅ DO:
     *
     * // Always use try-with-resources
     * try (MockedStatic<DateUtils> mock = mockStatic(DateUtils.class)) {
     *     // Your test code
     * }
     */
}
