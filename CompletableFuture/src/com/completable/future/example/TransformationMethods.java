package com.completable.future.example;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

/**
 * 2. TRANSFORMATION METHODS
 *
 * Demonstrates how to transform CompletableFuture results:
 * - thenApply(): Transform the result (Function<T,R>)
 * - thenAccept(): Consume the result (Consumer<T>)
 * - thenRun(): Execute action after completion (Runnable)
 *
 * Each method has three variants:
 * - Non-async: thenApply() - Runs in the completing thread
 * - Async: thenApplyAsync() - Runs in ForkJoinPool.commonPool()
 * - Async with executor: thenApplyAsync(executor) - Runs in custom pool
 */
public class TransformationMethods {

    public static void main(String[] args) {
        System.out.println("=== TRANSFORMATION METHODS ===\n");

       // example1_ThenApply();
        //example2_ThenAccept();
        //example3_ThenRun();
        //example4_ChainingTransformations();
       // example5_AsyncVsNonAsync();
        IntStream.range(0, 10).forEach(i -> example5_AsyncVsNonAsync());
    }

    /**
     * Example 1: thenApply()
     * Transforms the result of a CompletableFuture
     * Similar to map() in Stream API
     * Takes Function<T, R> and returns CompletableFuture<R>
     */
    private static void example1_ThenApply() {
        System.out.println("--- Example 1: thenApply() ---");

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Initial computation in: " + Thread.currentThread().getName());
            return 10;
        }).thenApply(result -> {
            System.out.println("thenApply in: " + Thread.currentThread().getName());
            return result * 2; // Transform: multiply by 2
        });

        Integer result = future.join();
        System.out.println("Final result: " + result); // Output: 20
        System.out.println();
    }

    /**
     * Example 2: thenAccept()
     * Consumes the result without returning anything
     * Similar to forEach() in Stream API
     * Takes Consumer<T> and returns CompletableFuture<Void>
     * Use when you want to perform side-effects
     */
    private static void example2_ThenAccept() {
        System.out.println("--- Example 2: thenAccept() ---");

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            return "Hello World";
        }).thenAccept(result -> {
            // Consume the result - perform side effect
            System.out.println("Consuming result: " + result);
            System.out.println("Result length: " + result.length());
        });

        future.join(); // Wait for completion
        System.out.println("Processing complete\n");
    }

    /**
     * Example 3: thenRun()
     * Executes an action after completion, doesn't use the result
     * Takes Runnable and returns CompletableFuture<Void>
     * Use when you want to execute code after completion regardless of result
     */
    private static void example3_ThenRun() {
        System.out.println("--- Example 3: thenRun() ---");

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Computing...");
            return "Some Result";
        }).thenRun(() -> {
            // Doesn't receive the result, just executes after completion
            System.out.println("Cleanup or logging task executed");
            System.out.println("Result is not available here");
        });

        future.join();
        System.out.println();
    }

    /**
     * Example 4: Chaining Multiple Transformations
     * You can chain multiple transformation operations
     * Each stage depends on the previous one
     */
    private static void example4_ChainingTransformations() {
        System.out.println("--- Example 4: Chaining Transformations ---");

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Step 1: Fetching user ID");
            return 12345;
        })
        .thenApply(userId -> {
            System.out.println("Step 2: Fetching user with ID: " + userId);
            return "User_" + userId; // Fetch user by ID
        })
        .thenApply(username -> {
            System.out.println("Step 3: Converting username to uppercase");
            return username.toUpperCase();
        })
        .thenApply(username -> {
            System.out.println("Step 4: Adding prefix");
            return "PREFIX_" + username;
        })
        .thenAccept(finalResult -> {
            System.out.println("Step 5: Final result: " + finalResult);
        })
        .thenRun(() -> {
            System.out.println("Step 6: Cleanup completed");
        });

        future.join();
        System.out.println();
    }

    /**
     * Example 5: Async vs Non-Async Variants
     *
     * Non-async (thenApply): Runs in the thread that completes the previous stage
     * Async (thenApplyAsync): Runs in a separate thread from common pool
     *
     * Use async when:
     * - The next stage is computationally intensive
     * - You want to ensure parallel execution
     * - The previous stage completes quickly but next stage is slow
     */
    private static void example5_AsyncVsNonAsync() {
        System.out.println("--- Example 5: Async vs Non-Async ---");

        // Non-async: All operations run in same thread (or completing thread)
        System.out.println("NON-ASYNC:");
        CompletableFuture<String> nonAsync = CompletableFuture.supplyAsync(() -> {
            printThread("supplyAsync");
            return "Data";
        }).thenApply(result -> {
        //	sleep(15000); 
            printThread("thenApply");
            return result.toUpperCase();
        }).thenApply(result -> {
            printThread("thenApply 2");
            return result + "_PROCESSED";
        });

        System.out.println("Result: " + nonAsync.join());
        System.out.println();

        // Async: Each operation may run in different threads
        System.out.println("ASYNC:");
        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
            printThread("supplyAsync");
            return "Data";
        }).thenApplyAsync(result -> {

           // sleep(60000); // Simulate delay
            printThread("thenApplyAsync");
            return result.toUpperCase();
        }).thenApplyAsync(result -> {
            printThread("thenApplyAsync 2");
            return result + "_PROCESSED";
        });

        System.out.println("Result: " + async.join());
        System.out.println();
    }

    // Utility method to print current thread
    private static void printThread(String stage) {
        System.out.println(stage + " -> " + Thread.currentThread().getName());
    }

    // Utility method to simulate delay
    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * COMPARISON TABLE:
 *
 * +-------------+------------------+-------------------------+---------------------+
 * | Method      | Input Type       | Return Type             | Use Case            |
 * +-------------+------------------+-------------------------+---------------------+
 * | thenApply   | Function<T,R>    | CompletableFuture<R>    | Transform result    |
 * | thenAccept  | Consumer<T>      | CompletableFuture<Void> | Consume result      |
 * | thenRun     | Runnable         | CompletableFuture<Void> | Execute action      |
 * +-------------+------------------+-------------------------+---------------------+
 *
 * ASYNC VARIANTS:
 * - thenApplyAsync(), thenAcceptAsync(), thenRunAsync()
 * - Execute in separate thread from ForkJoinPool.commonPool()
 * - Can provide custom Executor as second parameter
 *
 * WHEN TO USE WHICH:
 * 1. thenApply: When you need to transform the result to another type
 * 2. thenAccept: When you want to consume the result (e.g., save to DB, log)
 * 3. thenRun: When you want to execute code after completion (e.g., cleanup, metrics)
 *
 * BEST PRACTICES:
 * 1. Use non-async for lightweight operations
 * 2. Use async for CPU-intensive or blocking operations
 * 3. Provide custom executor for blocking I/O operations
 * 4. Chain operations for better readability
 * 5. Avoid deep nesting - use sequential chaining instead
 */
