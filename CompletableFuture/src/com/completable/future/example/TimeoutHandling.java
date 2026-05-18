package com.completable.future.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 7. TIMEOUT HANDLING (Java 9+)
 *
 * Java 9 introduced timeout methods for CompletableFuture:
 * - orTimeout(): Completes exceptionally if timeout exceeded
 * - completeOnTimeout(): Completes with default value if timeout exceeded
 * - get(timeout, unit): Traditional blocking timeout (Java 8+)
 *
 * These methods help prevent indefinite waiting and implement
 * timeout patterns for external service calls.
 *
 * NOTE: If running Java 8, the timeout methods will not compile.
 * Use get(timeout, unit) or implement custom timeout logic.
 */
public class TimeoutHandling {

    public static void main(String[] args) {
        System.out.println("=== TIMEOUT HANDLING ===\n");

        // Check Java version
        String javaVersion = System.getProperty("java.version");
        System.out.println("Java Version: " + javaVersion);
        System.out.println();

          example1_GetWithTimeout();
      //  example2_CustomTimeoutPattern();

        // Examples 3-7 require Java 9+
        // Uncomment if running Java 9 or higher
        // example3_OrTimeout();
        // example4_CompleteOnTimeout();
        // example5_TimeoutWithExceptionHandling();
         example6_TimeoutChain();
         example7_MultipleTimeouts();

        System.out.println("\nNote: Examples 3-7 require Java 9+ for orTimeout() and completeOnTimeout()");
    }

