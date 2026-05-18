package com.completable.future.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * 6. ASYNC VARIATIONS
 *
 * Demonstrates the difference between async and non-async methods:
 * - Non-async methods (e.g., thenApply): Execute in completing thread
 * - Async methods (e.g., thenApplyAsync): Execute in ForkJoinPool.commonPool()
 * - Async with Executor: Execute in custom thread pool
 *
 * Understanding thread execution is crucial for:
 * - Performance optimization
 * - Avoiding blocking the common pool
 * - Resource management
 */


public class AsyncVariations {
	


	    public static void main(String[] args) {
	        System.out.println("=== ASYNC VARIATIONS ===\n");

	        example1_NonAsyncExecution();
	       // example2_AsyncExecution();
	        ///example3_CustomExecutor();
	        //example4_BlockingOperations();
	        //example5_MixedAsyncNonAsync();
	        //example6_CommonPoolSize();
	        //example7_WhenToUseAsync();

	        System.out.println("\nShutting down executors...");
	    }

	    /**
	     * Example 1: Non-Async Execution
	     * Non-async methods run in the thread that completes the previous stage
	     *
	     * Characteristics:
	     * - No additional thread switching overhead
	     * - May run in calling thread or completing thread
	     * - Good for lightweight operations
	     */
	    private static void example1_NonAsyncExecution() {
	        System.out.println("--- Example 1: Non-Async Execution ---");

	        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
	            printThread("supplyAsync");
	            return "Data";
	        })
	        .thenApply(data -> {
	            printThread("thenApply (non-async)");
	            return data.toUpperCase();
	        })
	        .thenApply(data -> {
	            printThread("thenApply 2 (non-async)");
	            return data + "_PROCESSED";
	        });

	       System.out.println(future.join());
	        System.out.println();
	    }

	    /**
	     * Example 2: Async Execution
	     * Async methods always run in ForkJoinPool.commonPool() by default
	     *
	     * Characteristics:
	     * - Ensures parallel execution
	     * - Thread switching overhead
	     * - Good for CPU-intensive or potentially blocking operations
	     */
	    private static void example2_AsyncExecution() {
	        System.out.println("--- Example 2: Async Execution ---");

	        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
	            printThread("supplyAsync");
	            return "Data";
	        })
	        .thenApplyAsync(data -> {
	            printThread("thenApplyAsync");
	            sleep(100); // Simulate work
	            return data.toUpperCase();
	        })
	        .thenApplyAsync(data -> {
	            printThread("thenApplyAsync 2");
	            sleep(100); // Simulate work
	            return data + "_PROCESSED";
	        });

	        future.join();
	        System.out.println();
	    }

	    /**
	     * Example 3: Custom Executor
	     * Provide your own thread pool for better control
	     *
	     * Use Cases:
	     * - Blocking I/O operations (database, HTTP calls)
	     * - Different thread pool sizes for different workloads
	     * - Thread naming for debugging
	     * - Isolation from common pool
	     */
	    private static void example3_CustomExecutor() {
	        System.out.println("--- Example 3: Custom Executor ---");

	        ExecutorService customExecutor = Executors.newFixedThreadPool(3, r -> {
	            Thread t = new Thread(r);
	            t.setName("CustomPool-" + t.getId());
	            return t;
	        });

	        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
	            printThread("supplyAsync with custom executor");
	            return "Data";
	        }, customExecutor)
	        .thenApplyAsync(data -> {
	            printThread("thenApplyAsync with custom executor");
	            return data.toUpperCase();
	        }, customExecutor);

	        String result = future.join();
	        System.out.println("Result: " + result);

	        customExecutor.shutdown();
	        System.out.println();
	    }

	    /**
	     * Example 4: Blocking Operations
	     * IMPORTANT: Use custom executor for blocking operations
	     * DO NOT block the common pool - it's shared across the application
	     *
	     * Blocking operations include:
	     * - Database queries
	     * - HTTP requests
	     * - File I/O
	     * - Thread.sleep()
	     */
	    private static void example4_BlockingOperations() {
	        System.out.println("--- Example 4: Blocking Operations ---");

	        // BAD: Blocking in common pool
	        System.out.println("BAD: Blocking common pool");
	        CompletableFuture<String> bad = CompletableFuture.supplyAsync(() -> {
	            printThread("Blocking common pool (BAD)");
	            sleep(1000); // This blocks a common pool thread
	            return "Bad practice";
	        });

	        // GOOD: Use separate thread pool for blocking operations
	        System.out.println("\nGOOD: Blocking in separate pool");
	        ExecutorService blockingExecutor = Executors.newCachedThreadPool(r -> {
	            Thread t = new Thread(r);
	            t.setName("BlockingPool-" + t.getId());
	            return t;
	        });

	        CompletableFuture<String> good = CompletableFuture.supplyAsync(() -> {
	            printThread("Blocking separate pool (GOOD)");
	            sleep(1000); // OK to block here
	            return simulateDatabaseCall();
	        }, blockingExecutor);

	        System.out.println("Bad result: " + bad.join());
	        System.out.println("Good result: " + good.join());

	        blockingExecutor.shutdown();
	        System.out.println();
	    }

	    /**
	     * Example 5: Mixing Async and Non-Async
	     * You can mix both approaches based on operation characteristics
	     */
	    private static void example5_MixedAsyncNonAsync() {
	        System.out.println("--- Example 5: Mixed Async/Non-Async ---");

	        ExecutorService ioExecutor = Executors.newFixedThreadPool(2, r -> {
	            Thread t = new Thread(r);
	            t.setName("IO-Pool-" + t.getId());
	            return t;
	        });

	        CompletableFuture<String> future = CompletableFuture
	            .supplyAsync(() -> {
	                printThread("1. Fetch from DB (blocking)");
	                sleep(300);
	                return "DB_Data";
	            }, ioExecutor) // Use IO pool for blocking
	            .thenApply(data -> {
	                printThread("2. Transform (lightweight)");
	                return data.toLowerCase(); // Non-blocking, use same thread
	            })
	            .thenApplyAsync(data -> {
	                printThread("3. Heavy computation");
	                sleep(200); // CPU-intensive work
	                return data + "_processed";
	            }) // Use common pool for CPU work
	            .thenApply(data -> {
	                printThread("4. Final transform (lightweight)");
	                return data.toUpperCase();
	            });

	        System.out.println("Result: " + future.join());
	        ioExecutor.shutdown();
	        System.out.println();
	    }

	    /**
	     * Example 6: Common Pool Size
	     * ForkJoinPool.commonPool() size = Runtime.availableProcessors() - 1
	     * Minimum size is 1
	     */
	    private static void example6_CommonPoolSize() {
	        System.out.println("--- Example 6: Common Pool Size ---");

	        int commonPoolParallelism = ForkJoinPool.commonPool().getParallelism();
	        int availableProcessors = Runtime.getRuntime().availableProcessors();

	        System.out.println("Available processors: " + availableProcessors);
	        System.out.println("Common pool parallelism: " + commonPoolParallelism);
	        System.out.println("Common pool size formula: max(availableProcessors - 1, 1)");
	        System.out.println();

	        // Can be configured with system property:
	        // -Djava.util.concurrent.ForkJoinPool.common.parallelism=N
	    }

	    /**
	     * Example 7: Decision Guide - When to Use Async
	     */
	    private static void example7_WhenToUseAsync() {
	        System.out.println("--- Example 7: When to Use Async ---");

	        System.out.println("Use NON-ASYNC (thenApply) when:");
	        System.out.println("  - Operation is lightweight (< 1ms)");
	        System.out.println("  - Simple transformations");
	        System.out.println("  - No blocking operations");
	        System.out.println("  - Want to minimize thread context switching");
	        System.out.println();

	        System.out.println("Use ASYNC (thenApplyAsync) when:");
	        System.out.println("  - CPU-intensive operations");
	        System.out.println("  - Want guaranteed parallel execution");
	        System.out.println("  - Previous stage completes quickly but next is slow");
	        System.out.println();

	        System.out.println("Use ASYNC with CUSTOM EXECUTOR when:");
	        System.out.println("  - Blocking I/O (database, HTTP, files)");
	        System.out.println("  - Long-running operations");
	        System.out.println("  - Need to isolate from common pool");
	        System.out.println("  - Different workload characteristics");
	        System.out.println();
	    }

	    // Helper methods

	    private static void printThread(String message) {
	        System.out.printf("%-40s | Thread: %s%n", message, Thread.currentThread().getName());
	    }

	    private static void sleep(int milliseconds) {
	        try {
	            Thread.sleep(milliseconds);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    }

	    private static String simulateDatabaseCall() {
	        sleep(500);
	        return "Database Result";
	    }
	}

	/**
	 * ASYNC METHODS SUMMARY:
	 *
	 * Every callback method has 3 variants:
	 *
	 * +----------------------+---------------------------+--------------------------------+
	 * | Variant              | Execution Thread          | Use Case                       |
	 * +----------------------+---------------------------+--------------------------------+
	 * | method()             | Completing thread         | Lightweight, non-blocking      |
	 * | methodAsync()        | ForkJoinPool.commonPool() | CPU-intensive, parallel work   |
	 * | methodAsync(executor)| Custom executor           | Blocking I/O, isolation        |
	 * +----------------------+---------------------------+--------------------------------+
	 *
	 * METHODS WITH ASYNC VARIANTS:
	 * - thenApply / thenApplyAsync
	 * - thenAccept / thenAcceptAsync
	 * - thenRun / thenRunAsync
	 * - thenCompose / thenComposeAsync
	 * - thenCombine / thenCombineAsync
	 * - thenAcceptBoth / thenAcceptBothAsync
	 * - runAfterBoth / runAfterBothAsync
	 * - applyToEither / applyToEitherAsync
	 * - acceptEither / acceptEitherAsync
	 * - runAfterEither / runAfterEitherAsync
	 * - handle / handleAsync
	 * - whenComplete / whenCompleteAsync
	 *
	 * THREAD POOL GUIDELINES:
	 *
	 * 1. ForkJoinPool.commonPool():
	 *    - Default for *Async() methods
	 *    - Size: availableProcessors - 1
	 *    - Shared across application
	 *    - Good for: CPU-bound tasks
	 *    - Bad for: Blocking I/O
	 *
	 * 2. Custom Thread Pool:
	 *    - FixedThreadPool: Known number of concurrent tasks
	 *    - CachedThreadPool: Variable/unknown number of tasks
	 *    - ScheduledThreadPool: Delayed/periodic tasks
	 *
	 * 3. Blocking Operations:
	 *    - ALWAYS use separate thread pool
	 *    - Don't block common pool threads
	 *    - Size pool based on expected concurrency
	 *
	 * PERFORMANCE CONSIDERATIONS:
	 *
	 * 1. Thread Context Switching:
	 *    - Has overhead (~1-10 microseconds)
	 *    - Not worth it for trivial operations
	 *
	 * 2. Common Pool Exhaustion:
	 *    - Too many blocking operations can starve pool
	 *    - Causes other async operations to queue
	 *    - Use dedicated pools for blocking I/O
	 *
	 * 3. Memory:
	 *    - Each thread has stack (typically 1MB)
	 *    - Too many threads = memory pressure
	 *    - Virtual threads (Java 21+) solve this
	 *
	 * BEST PRACTICES:
	 *
	 * 1. Default to non-async for lightweight operations
	 * 2. Use async for CPU-intensive work
	 * 3. Always use custom executor for blocking I/O
	 * 4. Name threads in custom executors for debugging
	 * 5. Shutdown custom executors when done
	 * 6. Monitor thread pool metrics in production
	 * 7. Consider virtual threads (Java 21+) for I/O-bound apps
	 */

