package com.completable.future.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 9. BEST PRACTICES AND COMMON PITFALLS
 *
 * This file demonstrates:
 * - Best practices for using CompletableFuture
 * - Common pitfalls and how to avoid them
 * - Performance optimization techniques
 * - Testing strategies
 * - Production considerations
 */
public class BestPractices {

    public static void main(String[] args) {
        System.out.println("=== BEST PRACTICES & COMMON PITFALLS ===\n");

        pitfall1_BlockingCommonPool();
        pitfall2_NestedFutures();
        pitfall3_SwallowingExceptions();
        pitfall4_MemoryLeaks();
        pitfall5_NotHandlingExceptions();

        bestPractice1_CustomExecutor();
        bestPractice2_ProperExceptionHandling();
        bestPractice3_ResourceCleanup();
        bestPractice4_AvoidGetInProductionCode();
        bestPractice5_TestingAsyncCode();

        System.out.println("\n=== SUMMARY ===");
        printSummary();
    }

    // ==================== COMMON PITFALLS ====================

    /**
     * PITFALL 1: Blocking the Common Pool
     *
     * Problem: Using blocking operations in common pool exhausts threads
     * Solution: Use custom executor for blocking I/O
     */
    private static void pitfall1_BlockingCommonPool() {
        System.out.println("--- PITFALL 1: Blocking Common Pool ---");

        // BAD: Blocking operation in common pool
        System.out.println("BAD Example:");
        CompletableFuture<String> bad = CompletableFuture.supplyAsync(() -> {
            System.out.println("Blocking common pool thread");
            sleep(2000); // BAD: Blocks precious common pool thread
            return "Bad";
        });

        // GOOD: Use separate executor for blocking
        System.out.println("\nGOOD Example:");
        ExecutorService ioExecutor = Executors.newCachedThreadPool();
        CompletableFuture<String> good = CompletableFuture.supplyAsync(() -> {
            System.out.println("Blocking in dedicated pool");
            sleep(2000); // OK: Dedicated thread for blocking
            return "Good";
        }, ioExecutor);

        good.join();
        ioExecutor.shutdown();
        System.out.println();
    }

    /**
     * PITFALL 2: Nested Futures (Pyramid of Doom)
     *
     * Problem: Using thenApply when thenCompose is needed
     * Results in CompletableFuture<CompletableFuture<T>>
     */
    private static void pitfall2_NestedFutures() {
        System.out.println("--- PITFALL 2: Nested Futures ---");

        // BAD: Creates nested future
        System.out.println("BAD Example:");
        CompletableFuture<CompletableFuture<String>> bad = CompletableFuture
            .supplyAsync(() -> "user123")
            .thenApply(userId -> {
                // Returns CompletableFuture, creating nested structure
                return CompletableFuture.supplyAsync(() -> "User data for " + userId);
            });

        // Need to call join() twice - ugly!
        // String result = bad.join().join();
        System.out.println("Creates CompletableFuture<CompletableFuture<String>>");

        // GOOD: Use thenCompose to flatten
        System.out.println("\nGOOD Example:");
        CompletableFuture<String> good = CompletableFuture
            .supplyAsync(() -> "user123")
            .thenCompose(userId -> {
                // thenCompose flattens the future
                return CompletableFuture.supplyAsync(() -> "User data for " + userId);
            });

        String result = good.join(); // Single join
        System.out.println("Result: " + result);
        System.out.println();
    }