    /**
     * Example 1: get() with Timeout (Java 8+)
     * Traditional way to implement timeout
     * Throws TimeoutException if not completed in time
     *
     * Limitation: Blocks the calling thread
     */
    private static void example1_GetWithTimeout() {
        System.out.println("--- Example 1: get() with Timeout ---");

        // Fast operation - completes before timeout
        CompletableFuture<String> fast = CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return "Fast result";
        });

        try {
            String result = fast.get(2, TimeUnit.SECONDS);
            System.out.println("Fast result: " + result);
        } catch (TimeoutException e) {
            System.out.println("Operation timed out");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Slow operation - exceeds timeout
        CompletableFuture<String> slow = CompletableFuture.supplyAsync(() -> {
            sleep(3000);
            return "Slow result";
        });

        try {
            String result = slow.get(1, TimeUnit.SECONDS);
            System.out.println("Slow result: " + result);
        } catch (TimeoutException e) {
            System.out.println("Operation timed out (as expected)");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * Example 2: Custom Timeout Pattern (Java 8+)
     * Implement timeout using anyOf() and delayed future
     * Non-blocking approach
     */
    private static void example2_CustomTimeoutPattern() {
        System.out.println("--- Example 2: Custom Timeout Pattern ---");

        CompletableFuture<String> operation = CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "Operation result";
        });

        // Create a timeout future
        CompletableFuture<String> timeout = new CompletableFuture<>();
        scheduleTimeout(timeout, 1000, "Timeout exceeded!");

        // Race between operation and timeout
        CompletableFuture<String> result = CompletableFuture.anyOf(operation, timeout)
            .thenApply(obj -> (String) obj)
            .exceptionally(ex -> "Error: " + ex.getMessage());

        System.out.println("Result: " + result.join());
        System.out.println();
    }

    /**
     * Example 3: orTimeout() (Java 9+)
     * Completes exceptionally with TimeoutException if timeout exceeded
     * Non-blocking and composable
     *
     * UNCOMMENT IF RUNNING JAVA 9+
     */
    
    private static void example3_OrTimeout() {
        System.out.println("--- Example 3: orTimeout() ---");

        // Operation that times out
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(3000);
            return "Result";
        }).orTimeout(1, TimeUnit.SECONDS);

        future.exceptionally(ex -> {
            if (ex instanceof TimeoutException) {
                return "Operation timed out";
            }
            return "Error: " + ex.getMessage();
        }).thenAccept(result -> {
            System.out.println("Result: " + result);
        }).join();

        System.out.println();
    }
    

    /**
     * Example 4: completeOnTimeout() (Java 9+)
     * Completes with a default value if timeout exceeded
     * Does not throw exception - provides fallback
     *
     * UNCOMMENT IF RUNNING JAVA 9+
     */
    
    private static void example4_CompleteOnTimeout() {
        System.out.println("--- Example 4: completeOnTimeout() ---");

        // Operation that times out but returns default value
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "Actual result";
        }).completeOnTimeout("Default value", 1, TimeUnit.SECONDS);

        String result = future.join();
        System.out.println("Result: " + result); // Will print "Default value"

        System.out.println();
    }
    

    /**
     * Example 5: Timeout with Exception Handling (Java 9+)
     * Combining timeout with error handling
     *
     * UNCOMMENT IF RUNNING JAVA 9+
     */
    
    private static void example5_TimeoutWithExceptionHandling() {
        System.out.println("--- Example 5: Timeout with Exception Handling ---");

        CompletableFuture<String> future = fetchDataFromAPI()
            .orTimeout(2, TimeUnit.SECONDS)
            .exceptionally(ex -> {
                if (ex.getCause() instanceof TimeoutException) {
                    System.out.println("API call timed out, using cache");
                    return fetchFromCache();
                }
                System.out.println("API call failed: " + ex.getMessage());
                return "Error response";
            });

        System.out.println("Final result: " + future.join());
        System.out.println();
    }
    

    /**
     * Example 6: Timeout in Chain (Java 9+)
     * Apply timeout to specific stages in a chain
     *
     * UNCOMMENT IF RUNNING JAVA 9+
     */
    
    private static void example6_TimeoutChain() {
        System.out.println("--- Example 6: Timeout in Chain ---");

        CompletableFuture<String> pipeline = CompletableFuture
            .supplyAsync(() -> {
                System.out.println("Step 1: Fetch data");
                sleep(500);
                return "Data";
            })
            .orTimeout(1, TimeUnit.SECONDS) // Timeout on fetch
            .thenApplyAsync(data -> {
                System.out.println("Step 2: Process data");
                sleep(5000);
                return data.toUpperCase();
            })
            .orTimeout(2, TimeUnit.SECONDS) // Overall timeout
            .exceptionally(ex -> {
            	   Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
                return "Failed: " + cause.getClass().getSimpleName();
            });

        System.out.println("Result: " + pipeline.join());
        System.out.println();
    }
    

    /**
     * Example 7: Multiple Service Calls with Different Timeouts (Java 9+)
     *
     * UNCOMMENT IF RUNNING JAVA 9+
     */
    
    private static void example7_MultipleTimeouts() {
        System.out.println("--- Example 7: Multiple Timeouts ---");

        // Fast service - short timeout
        CompletableFuture<String> cacheLookup = CompletableFuture
            .supplyAsync(() -> {
                sleep(800);
                return "Cache data";
            })
            .completeOnTimeout("Cache miss", 500, TimeUnit.MILLISECONDS);

        // Slow service - longer timeout
        CompletableFuture<String> databaseQuery = CompletableFuture
            .supplyAsync(() -> {
                sleep(1500);
                return "Database data";
            })
            .completeOnTimeout("DB unavailable", 2, TimeUnit.SECONDS);

        // External API - medium timeout with fallback
        CompletableFuture<String> apiCall = CompletableFuture
            .supplyAsync(() -> {
                sleep(1200);
                return "API data";
            })
            .orTimeout(1, TimeUnit.SECONDS)
            .exceptionally(ex -> "API timeout");

        // Combine all results
        CompletableFuture<String> combined = CompletableFuture.allOf(
            cacheLookup, databaseQuery, apiCall
        ).thenApply(v -> {
            return String.format("Cache: %s, DB: %s, API: %s",
                cacheLookup.join(),
                databaseQuery.join(),
                apiCall.join());
        });

        System.out.println(combined.join());
        System.out.println();
    }
    

    // Helper methods

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Custom timeout implementation for Java 8
     */
    private static void scheduleTimeout(CompletableFuture<String> future, long timeout, String message) {
        new Thread(() -> {
            sleep((int) timeout);
            future.completeExceptionally(new TimeoutException(message));
        }).start();
    }

    private static CompletableFuture<String> fetchDataFromAPI() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(3000); // Slow API
            return "API Response";
        });
    }

    private static String fetchFromCache() {
        return "Cached data";
    }
}

