package com.completable.future.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 3. CHAINING OPERATIONS
 *
 * Demonstrates how to chain and combine multiple CompletableFutures:
 * - thenCompose(): Flattens nested futures (like flatMap)
 * - thenCombine(): Combines two independent futures
 * - thenAcceptBoth(): Consumes results from two futures
 * - runAfterBoth(): Executes action after both complete
 * - applyToEither(): Uses result from whichever completes first
 * - acceptEither(): Consumes result from whichever completes first
 * - runAfterEither(): Runs action after either completes
 */
public class ChainingOperations {

    public static void main(String[] args) {
        System.out.println("=== CHAINING OPERATIONS ===\n");

        example1_ThenCompose();
        example2_ThenCombine();
        example3_ThenAcceptBoth();
        example4_RunAfterBoth();
        example5_ApplyToEither();
        example6_AcceptEither();
        example7_RunAfterEither();
    }

    /**
     * Example 1: thenCompose()
     * Used when the next stage also returns a CompletableFuture
     * Flattens nested CompletableFutures (similar to flatMap in Stream API)
     * Prevents CompletableFuture<CompletableFuture<T>>
     *
     * Use Case: Dependent async operations (result of first is input to second)
     */
    private static void example1_ThenCompose() {
        System.out.println("--- Example 1: thenCompose() ---");

        // Scenario: Get user ID, then fetch user details asynchronously
        CompletableFuture<String> future = getUserId()
            .thenCompose(userId -> getUserDetails(userId));

        String result = future.join();
        System.out.println("User Details: " + result);
        System.out.println();

        // WRONG WAY: Using thenApply would create nested future
        CompletableFuture<CompletableFuture<String>> nested = getUserId()
            .thenApply(userId -> getUserDetails(userId)); // Wrong! Returns nested future

        // Would need to call join() twice:
        // String result = nested.join().join(); // Ugly!
    }

