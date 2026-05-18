package com.completable.future.example;

import java.util.concurrent.CompletableFuture;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

/**
 * 5. WORKING WITH MULTIPLE FUTURES
 *
 * Demonstrates handling multiple CompletableFutures:
 * - allOf(): Wait for all futures to complete
 * - anyOf(): Wait for any future to complete
 * - Collecting results from multiple futures
 * - Parallel processing patterns
 */
public class MultipleFutures {

    public static void main(String[] args) {
        System.out.println("=== MULTIPLE FUTURES ===\n");

        example1_AllOf_Basic();
        example2_AllOf_CollectResults();
        example3_AllOf_WithExceptions();
        example4_AnyOf_Basic();
        example5_AnyOf_RaceCondition();
        example6_ParallelProcessing();
        example7_BatchProcessing();
    }

    /**
     * Example 1: allOf() - Basic Usage
     * Waits for all futures to complete
     * Returns CompletableFuture<Void>
     * Does NOT return results (must collect separately)
     *
     * Use Case: Execute multiple independent tasks in parallel
     */
    private static void example1_AllOf_Basic() {
        System.out.println("--- Example 1: allOf() Basic ---");

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            System.out.println("Task 1 completed");
            return "Result 1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            sleep(800);
            System.out.println("Task 2 completed");
            return "Result 2";
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            sleep(600);
            System.out.println("Task 3 completed");
            return "Result 3";
        });

        // Wait for all to complete
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(future1, future2, future3);

        allFutures.join(); // Blocks until all complete
        System.out.println("All tasks completed!");
        System.out.println();
    }

    /**
     * Example 2: allOf() with Result Collection
     * Collect results from all futures after they complete
     *
     * Pattern: Use allOf() then map to collect results
     */
    private static void example2_AllOf_CollectResults() {
        System.out.println("--- Example 2: allOf() with Results ---");

        List<CompletableFuture<String>> futures = Arrays.asList(
            CompletableFuture.supplyAsync(() -> {
                sleep(500);
                return "Apple";
            }),
            CompletableFuture.supplyAsync(() -> {
                sleep(300);
                return "Banana";
            }),
            CompletableFuture.supplyAsync(() -> {
                sleep(400);
                return "Cherry";
            })
        );

        // Convert List<CompletableFuture<T>> to CompletableFuture<List<T>>
        CompletableFuture<List<String>> allResults = CompletableFuture
            .allOf(futures.toArray(new CompletableFuture[0]))
            .thenApply(v ->
                futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList())
            );

        List<String> results = allResults.join();
        System.out.println("All results: " + results);
        System.out.println();
    }

    /**
     * Example 3: allOf() with Exception Handling
     * If any future fails, allOf() completes exceptionally
     * Need to handle exceptions for individual futures
     */
    private static void example3_AllOf_WithExceptions() {
        System.out.println("--- Example 3: allOf() with Exceptions ---");

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            sleep(300);
            return "Success 1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            sleep(200);
            throw new RuntimeException("Task 2 failed");
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            sleep(100);
            return "Success 3";
        });

        // Handle exceptions individually
        CompletableFuture<String> safe1 = future1.exceptionally(ex -> "Default 1");
        CompletableFuture<String> safe2 = future2.exceptionally(ex -> "Default 2 (recovered)");
        CompletableFuture<String> safe3 = future3.exceptionally(ex -> "Default 3");

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(safe1, safe2, safe3);

        allFutures.join();

        System.out.println("Result 1: " + safe1.join());
        System.out.println("Result 2: " + safe2.join());
        System.out.println("Result 3: " + safe3.join());
        System.out.println();
    }

    /**
     * Example 4: anyOf() - Basic Usage
     * Completes when ANY future completes (success or failure)
     * Returns CompletableFuture<Object>
     *
     * Use Case: Timeout patterns, fastest response, redundant services
     */
    private static void example4_AnyOf_Basic() {
        System.out.println("--- Example 4: anyOf() Basic ---");

        CompletableFuture<String> slow = CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "Slow response";
        });

        CompletableFuture<String> fast = CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return "Fast response";
        });

        CompletableFuture<String> medium = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "Medium response";
        });

        // Returns as soon as any completes
        CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(slow, fast, medium);

        Object result = anyFuture.join();
        System.out.println("First to complete: " + result);
        System.out.println();
    }

    /**
     * Example 5: anyOf() - Race Multiple Services
     * Practical example: Call multiple services, use fastest response
     */
    private static void example5_AnyOf_RaceCondition() {
        System.out.println("--- Example 5: anyOf() Race Services ---");

        // Simulate calling multiple service endpoints
        CompletableFuture<String> service1 = callService("Service-1", 800);
        CompletableFuture<String> service2 = callService("Service-2", 1200);
        CompletableFuture<String> service3 = callService("Service-3", 500);

        CompletableFuture<Object> fastest = CompletableFuture.anyOf(service1, service2, service3);

        fastest.thenAccept(result -> {
            System.out.println("Using response from: " + result);
        }).join();

        System.out.println();
    }

    /**
     * Example 6: Parallel Processing of Collection
     * Process a collection of items in parallel
     */
    private static void example6_ParallelProcessing() {
        System.out.println("--- Example 6: Parallel Processing ---");

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Process each number in parallel
        List<CompletableFuture<Integer>> futures = numbers.stream()
            .map(num -> CompletableFuture.supplyAsync(() -> {
                sleep(100);
                System.out.println("Processing: " + num);
                return num * num; // Square the number
            }))
            .collect(Collectors.toList());

        // Wait for all and collect results
        CompletableFuture<List<Integer>> allResults = CompletableFuture
            .allOf(futures.toArray(new CompletableFuture[0]))
            .thenApply(v ->
                futures.stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList())
            );

        List<Integer> results = allResults.join();
        System.out.println("Squared results: " + results);
        System.out.println();
    }

    /**
     * Example 7: Batch Processing with Timeouts
     * Process items in batches with timeout handling
     */
    private static void example7_BatchProcessing() {
        System.out.println("--- Example 7: Batch Processing ---");

        List<String> userIds = Arrays.asList("user1", "user2", "user3", "user4", "user5");

        System.out.println("Fetching data for " + userIds.size() + " users in parallel...");

        // Fetch user data in parallel
        List<CompletableFuture<String>> userFutures = userIds.stream()
            .map(userId -> fetchUserData(userId))
            .collect(Collectors.toList());

        // Collect all results
        CompletableFuture<List<String>> allUsers = CompletableFuture
            .allOf(userFutures.toArray(new CompletableFuture[0]))
            .thenApply(v ->
                userFutures.stream()
                    .map(future -> future.join())
                    .collect(Collectors.toList())
            );

        List<String> userData = allUsers.join();
        System.out.println("Fetched data: " + userData);
        System.out.println();
    }

    // Helper methods

    private static CompletableFuture<String> callService(String serviceName, int delay) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(delay);
            System.out.println(serviceName + " responded after " + delay + "ms");
            return serviceName;
        });
    }

    private static CompletableFuture<String> fetchUserData(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(300 + (int)(Math.random() * 200)); // Simulate variable latency
            return "Data for " + userId;
        }).exceptionally(ex -> "Error fetching " + userId);
    }

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * SUMMARY:
 *
 * +----------+---------------------------+----------------------+---------------------------+
 * | Method   | Completes When            | Return Type          | Use Case                  |
 * +----------+---------------------------+----------------------+---------------------------+
 * | allOf()  | All futures complete      | CF<Void>             | Parallel independent tasks|
 * | anyOf()  | Any future completes      | CF<Object>           | Fastest response, timeout |
 * +----------+---------------------------+----------------------+---------------------------+
 *
 * COLLECTING RESULTS FROM allOf():
 *
 * Pattern:
 * ```java
 * List<CF<T>> futures = ...;
 * CF<List<T>> results = CF.allOf(futures.toArray(new CF[0]))
 *     .thenApply(v -> futures.stream()
 *         .map(CF::join)
 *         .collect(Collectors.toList()));
 * ```
 *
 * IMPORTANT NOTES:
 *
 * 1. allOf() returns CompletableFuture<Void>
 *    - Doesn't give you results directly
 *    - Must call join() on individual futures
 *
 * 2. anyOf() returns CompletableFuture<Object>
 *    - Need to cast result to expected type
 *    - Other futures continue running (not cancelled)
 *
 * 3. Exception Handling:
 *    - allOf() fails if ANY future fails
 *    - Use exceptionally() on individual futures for resilience
 *
 * 4. Performance:
 *    - All futures start immediately (not sequential)
 *    - Total time = max(individual times), not sum
 *
 * COMMON PATTERNS:
 *
 * 1. Parallel API Calls:
 *    - Fetch multiple resources simultaneously
 *    - Reduce total latency
 *
 * 2. Fan-out / Fan-in:
 *    - Split work into parallel tasks
 *    - Combine results at the end
 *
 * 3. Timeout Pattern:
 *    - Use anyOf() with timeout future
 *    - Cancel slow operations
 *
 * 4. Redundant Calls:
 *    - Call multiple services
 *    - Use fastest response
 *
 * BEST PRACTICES:
 *
 * 1. Handle exceptions individually before allOf()
 * 2. Use custom thread pool for blocking operations
 * 3. Consider memory with large number of futures
 * 4. Cancel remaining futures after anyOf() if needed
 * 5. Add timeouts for long-running operations
 * 6. Monitor thread pool saturation
 */
