package com.example;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive guide to TEST ORDERING in JUnit 5
 *
 * By default, JUnit 5 executes tests in an unpredictable order.
 * Sometimes you need specific order for:
 * - Integration tests with dependencies
 * - Tests modifying shared state
 * - Demonstrating test execution flow
 * - Performance testing scenarios
 */
@DisplayName("Test Ordering Examples")
class TestOrderingExampleTest {

    // Shared state to demonstrate test execution order
    private static List<String> executionLog = new ArrayList<>();

    @BeforeAll
    static void setupAll() {
        System.out.println("\n=== Starting Test Execution ===\n");
        executionLog.clear();
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("\n=== Test Execution Order ===");
        executionLog.forEach(log -> System.out.println(log));
        System.out.println("=========================\n");
    }

    // ===========================================
    // 1. METHOD ORDER BY @Order ANNOTATION
    // ===========================================

    /**
     * Use @TestMethodOrder with MethodOrderer.OrderAnnotation.class
     * Then annotate each test with @Order(n)
     */
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("1. Ordering by @Order annotation")
    class OrderByAnnotation {

        @Test
        @Order(1)
        @DisplayName("This runs FIRST")
        void test1_First() {
            executionLog.add("OrderByAnnotation: test1_First");
            System.out.println("Executing: test1_First");
            assertTrue(true);
        }

        @Test
        @Order(3)
        @DisplayName("This runs THIRD")
        void test3_Third() {
            executionLog.add("OrderByAnnotation: test3_Third");
            System.out.println("Executing: test3_Third");
            assertTrue(true);
        }

        @Test
        @Order(2)
        @DisplayName("This runs SECOND")
        void test2_Second() {
            executionLog.add("OrderByAnnotation: test2_Second");
            System.out.println("Executing: test2_Second");
            assertTrue(true);
        }

        @Test
        @Order(4)
        @DisplayName("This runs FOURTH")
        void test4_Fourth() {
            executionLog.add("OrderByAnnotation: test4_Fourth");
            System.out.println("Executing: test4_Fourth");
            assertTrue(true);
        }
    }

    // ===========================================
    // 2. METHOD ORDER BY NAME
    // ===========================================

    /**
     * Tests execute in alphabetical order by method name
     */
    @Nested
    @TestMethodOrder(MethodOrderer.MethodName.class)
    @DisplayName("2. Ordering by method name (alphabetical)")
    class OrderByMethodName {

        @Test
        void test_A_First() {
            executionLog.add("OrderByMethodName: test_A_First");
            System.out.println("Executing: test_A_First");
        }

        @Test
        void test_C_Third() {
            executionLog.add("OrderByMethodName: test_C_Third");
            System.out.println("Executing: test_C_Third");
        }

        @Test
        void test_B_Second() {
            executionLog.add("OrderByMethodName: test_B_Second");
            System.out.println("Executing: test_B_Second");
        }
    }

    // ===========================================
    // 3. DISPLAY NAME ORDER
    // ===========================================

    /**
     * Tests execute in alphabetical order by display name
     */
    @Nested
    @TestMethodOrder(MethodOrderer.DisplayName.class)
    @DisplayName("3. Ordering by display name")
    class OrderByDisplayName {

        @Test
        @DisplayName("A - First Test")
        void methodC() {
            executionLog.add("OrderByDisplayName: A - First Test");
            System.out.println("Executing: A - First Test");
        }

        @Test
        @DisplayName("C - Third Test")
        void methodA() {
            executionLog.add("OrderByDisplayName: C - Third Test");
            System.out.println("Executing: C - Third Test");
        }

        @Test
        @DisplayName("B - Second Test")
        void methodB() {
            executionLog.add("OrderByDisplayName: B - Second Test");
            System.out.println("Executing: B - Second Test");
        }
    }

    // ===========================================
    // 4. RANDOM ORDER
    // ===========================================

    /**
     * Tests execute in random order (good for detecting test dependencies)
     */
    @Nested
    @TestMethodOrder(MethodOrderer.Random.class)
    @DisplayName("4. Random order (changes each run)")
    class RandomOrder {

        @Test
        void testOne() {
            executionLog.add("RandomOrder: testOne");
            System.out.println("Executing: testOne");
        }

        @Test
        void testTwo() {
            executionLog.add("RandomOrder: testTwo");
            System.out.println("Executing: testTwo");
        }

        @Test
        void testThree() {
            executionLog.add("RandomOrder: testThree");
            System.out.println("Executing: testThree");
        }
    }

    // ===========================================
    // 5. REAL-WORLD EXAMPLE: USER LIFECYCLE
    // ===========================================

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("5. Real-world: User lifecycle test")
    class UserLifecycleTest {

        private  User testUser;

        @Test
        @Order(1)
        @DisplayName("1. Create user")
        void createUser() {
            testUser = new User(null, "John Doe", "john@example.com");
            assertNotNull(testUser);
            System.out.println("✓ User created");
        }

        @Test
        @Order(2)
        @DisplayName("2. Activate user")
        void activateUser() {
            assertNotNull(testUser, "User should be created first");
            // Simulate activation
            System.out.println("✓ User activated: " + testUser.getName());
        }

        @Test
        @Order(3)
        @DisplayName("3. Update user")
        void updateUser() {
            assertNotNull(testUser, "User should be created first");
            testUser.setEmail("newemail@example.com");
            assertEquals("newemail@example.com", testUser.getEmail());
            System.out.println("✓ User updated");
        }

        @Test
        @Order(4)
        @DisplayName("4. Deactivate user")
        void deactivateUser() {
            assertNotNull(testUser, "User should be created first");
            System.out.println("✓ User deactivated: " + testUser.getName());
        }