    /**
     * PITFALL 3: Swallowing Exceptions Silently
     *
     * Problem: Using exceptionally() without logging
     * Makes debugging impossible
     */
    private static void pitfall3_SwallowingExceptions() {
        System.out.println("--- PITFALL 3: Swallowing Exceptions ---");

        // BAD: Silent failure
        System.out.println("BAD Example:");
        CompletableFuture<String> bad = CompletableFuture
            .<String>supplyAsync(() -> {
                throw new RuntimeException("Something went wrong");
            })
            .exceptionally(ex -> "default"); // No logging!

        System.out.println("Result: " + bad.join());
        System.out.println("You'll never know what went wrong!");

        // GOOD: Log exceptions
        System.out.println("\nGOOD Example:");
        CompletableFuture<String> good = CompletableFuture
            .<String>supplyAsync(() -> {
                throw new RuntimeException("Something went wrong");
            })
            .exceptionally(ex -> {
                System.err.println("ERROR: " + ex.getMessage());
                ex.printStackTrace(); // Or use proper logging framework
                return "default";
            });

        System.out.println("Result: " + good.join());
        System.out.println();
    }

    /**
     * PITFALL 4: Memory Leaks with Incomplete Futures
     *
     * Problem: Creating futures that never complete
     * They stay in memory forever
     */
    private static void pitfall4_MemoryLeaks() {
        System.out.println("--- PITFALL 4: Memory Leaks ---");

        // BAD: Future never completes
        System.out.println("BAD Example:");
        CompletableFuture<String> neverCompletes = new CompletableFuture<>();
        // This future will never complete and holds references

        System.out.println("Future created but never completed");
        System.out.println("Is done? " + neverCompletes.isDone());

        // GOOD: Always complete futures or set timeouts
        System.out.println("\nGOOD Example:");
        CompletableFuture<String> withTimeout = new CompletableFuture<>();

        // Set timeout (Java 9+) or use get(timeout)
        try {
            String result = withTimeout.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("Timeout - future didn't complete");
            withTimeout.completeExceptionally(e); // Clean up
        }

        System.out.println();
    }

    /**
     * PITFALL 5: Not Handling Exceptions at All
     *
     * Problem: Exceptions propagate to join()/get()
     * May crash application unexpectedly
     */
    private static void pitfall5_NotHandlingExceptions() {
        System.out.println("--- PITFALL 5: Not Handling Exceptions ---");

        // BAD: No exception handling
        System.out.println("BAD Example:");
        CompletableFuture<String> bad = CompletableFuture
            .supplyAsync(() -> {
                if (Math.random() > 0.5) {
                    throw new RuntimeException("Random failure");
                }
                return "Success";
            });

        try {
            bad.join(); // May throw CompletionException
        } catch (Exception e) {
            System.out.println("Caught exception at the end: " + e.getClass().getSimpleName());
        }

        // GOOD: Handle exceptions in the chain
        System.out.println("\nGOOD Example:");
        CompletableFuture<String> good = CompletableFuture
            .supplyAsync(() -> {
                if (Math.random() > 0.5) {
                    throw new RuntimeException("Random failure");
                }
                return "Success";
            })
            .handle((result, ex) -> {
                if (ex != null) {
                    System.err.println("Handled in chain: " + ex.getMessage());
                    return "Default value";
                }
                return result;
            });

        System.out.println("Result: " + good.join()); // Never throws
        System.out.println();
    }

    // ==================== BEST PRACTICES ====================

    /**
     * BEST PRACTICE 1: Use Custom Executor for Blocking Operations
     */
    private static void bestPractice1_CustomExecutor() {
        System.out.println("--- BEST PRACTICE 1: Custom Executor ---");

        // Separate executors for different workloads
        ExecutorService cpuBound = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
        );

        ExecutorService ioBound = Executors.newCachedThreadPool();

        CompletableFuture<String> result = CompletableFuture
            .supplyAsync(() -> {
                // I/O operation
                System.out.println("Fetching data from database...");
                sleep(500);
                return "Data";
            }, ioBound)
            .thenApplyAsync(data -> {
                // CPU-intensive operation
                System.out.println("Processing data...");
                return processData(data);
            }, cpuBound);

        System.out.println("Result: " + result.join());