/**
 * TIMEOUT METHODS COMPARISON:
 *
 * +---------------------+------------------+------------------------+------------------+
 * | Method              | Java Version     | On Timeout             | Return Type      |
 * +---------------------+------------------+------------------------+------------------+
 * | get(timeout, unit)  | Java 8+          | Throws TimeoutException| T (blocking)     |
 * | orTimeout()         | Java 9+          | Throws TimeoutException| CF<T>            |
 * | completeOnTimeout() | Java 9+          | Returns default value  | CF<T>            |
 * +---------------------+------------------+------------------------+------------------+
 *
 * WHEN TO USE WHICH:
 *
 * 1. get(timeout, unit):
 *    - Java 8 compatibility
 *    - Blocking acceptable
 *    - Simple timeout needed
 *    - Not composable with other stages
 *
 * 2. orTimeout():
 *    - Java 9+
 *    - Want to propagate timeout as exception
 *    - Need to handle timeout differently from other errors
 *    - Composable with exception handling
 *
 * 3. completeOnTimeout():
 *    - Java 9+
 *    - Have a reasonable default value
 *    - Want graceful degradation
 *    - Don't want exception handling
 *
 * TIMEOUT PATTERNS:
 *
 * 1. Single Operation Timeout:
 *    ```java
 *    future.orTimeout(5, TimeUnit.SECONDS)
 *    ```
 *
 * 2. Timeout with Fallback:
 *    ```java
 *    future.completeOnTimeout(defaultValue, 5, TimeUnit.SECONDS)
 *    ```
 *
 * 3. Timeout with Retry:
 *    ```java
 *    future.orTimeout(5, TimeUnit.SECONDS)
 *          .exceptionally(ex -> retry())
 *    ```
 *
 * 4. Timeout with Alternative Source:
 *    ```java
 *    primary.orTimeout(2, TimeUnit.SECONDS)
 *           .exceptionally(ex -> fetchFromBackup().join())
 *    ```
 *
 * 5. Cascading Timeouts:
 *    ```java
 *    step1.orTimeout(1, TimeUnit.SECONDS)
 *         .thenCompose(r -> step2.orTimeout(2, TimeUnit.SECONDS))
 *    ```
 *
 * BEST PRACTICES:
 *
 * 1. Always set timeouts for external service calls
 * 2. Choose timeout values based on SLA requirements
 * 3. Different operations may need different timeouts
 * 4. Log timeout events for monitoring
 * 5. Have fallback strategies (cache, defaults, backup services)
 * 6. Consider retry logic for transient failures
 * 7. Test timeout scenarios in integration tests
 * 8. Monitor timeout rates in production
 *
 * TIMEOUT GUIDELINES:
 *
 * Operation Type          | Recommended Timeout
 * ----------------------- | -------------------
 * Cache lookup            | 50-100ms
 * Database query          | 1-5 seconds
 * External API call       | 5-30 seconds
 * Batch processing        | Minutes
 * Background jobs         | Hours
 *
 * CANCELLATION NOTE:
 *
 * Timeouts don't cancel the underlying operation!
 * The future completes exceptionally, but the async task
 * continues running. If you need true cancellation:
 * - Use interruption flags
 * - Design operations to be cancellable
 * - Check Thread.interrupted() in long-running tasks
 */