        @Test
        @Order(5)
        @DisplayName("5. Delete user")
        void deleteUser() {
            assertNotNull(testUser, "User should be created first");
            System.out.println("✓ User deleted");
            testUser = null;
        }
    }

    // ===========================================
    // 6. INTEGRATION TEST SCENARIO
    // ===========================================

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("6. Integration test scenario")
    class IntegrationTestScenario {

        private  Ordr ordr;

        @Test
        @Order(1)
        @DisplayName("Step 1: Check inventory")
        void checkInventory() {
            System.out.println("Step 1: Checking inventory...");
            // Simulate inventory check
            assertTrue(true, "Inventory available");
        }

        @Test
        @Order(2)
        @DisplayName("Step 2: Create order")
        void createOrder() {
            System.out.println("Step 2: Creating order...");
            ordr = new Ordr(1L, List.of("item1", "item2"));
            assertNotNull(ordr);
            assertEquals("PENDING", ordr.getStatus());
        }

        @Test
        @Order(3)
        @DisplayName("Step 3: Process payment")
        void processPayment() {
            System.out.println("Step 3: Processing payment...");
            assertNotNull(ordr, "Order must be created first");
            // Simulate payment processing
            assertTrue(true, "Payment processed");
        }

        @Test
        @Order(4)
        @DisplayName("Step 4: Ship order")
        void shipOrder() {
            System.out.println("Step 4: Shipping order...");
            assertNotNull(ordr, "Order must exist");
            ordr.setStatus("SHIPPED");
            assertEquals("SHIPPED", ordr.getStatus());
        }

        @Test
        @Order(5)
        @DisplayName("Step 5: Confirm delivery")
        void confirmDelivery() {
            System.out.println("Step 5: Confirming delivery...");
            assertNotNull(ordr, "Order must exist");
            ordr.setStatus("DELIVERED");
            assertEquals("DELIVERED", ordr.getStatus());
        }
    }

    // ===========================================
    // 7. PERFORMANCE TESTING ORDER
    // ===========================================

    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("7. Performance testing with warm-up")
    class PerformanceTestOrder {

        @Test
        @Order(1)
        @DisplayName("Warm-up: Initialize caches")
        void warmUp() {
            System.out.println("Warming up system...");
            // Warm-up logic
        }

        @Test
        @Order(2)
        @DisplayName("Measure: Small dataset performance")
        void testSmallDataset() {
            System.out.println("Testing small dataset...");
            // Performance test
        }

        @Test
        @Order(3)
        @DisplayName("Measure: Medium dataset performance")
        void testMediumDataset() {
            System.out.println("Testing medium dataset...");
            // Performance test
        }

        @Test
        @Order(4)
        @DisplayName("Measure: Large dataset performance")
        void testLargeDataset() {
            System.out.println("Testing large dataset...");
            // Performance test
        }

        @Test
        @Order(5)
        @DisplayName("Cleanup: Reset state")
        void cleanup() {
            System.out.println("Cleaning up...");
            // Cleanup logic
        }
    }

    // ===========================================
    // CUSTOM ORDER EXAMPLE
    // ===========================================

    /**
     * You can create custom ordering by implementing MethodOrderer
     */
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @DisplayName("8. Custom priority ordering")
    class CustomPriorityOrder {

        @Test
        @Order(10)
        @DisplayName("Low priority test")
        void lowPriorityTest() {
            System.out.println("Low priority test executing");
        }

        @Test
        @Order(1)
        @DisplayName("High priority test")
        void highPriorityTest() {
            System.out.println("High priority test executing");
        }

        @Test
        @Order(5)
        @DisplayName("Medium priority test")
        void mediumPriorityTest() {
            System.out.println("Medium priority test executing");
        }
    }

    // ===========================================
    // BEST PRACTICES
    // ===========================================

    /*
     * WHEN TO USE TEST ORDERING:
     *
     * ✅ DO use ordering for:
     * 1. Integration tests with dependencies
     * 2. End-to-end scenario tests
     * 3. Performance tests with warm-up
     * 4. Demo/documentation tests
     * 5. Database migration tests
     * 6. State machine testing
     *
     * ❌ AVOID ordering for:
     * 1. Unit tests (should be independent)
     * 2. Tests that can run in parallel
     * 3. Regular business logic tests
     *
     * GOLDEN RULE:
     * - Tests should be independent when possible
     * - Use ordering only when truly necessary
     * - Document why ordering is required
     */

    // ===========================================
    // ORDERER OPTIONS SUMMARY
    // ===========================================

    /*
     * AVAILABLE ORDERERS:
     *
     * 1. MethodOrderer.OrderAnnotation.class
     *    - Use @Order(n) on each test
     *    - Most explicit and readable
     *    - Best for: Integration tests, scenarios
     *
     * 2. MethodOrderer.MethodName.class
     *    - Alphabetical by method name
     *    - Simple but naming can be awkward
     *    - Best for: Quick ordering without annotations
     *
     * 3. MethodOrderer.DisplayName.class
     *    - Alphabetical by @DisplayName
     *    - More readable than method names
     *    - Best for: Documentation tests
     *
     * 4. MethodOrderer.Random.class
     *    - Random order each run
     *    - Helps detect dependencies
     *    - Best for: Finding test coupling issues
     *
     * 5. Custom MethodOrderer
     *    - Implement your own ordering logic
     *    - Full control
     *    - Best for: Complex custom requirements
     */

    // ===========================================
    // CONFIGURATION
    // ===========================================

    /*
     * GLOBAL CONFIGURATION (junit-platform.properties):
     *
     * Create file: src/test/resources/junit-platform.properties
     *
     * Content:
     * junit.jupiter.testmethod.order.default = \
     *     org.junit.jupiter.api.MethodOrderer$OrderAnnotation
     *
     * This sets default ordering for all tests
     */
}