        cpuBound.shutdown();
        ioBound.shutdown();
        System.out.println();
    }

    /**
     * BEST PRACTICE 2: Comprehensive Exception Handling
     */
    private static void bestPractice2_ProperExceptionHandling() {
        System.out.println("--- BEST PRACTICE 2: Exception Handling ---");

        CompletableFuture<String> robust = CompletableFuture
            .supplyAsync(() -> {
                return fetchFromPrimary();
            })
            .exceptionally(ex -> {
                System.err.println("Primary failed: " + ex.getMessage());
                return fetchFromBackup();
            })
            .thenApply(data -> {
                return processData(data);
            })
            .handle((result, ex) -> {
                if (ex != null) {
                    System.err.println("Processing failed: " + ex.getMessage());
                    sendAlert("Processing failed");
                    return "Error";
                }
                return result;
            })
            .whenComplete((result, ex) -> {
                // Always log completion
                if (ex == null) {
                    System.out.println("Operation completed successfully");
                } else {
                    System.err.println("Operation completed with error");
                }
            });

        System.out.println("Result: " + robust.join());
        System.out.println();
    }

    /**
     * BEST PRACTICE 3: Resource Cleanup
     */
    private static void bestPractice3_ResourceCleanup() {
        System.out.println("--- BEST PRACTICE 3: Resource Cleanup ---");

        ExecutorService executor = Executors.newFixedThreadPool(5);

        try {
            CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> {
                    return "Result";
                }, executor)
                .whenComplete((result, ex) -> {
                    // Clean up resources
                    System.out.println("Cleaning up resources...");
                });

            System.out.println("Result: " + future.join());

        } finally {
            // Always shutdown executor
            System.out.println("Shutting down executor...");
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }

        System.out.println();
    }

    /**
     * BEST PRACTICE 4: Avoid get() in Production Code
     */
    private static void bestPractice4_AvoidGetInProductionCode() {
        System.out.println("--- BEST PRACTICE 4: Avoid get() ---");

        // BAD: Blocking with get()
        System.out.println("BAD: Blocking the thread");
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "Result";
        });

        try {
            String result = future1.get(); // Blocks thread!
            System.out.println("Got result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // GOOD: Use callbacks
        System.out.println("\nGOOD: Non-blocking with callbacks");
        CompletableFuture<Void> future2 = CompletableFuture
            .supplyAsync(() -> {
                sleep(1000);
                return "Result";
            })
            .thenAccept(result -> {
                System.out.println("Got result asynchronously: " + result);
            });

        future2.join(); // Only for demo - in production, let it complete async
        System.out.println();
    }

    /**
     * BEST PRACTICE 5: Testing Async Code
     */
    private static void bestPractice5_TestingAsyncCode() {
        System.out.println("--- BEST PRACTICE 5: Testing ---");

        // Testable async code
        class UserService {
            private ExecutorService executor;

            // Constructor injection for testing
            UserService(ExecutorService executor) {
                this.executor = executor;
            }

            CompletableFuture<String> getUserAsync(String userId) {
                return CompletableFuture.supplyAsync(() -> {
                    // Simulate database call
                    sleep(100);
                    return "User-" + userId;
                }, executor);
            }
        }

        // Production: Use real executor
        ExecutorService realExecutor = Executors.newFixedThreadPool(2);
        UserService prodService = new UserService(realExecutor);

        // Test: Use synchronous executor
        ExecutorService testExecutor = Executors.newSingleThreadExecutor();
        UserService testService = new UserService(testExecutor);

        // Test the service
        CompletableFuture<String> result = testService.getUserAsync("123");
        System.out.println("Test result: " + result.join());

        realExecutor.shutdown();
        testExecutor.shutdown();
        System.out.println();
    }

    // ==================== HELPER METHODS ====================

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static String fetchFromPrimary() {
        if (Math.random() > 0.7) {
            throw new RuntimeException("Primary unavailable");
        }
        return "Primary data";
    }

    private static String fetchFromBackup() {
        return "Backup data";
    }

    private static String processData(String data) {
        return data.toUpperCase();
    }

    private static void sendAlert(String message) {
        System.err.println("ALERT: " + message);
    }

    // ==================== SUMMARY ====================

    private static void printSummary() {
        System.out.println("""

            ============================================================
            COMPLETABLEFUTURE BEST PRACTICES SUMMARY
            ============================================================

            ✓ DO's:
            -------
            1. Use custom executors for blocking I/O operations
            2. Always handle exceptions (exceptionally, handle, whenComplete)
            3. Use thenCompose for chaining futures (not thenApply)
            4. Log exceptions for debugging
            5. Set timeouts for external service calls
            6. Shutdown custom executors properly
            7. Use join() over get() for cleaner code
            8. Make executors configurable for testing
            9. Monitor thread pool metrics in production
            10. Use meaningful thread names for debugging

            ✗ DON'Ts:
            ---------
            1. DON'T block common pool with I/O operations
            2. DON'T swallow exceptions silently
            3. DON'T use get() in production code (use callbacks)
            4. DON'T create nested futures (use thenCompose)
            5. DON'T forget to complete manually created futures
            6. DON'T ignore exceptions - they won't go away
            7. DON'T use unlimited thread pools without monitoring
            8. DON'T mix sync and async code carelessly

            THREAD POOL SIZING:
            -------------------
            - CPU-bound: Runtime.getRuntime().availableProcessors()
            - I/O-bound: Higher (depends on blocking factor)
            - Mixed: Separate pools for different workloads

            EXCEPTION HANDLING LAYERS:
            --------------------------
            1. exceptionally() - Provide fallback value
            2. handle() - Transform or recover
            3. whenComplete() - Observe and log
            4. Try-catch on join() - Last resort

            PERFORMANCE TIPS:
            -----------------
            1. Minimize async boundaries (thread switching overhead)
            2. Batch operations when possible
            3. Use allOf() for parallel operations
            4. Set appropriate timeouts
            5. Cache frequently accessed data
            6. Profile before optimizing

            PRODUCTION CHECKLIST:
            ---------------------
            □ Exception handling at all stages
            □ Timeouts configured
            □ Custom executors for blocking ops
            □ Logging and monitoring
            □ Resource cleanup (executor shutdown)
            □ Graceful degradation (fallbacks)
            □ Metrics and alerting
            □ Load testing with concurrent requests
            □ Thread pool sizing validated
            □ Memory leak testing

            ============================================================
            """);
    }
}