    /**
     * Example 2: thenCombine()
     * Combines results from two INDEPENDENT CompletableFutures
     * Takes BiFunction<T, U, R> to combine results
     * Both futures execute in parallel
     *
     * Use Case: When you need results from two independent operations
     */
    private static void example2_ThenCombine() {
        System.out.println("--- Example 2: thenCombine() ---");

        // Execute two independent API calls in parallel
        CompletableFuture<String> userFuture = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            System.out.println("Fetched user data");
            return "User: John";
        });

        CompletableFuture<String> orderFuture = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            System.out.println("Fetched order data");
            return "Orders: 5";
        });

        // Combine both results
        CompletableFuture<String> combined = userFuture.thenCombine(orderFuture,
            (userData, orderData) -> {
                return userData + ", " + orderData;
            });

        String result = combined.join();
        System.out.println("Combined Result: " + result);
        System.out.println();
    }

    /**
     * Example 3: thenAcceptBoth()
     * Similar to thenCombine but doesn't return a value
     * Takes BiConsumer<T, U> to consume both results
     *
     * Use Case: When you need both results but don't need to return anything
     */
    private static void example3_ThenAcceptBoth() {
        System.out.println("--- Example 3: thenAcceptBoth() ---");

        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return 100;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return 200;
        });

     //    List<Integer> Sum = new ArrayList<>();
        CompletableFuture<Void> result = future1.thenAcceptBoth(future2,
            (result1, result2) -> {
                System.out.println("Result 1: " + result1);
                System.out.println("Result 2: " + result2);
           //     Sum.add(result1 + result2);
                System.out.println("Sum: " + (result1 + result2));
            });

       System.out.println(result.join());
        System.out.println();
    }

    /**
     * Example 4: runAfterBoth()
     * Executes an action after both futures complete
     * Doesn't use results from either future
     * Takes Runnable
     *
     * Use Case: Execute cleanup or notification after multiple operations
     */
    private static void example4_RunAfterBoth() {
        System.out.println("--- Example 4: runAfterBoth() ---");

        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            sleep(800);
            System.out.println("Task 1 completed");
            return "Task1 Result";
        });

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
            sleep(600);
            System.out.println("Task 2 completed");
            return "Task2 Result";
        });

        CompletableFuture<Void> both = task1.runAfterBoth(task2, () -> {
            System.out.println("Both tasks completed - running cleanup");
        });

        both.join();
        System.out.println();
    }

    /**
     * Example 5: applyToEither()
     * Uses the result from whichever future completes first
     * Takes Function<T, R>
     *
     * Use Case: Timeout pattern, redundant service calls, fastest response
     */
    private static void example5_ApplyToEither() {
        System.out.println("--- Example 5: applyToEither() ---");

        // Simulate calling two services - use whichever responds first
        CompletableFuture<String> service1 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "Response from Service 1";
        });

        CompletableFuture<String> service2 = CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return "Response from Service 2";
        });

        CompletableFuture<String> fastest = service1.applyToEither(service2,
            response -> {
                return "Fastest: " + response;
            });

        String result = fastest.join();
        System.out.println(result); // Will use Service 2 (faster)
        System.out.println();
    }

    /**
     * Example 6: acceptEither()
     * Consumes result from whichever completes first
     * Similar to applyToEither but returns void
     * Takes Consumer<T>
     */
    private static void example6_AcceptEither() {
        System.out.println("--- Example 6: acceptEither() ---");

        CompletableFuture<String> cache = CompletableFuture.supplyAsync(() -> {
            sleep(200);
            return "Cache Data";
        });

        CompletableFuture<String> database = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "Database Data";
        });

        CompletableFuture<Void> result = cache.acceptEither(database,
            data -> {
                System.out.println("Using data from fastest source: " + data);
            });

        result.join();
        System.out.println();
    }

    /**
     * Example 7: runAfterEither()
     * Runs action after whichever completes first
     * Doesn't use the result
     * Takes Runnable
     */
    private static void example7_RunAfterEither() {
        System.out.println("--- Example 7: runAfterEither() ---");

        CompletableFuture<Void> operation1 = CompletableFuture.runAsync(() -> {
            sleep(700);
            System.out.println("Operation 1 done");
        });

        CompletableFuture<Void> operation2 = CompletableFuture.runAsync(() -> {
            sleep(400);
            System.out.println("Operation 2 done");
        });

        CompletableFuture<Void> result = operation1.runAfterEither(operation2, () -> {
            System.out.println("First operation completed - starting next phase");
        });

        result.join();
        System.out.println();
    }

    // Helper methods to simulate async operations

    private static CompletableFuture<Integer> getUserId() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching user ID...");
            sleep(500);
            return 12345;
        });
    }

    private static CompletableFuture<String> getUserDetails(Integer userId) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching details for user: " + userId);
            sleep(500);
            return "User{id=" + userId + ", name='John Doe', email='john@example.com'}";
        });
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
 * SUMMARY OF COMBINING METHODS:
 *
 * +------------------+-----------------------------+------------------+------------------------+
 * | Method           | When                        | Input Types      | Use Case               |
 * +------------------+-----------------------------+------------------+------------------------+
 * | thenCompose      | Dependent futures           | Function<T,CF<R>>| Sequential async ops   |
 * | thenCombine      | Both complete, need result  | BiFunction<T,U,R>| Combine 2 results      |
 * | thenAcceptBoth   | Both complete, consume      | BiConsumer<T,U>  | Process 2 results      |
 * | runAfterBoth     | Both complete, no result    | Runnable         | Cleanup after both     |
 * | applyToEither    | First complete, need result | Function<T,R>    | Fastest response       |
 * | acceptEither     | First complete, consume     | Consumer<T>      | Use fastest result     |
 * | runAfterEither   | First complete, no result   | Runnable         | React to first         |
 * +------------------+-----------------------------+------------------+------------------------+
 *
 * KEY DIFFERENCES:
 *
 * thenCompose vs thenApply:
 * - thenApply: For synchronous transformations (T -> R)
 * - thenCompose: For async transformations that return CompletableFuture (T -> CF<R>)
 *
 * thenCombine vs thenCompose:
 * - thenCombine: Two INDEPENDENT futures running in parallel
 * - thenCompose: DEPENDENT futures where second depends on first's result
 *
 * "Both" vs "Either":
 * - "Both" methods: Wait for all futures to complete
 * - "Either" methods: Use result from whichever completes first
 *
 * ASYNC VARIANTS:
 * All methods have async variants (e.g., thenComposeAsync, thenCombineAsync)
 * Use async when the combining operation is computationally expensive
 */
