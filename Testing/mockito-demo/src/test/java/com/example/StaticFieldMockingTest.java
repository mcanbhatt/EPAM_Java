package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Comprehensive guide to working with STATIC FIELDS in tests
 *
 * Note: Mockito doesn't directly "mock" static fields, but we can:
 * 1. Use reflection to modify static fields
 * 2. Mock the class containing static fields
 * 3. Design code to make static fields testable
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Static Field Testing Examples")
class StaticFieldMockingTest {

    // Save original values to restore after tests
    private String originalAppName;
    private String originalVersion;
    private int originalMaxConnections;
    private boolean originalDebugMode;

    @BeforeEach
    void saveOriginalValues() {
        // Save original static field values
        originalAppName = ConfigManager.APPLICATION_NAME;
        originalVersion = ConfigManager.VERSION;
        originalMaxConnections = ConfigManager.MAX_CONNECTIONS;
        originalDebugMode = ConfigManager.DEBUG_MODE;
    }

    @AfterEach
    void restoreOriginalValues() {
        // Restore original values after each test
        ConfigManager.APPLICATION_NAME = originalAppName;
        ConfigManager.VERSION = originalVersion;
        ConfigManager.MAX_CONNECTIONS = originalMaxConnections;
        ConfigManager.DEBUG_MODE = originalDebugMode;
    }

    // ===========================================
    // 1. DIRECT STATIC FIELD MODIFICATION
    // ===========================================

    @Test
    @Ordr(1)
    @DisplayName("1. Modify static field directly")
    void testDirectStaticFieldModification() {
        // Original value
        assertEquals("MyApp", ConfigManager.APPLICATION_NAME);

        // Modify static field
        ConfigManager.APPLICATION_NAME = "TestApp";

        // Verify modification
        assertEquals("TestApp", ConfigManager.APPLICATION_NAME);
        assertEquals("TestApp v1.0.0", ConfigManager.getApplicationInfo());
    }

    // ===========================================
    // 2. USING REFLECTION TO MODIFY STATIC FIELDS
    // ===========================================

    @Test
    @Ordr(2)
    @DisplayName("2. Modify static field using reflection")
    void testStaticFieldWithReflection() throws Exception {
        // Use reflection to modify static field
        Field field = ConfigManager.class.getDeclaredField("DEBUG_MODE");
        field.setAccessible(true);
        field.set(null, true); // null because it's static

        // Verify
        assertTrue(ConfigManager.DEBUG_MODE);
        assertTrue(ConfigManager.isDebugEnabled());
    }

    // ===========================================
    // 3. MODIFY MULTIPLE STATIC FIELDS
    // ===========================================

    @Test
    @Ordr(3)
    @DisplayName("3. Modify multiple static fields")
    void testMultipleStaticFields() {
        // Change multiple fields
        ConfigManager.APPLICATION_NAME = "UnitTestApp";
        ConfigManager.VERSION = "2.0.0";
        ConfigManager.DEBUG_MODE = true;

        // Test method using multiple fields
        String info = ConfigManager.getApplicationInfo();
        assertEquals("UnitTestApp v2.0.0", info);
        assertTrue(ConfigManager.isDebugEnabled());
    }

    // ===========================================
    // 4. TESTING LOGIC DEPENDENT ON STATIC FIELDS
    // ===========================================

    @Test
    @Ordr(4)
    @DisplayName("4. Test business logic with static fields")
    void testBusinessLogicWithStaticFields() {
        // Test with default value
        assertEquals(100, ConfigManager.MAX_CONNECTIONS);
        assertTrue(ConfigManager.canAcceptConnection(50));
        assertFalse(ConfigManager.canAcceptConnection(100));

        // Change static field
        ConfigManager.MAX_CONNECTIONS = 10;

        // Test with new value
        assertTrue(ConfigManager.canAcceptConnection(5));
        assertFalse(ConfigManager.canAcceptConnection(10));
    }

    // ===========================================
    // 5. TESTING SERVICE WITH STATIC DEPENDENCIES
    // ===========================================