/**
 * COMMON ISSUES CHECKLIST:
 *
 * Issue: Application hangs
 * Cause: Blocking common pool
 * Fix: Use custom executor for blocking ops
 *
 * Issue: Exceptions disappear
 * Cause: No exception handling
 * Fix: Add exceptionally() or handle()
 *
 * Issue: Poor performance
 * Cause: Too much async overhead
 * Fix: Reduce async boundaries
 *
 * Issue: Thread pool exhaustion
 * Cause: Too many concurrent operations
 * Fix: Limit concurrency, use proper pool size
 *
 * Issue: Memory leaks
 * Cause: Futures never complete
 * Fix: Set timeouts, ensure completion
 *
 * Issue: Nested futures
 * Cause: Using thenApply instead of thenCompose
 * Fix: Use thenCompose for future-returning functions
 *
 * Issue: Hard to test
 * Cause: Hardcoded executors
 * Fix: Inject executors via constructor
 *
 * Issue: Slow tests
 * Cause: Real async execution in tests
 * Fix: Use synchronous executor for tests
 *
 * ============================================================
 * FURTHER READING:
 *
 * 1. Java Concurrency in Practice - Brian Goetz
 * 2. CompletableFuture JavaDoc
 * 3. Project Loom (Virtual Threads) - Java 21+
 * 4. Reactive Programming with RxJava/Project Reactor
 * 5. Asynchronous Programming Patterns
 * ============================================================
 */
