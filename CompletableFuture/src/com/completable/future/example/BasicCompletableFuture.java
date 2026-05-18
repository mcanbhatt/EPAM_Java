package com.completable.future.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 1. BASIC COMPLETABLE FUTURE
 *
 * Demonstrates the fundamental ways to create and complete CompletableFuture:
 * - supplyAsync(): Creates future that returns a value
 * - runAsync(): Creates future that doesn't return value
 * - completedFuture(): Creates already completed future
 * - Manual completion with complete()
 * - get() vs join() for retrieving results
 */
public class BasicCompletableFuture {

    public static void main(String[] args) {
        System.out.println("=== BASIC COMPLETABLE FUTURE EXAMPLES ===\n");

        example1_SupplyAsync();
        example2_RunAsync();
        example3_CompletedFuture();
        example4_ManualCompletion();
        example5_GetVsJoin();
        example6_IsDoneAndCancel();
    }

    /**
     * Example 1: supplyAsync()
     * Creates a CompletableFuture that asynchronously computes a value
     * Uses Supplier<T> functional interface
     */
    private static void example1_SupplyAsync() {
        System.out.println("--- Example 1: supplyAsync() ---");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Executing in thread: " + Thread.currentThread().getName());
            sleep(1000); // Simulate long-running task
            return "Hello from supplyAsync!";
        });

        // Non-blocking - continues immediately
        System.out.println("Main thread continues: " + Thread.currentThread().getName());

        // Block and get result
        String result = future.join(); // join() doesn't throw checked exception
        System.out.println("Result: " + result);
        System.out.println();
    }

    /**
     * Example 2: runAsync()
     * Creates a CompletableFuture that runs asynchronously but returns no value
     * Uses Runnable functional interface
     * Returns CompletableFuture<Void>
     */
    private static void example2_RunAsync() {
        System.out.println("--- Example 2: runAsync() ---");

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Running async task in: " + Thread.currentThread().getName());
            sleep(500);
            System.out.println("Task completed!");
        });

        System.out.println("Main thread continues...");

        // Wait for completion
        future.join();
        System.out.println("Future completed\n");
    }

    /**
     * Example 3: completedFuture()
     * Creates an already completed future with a given value
     * Useful for testing or when you already have the result
     */
    private static void example3_CompletedFuture() {
        System.out.println("--- Example 3: completedFuture() ---");

        CompletableFuture<String> future = CompletableFuture.completedFuture("Already completed!");

        // This returns immediately as future is already complete
        String result = future.join();
        System.out.println("Result: " + result);
        System.out.println("Is done? " + future.isDone());
        System.out.println();
    }

    /**
     * Example 4: Manual Completion
     * Create an incomplete future and complete it manually
     * Useful for bridging callback-based APIs with CompletableFuture
     */
    private static void example4_ManualCompletion() {
        System.out.println("--- Example 4: Manual Completion ---");

        CompletableFuture<String> future = new CompletableFuture<>();

        // Start a thread that will complete the future
        new Thread(() -> {
            sleep(1000);
            System.out.println("Completing future manually...");
            future.complete("Manually completed result");
        }).start();

        System.out.println("Waiting for manual completion...");
        String result = future.join();
        System.out.println("Result: " + result);
        System.out.println();
    }

    /**
     * Example 5: get() vs join()
     *
     * get():
     * - Throws checked exceptions (ExecutionException, InterruptedException)
     * - Must handle or declare exceptions
     * - Can specify timeout: get(timeout, TimeUnit)
     *
     * join():
     * - Throws unchecked CompletionException
     * - No need to handle checked exceptions
     * - No timeout option
     * - Preferred for chaining operations
     */
    private static void example5_GetVsJoin() {
        System.out.println("--- Example 5: get() vs join() ---");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return "Result";
        });

        // Using get() - requires exception handling
        try {
            String result1 = future.get(); // Blocks until complete
            System.out.println("get() result: " + result1);

            // get() with timeout
            CompletableFuture<String> slowFuture = CompletableFuture.supplyAsync(() -> {
                sleep(2000);
                return "Slow result";
            });
            String result2 = slowFuture.get(3, TimeUnit.SECONDS);
            System.out.println("get(timeout) result: " + result2);

        } catch (InterruptedException | ExecutionException | java.util.concurrent.TimeoutException e) {
            System.err.println("Exception: " + e.getMessage());
        }

        // Using join() - no checked exception handling needed
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Join result");
        String result3 = future2.join(); // Cleaner, throws unchecked exception
        System.out.println("join() result: " + result3);
        System.out.println();
    }

    /**
     * Example 6: Checking Status and Cancellation
     * - isDone(): Check if completed (normally or exceptionally)
     * - isCompletedExceptionally(): Check if completed with exception
     * - isCancelled(): Check if cancelled
     * - cancel(): Attempt to cancel execution
     */
    private static void example6_IsDoneAndCancel() {
        System.out.println("--- Example 6: Status and Cancellation ---");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "Result";
        });

        System.out.println("Is done? " + future.isDone());
        System.out.println("Is cancelled? " + future.isCancelled());

        // Cancel the future
        boolean cancelled = future.cancel(true);
        System.out.println("Cancellation successful? " + cancelled);
        System.out.println("Is cancelled now? " + future.isCancelled());
        System.out.println("Is done now? " + future.isDone());
        System.out.println("Is completed exceptionally? " + future.isCompletedExceptionally());

        // Trying to get result from cancelled future will throw CancellationException
        try {
            future.join();
        } catch (java.util.concurrent.CancellationException e) {
            System.out.println("Caught CancellationException: " + e.getClass().getSimpleName());
        }

        System.out.println();
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
 * KEY TAKEAWAYS:
 *
 * 1. supplyAsync() - Use when you need a return value
 * 2. runAsync() - Use when you don't need a return value
 * 3. completedFuture() - Use for already available values
 * 4. Manual completion - Bridge callback APIs to CompletableFuture
 * 5. join() is preferred over get() for cleaner code
 * 6. Always check isDone() before blocking operations in production
 *
 * THREAD POOL:
 * By default, supplyAsync() and runAsync() use ForkJoinPool.commonPool()
 * You can provide custom Executor as second parameter:
 * CompletableFuture.supplyAsync(() -> "value", customExecutor)
 */