    @Test
    @Ordr(5)
    @DisplayName("5. Test service using static fields")
    void testServiceWithStaticFields() {
        ReportService service = new ReportService();

        // Change static fields
        ConfigManager.APPLICATION_NAME = "ReportApp";
        ConfigManager.VERSION = "3.0.0";
        ConfigManager.DEBUG_MODE = true;

        // Test service method
        String systemInfo = service.getSystemInfo();
        assertTrue(systemInfo.contains("ReportApp v3.0.0"));
        assertTrue(systemInfo.contains("DEBUG"));
    }

    // ===========================================
    // 6. TESTING WITH DIFFERENT FIELD VALUES
    // ===========================================

    @Test
    @Ordr(6)
    @DisplayName("6. Test same logic with different field values")
    void testWithDifferentFieldValues() {
        ReportService service = new ReportService();

        // Test with DEBUG_MODE = false
        ConfigManager.DEBUG_MODE = false;
        String infoWithoutDebug = service.getSystemInfo();
        assertFalse(infoWithoutDebug.contains("DEBUG"));

        // Test with DEBUG_MODE = true
        ConfigManager.DEBUG_MODE = true;
        String infoWithDebug = service.getSystemInfo();
        assertTrue(infoWithDebug.contains("DEBUG"));
    }

    // ===========================================
    // 7. REFLECTION HELPER METHOD
    // ===========================================

    @Test
    @Ordr(7)
    @DisplayName("7. Use helper method for reflection")
    void testWithReflectionHelper() throws Exception {
        // Helper method to set static field
        setStaticField(ConfigManager.class, "MAX_CONNECTIONS", 50);

        assertEquals(50, ConfigManager.MAX_CONNECTIONS);
        assertTrue(ConfigManager.canAcceptConnection(25));
    }

    // Helper method for setting static fields via reflection
    private void setStaticField(Class<?> clazz, String fieldName, Object value)
            throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(null, value);
    }

    // ===========================================
    // 8. TESTING STATIC FINAL FIELDS (Workaround)
    // ===========================================

    @Test
    @Ordr(8)
    @DisplayName("8. Access static final field (read-only)")
    void testStaticFinalField() {
        // Static final fields cannot be changed
        // We can only read them in tests
        assertEquals("production", ConfigManager.ENVIRONMENT);

        // Note: Attempting to change final fields requires unsafe operations
        // and is not recommended. Design your code to avoid this need.
    }

    // ===========================================
    // 9. MOCK STATIC CLASS WITH STATIC FIELDS
    // ===========================================

    @Test
    @Ordr(9)
    @DisplayName("9. Mock entire static class")
    void testMockStaticClass() {
        try (var mockedConfig = mockStatic(ConfigManager.class)) {
            // Mock the static method that uses static fields
            mockedConfig.when(ConfigManager::getApplicationInfo)
                       .thenReturn("MockedApp v9.9.9");

            assertEquals("MockedApp v9.9.9", ConfigManager.getApplicationInfo());

            // But direct field access still returns original value
            // (fields themselves aren't mocked, only methods)
            assertEquals("MyApp", ConfigManager.APPLICATION_NAME);
        }
    }

    // ===========================================
    // 10. BOUNDARY TESTING WITH STATIC FIELDS
    // ===========================================

    @Test
    @Ordr(10)
    @DisplayName("10. Boundary testing with static fields")
    void testBoundaryConditions() {
        // Test boundary at max connections
        ConfigManager.MAX_CONNECTIONS = 100;

        assertTrue(ConfigManager.canAcceptConnection(99));  // Just below
        assertFalse(ConfigManager.canAcceptConnection(100)); // At limit
        assertFalse(ConfigManager.canAcceptConnection(101)); // Above limit

        // Change limit
        ConfigManager.MAX_CONNECTIONS = 1;
        assertTrue(ConfigManager.canAcceptConnection(0));
        assertFalse(ConfigManager.canAcceptConnection(1));
    }

    // ===========================================
    // 11. TESTING STATE TRANSITIONS
    // ===========================================

    @Test
    @Ordr(11)
    @DisplayName("11. Test state transitions")
    void testStateTransitions() {
        ReportService service = new ReportService();

        // Initial state
        ConfigManager.DEBUG_MODE = false;
        String info1 = service.getSystemInfo();
        assertFalse(info1.contains("DEBUG"));

        // Transition to debug mode
        ConfigManager.DEBUG_MODE = true;
        String info2 = service.getSystemInfo();
        assertTrue(info2.contains("DEBUG"));

        // Transition back
        ConfigManager.DEBUG_MODE = false;
        String info3 = service.getSystemInfo();
        assertFalse(info3.contains("DEBUG"));
    }

    // ===========================================
    // 12. COMPLEX SCENARIO WITH MULTIPLE FIELDS
    // ===========================================

    @Test
    @Ordr(12)
    @DisplayName("12. Complex test with multiple field changes")
    void testComplexScenario() {
        ReportService service = new ReportService();

        // Setup test scenario
        ConfigManager.APPLICATION_NAME = "ProductionApp";
        ConfigManager.VERSION = "5.0.0";
        ConfigManager.MAX_CONNECTIONS = 500;
        ConfigManager.DEBUG_MODE = false;

        // Test multiple conditions
        String systemInfo = service.getSystemInfo();
        assertTrue(systemInfo.contains("ProductionApp"));
        assertTrue(systemInfo.contains("5.0.0"));
        assertFalse(systemInfo.contains("DEBUG"));

        assertTrue(service.validateConnectionLimit(250));
        assertFalse(service.validateConnectionLimit(600));
    }

    // ===========================================
    // BEST PRACTICES
    // ===========================================

    /*
     * BEST PRACTICES FOR STATIC FIELDS:
     *
     * 1. ✅ Always restore original values
     *    - Use @BeforeEach and @AfterEach
     *    - Prevents test interference
     *
     * 2. ✅ Use @TestMethodOrder if order matters
     *    - Static fields maintain state across tests
     *    - Order can prevent flaky tests
     *
     * 3. ✅ Consider better design
     *    - Avoid static mutable fields when possible
     *    - Use dependency injection
     *    - Use configuration classes
     *
     * 4. ✅ Document field dependencies
     *    - Make it clear which tests modify static state
     *    - Use descriptive test names
     *
     * 5. ✅ Use reflection for private fields
     *    - Create helper methods
     *    - Handle exceptions properly
     */

    // ===========================================
    // DESIGN ALTERNATIVES TO STATIC FIELDS
    // ===========================================

    /*
     * BETTER DESIGN OPTIONS:
     *
     * Instead of:
     *   public static String APPLICATION_NAME = "MyApp";
     *
     * Consider:
     *
     * 1. Configuration Class (Injectable)
     *    public class AppConfig {
     *        private String applicationName;
     *        // getter/setter
     *    }
     *
     * 2. Builder Pattern
     *    ConfigManager.builder()
     *        .applicationName("MyApp")
     *        .build();
     *
     * 3. Dependency Injection (Spring)
     *    @Value("${app.name}")
     *    private String applicationName;
     *
     * 4. Immutable Configuration
     *    public final class Config {
     *        public final String appName;
     *        public Config(String appName) {
     *            this.appName = appName;
     *        }
     *    }
     */

    // ===========================================
    // COMMON PITFALLS
    // ===========================================

    /*
     * ❌ AVOID:
     *
     * 1. Not restoring original values
     *    - Causes test pollution
     *    - Tests fail in different order
     *
     * 2. Modifying final static fields
     *    - Requires unsafe operations
     *    - Not portable across JVM versions
     *
     * 3. Parallel test execution with static fields
     *    - Race conditions
     *    - Unpredictable results
     *
     * 4. Excessive use of static fields
     *    - Makes code hard to test
     *    - Tight coupling
     *
     * ✅ DO:
     *
     * 1. Save and restore values
     * 2. Use @TestMethodOrder when needed
     * 3. Prefer dependency injection
     * 4. Document static field usage
     */
}
