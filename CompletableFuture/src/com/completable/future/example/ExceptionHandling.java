package com.completable.future.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * 4. EXCEPTION HANDLING
 *
 * Demonstrates error handling in CompletableFuture:
 * - exceptionally(): Handle exceptions and provide default value
 * - handle(): Handle both success and failure cases
 * - whenComplete(): Perform action on completion (success or failure)
 * - completeExceptionally(): Manually fail a future
 *
 * Exception propagation:
 * - Exceptions in async operations wrapped in CompletionException
 * - Exceptions propagate through the chain until handled
 * - Unhandled exceptions thrown when calling join() or get()
 */
public class ExceptionHandling {

    public static void main(String[] args) {
        System.out.println("=== EXCEPTION HANDLING ===\n");

        example1_Exceptionally();
        example2_Handle();
        example3_WhenComplete();
        example4_CompleteExceptionally();
        example5_ExceptionPropagation();
        example6_RecoverFromMultipleExceptions();
        example7_ChainingWithExceptionHandling();
    }

    /**
     * Example 1: exceptionally()
     * Handles exceptions and provides a default/fallback value
     * Only called if an exception occurs
     * Takes Function<Throwable, T>
     *
     * Use Case: Provide default value on error, graceful degradation
     */
    private static void example1_Exceptionally() {
        System.out.println("--- Example 1: exceptionally() ---");

        // Successful case
        CompletableFuture<String> successFuture = CompletableFuture.supplyAsync(() -> {
            return "Success!";
        }).exceptionally(ex -> {
            return "Fallback value"; // Not called
        });

        System.out.println("Success case: " + successFuture.join());

        // Failure case
        CompletableFuture<String> failureFuture = CompletableFuture.<String>supplyAsync(() -> {
            throw new RuntimeException("Something went wrong!");
        }).exceptionally(ex -> {
            System.out.println("Exception caught: " + ex.getMessage());
            return "Default value"; // Fallback
        });

        System.out.println("Failure case: " + failureFuture.join());
        System.out.println();
    }

    /**
     * Example 2: handle()
     * Handles both successful results and exceptions
     * Always called regardless of outcome
     * Takes BiFunction<T, Throwable, R>
     *
     * Use Case: Transform result or handle error in one place
     */
    private static void example2_Handle() {
        System.out.println("--- Example 2: handle() ---");

        // Success case
        CompletableFuture<String> successFuture = CompletableFuture.supplyAsync(() -> {
            return 42;
        }).handle((result, exception) -> {
            if (exception != null) {
                return "Error: " + exception.getMessage();
            } else {
                return "Result: " + result;
            }
        });

        System.out.println("Success: " + successFuture.join());

        // Failure case
        CompletableFuture<String> failureFuture = CompletableFuture.supplyAsync(() -> {
            int x = 10 / 0; // ArithmeticException
            return x;
        }).handle((result, exception) -> {
            if (exception != null) {
                return "Error handled: " + exception.getCause().getMessage();
            } else {
                return "Result: " + result;
            }
        });

        System.out.println("Failure: " + failureFuture.join());
        System.out.println();
    }

    /**
     * Example 3: whenComplete()
     * Performs an action after completion (success or failure)
     * Doesn't transform the result - just observes
     * Takes BiConsumer<T, Throwable>
     * Exception is re-thrown if present
     *
     * Use Case: Logging, metrics, cleanup (similar to finally block)
     */
    private static void example3_WhenComplete() {
        System.out.println("--- Example 3: whenComplete() ---");

        // Success case
        CompletableFuture<Integer> successFuture = CompletableFuture.supplyAsync(() -> {
            return 100;
        }).whenComplete((result, exception) -> {
            if (exception != null) {
                System.out.println("Failed with: " + exception.getMessage());
            } else {
                System.out.println("Completed successfully with: " + result);
            }
        });

        System.out.println("Result: " + successFuture.join());

        // Failure case - exception is re-thrown
        CompletableFuture<Integer> failureFuture = CompletableFuture.<Integer>supplyAsync(() -> {
            throw new RuntimeException("Task failed");
        }).whenComplete((result, exception) -> {
            System.out.println("In whenComplete - Exception: " + exception.getMessage());
            // Logging or cleanup
        });

        try {
            failureFuture.join(); // Exception is re-thrown
        } catch (CompletionException e) {
            System.out.println("Exception caught outside: " + e.getCause().getMessage());
        }
        System.out.println();
    }

    /**
     * Example 4: completeExceptionally()
     * Manually complete a future with an exception
     *
     * Use Case: Bridge callback-based APIs that report errors
     */
    private static void example4_CompleteExceptionally() {
        System.out.println("--- Example 4: completeExceptionally() ---");

        CompletableFuture<String> future = new CompletableFuture<>();

        // Simulate async operation that fails
        new Thread(() -> {
            sleep(500);
            // Manually fail the future
            future.completeExceptionally(new RuntimeException("Manual failure"));
        }).start();

        try {
            future.join();
        } catch (CompletionException e) {
            System.out.println("Future failed: " + e.getCause().getMessage());
        }
        System.out.println();
    }

    /**
     * Example 5: Exception Propagation
     * Exceptions propagate through the chain until handled
     * Once handled, the chain continues with the recovery value
     */
    private static void example5_ExceptionPropagation() {
        System.out.println("--- Example 5: Exception Propagation ---");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("Stage 1: Success");
            return "Initial";
        }).thenApply(result -> {
            System.out.println("Stage 2: Throwing exception");
            throw new RuntimeException("Stage 2 failed");
        }).thenApply(result -> {
            System.out.println("Stage 3: Not executed"); // Skipped
            return result + "_3";
        }).exceptionally(ex -> {
            System.out.println("Exception caught: " + ex.getMessage());
            return "Recovered";
        }).thenApply(result -> {
            System.out.println("Stage 4: Continues after recovery");
            return result + "_Final";
        });

        System.out.println("Final result: " + future.join());
        System.out.println();
    }

    /**
     * Example 6: Recovering from Multiple Points
     * You can have multiple exception handlers at different stages
     */
    private static void example6_RecoverFromMultipleExceptions() {
        System.out.println("--- Example 6: Multiple Exception Handlers ---");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return fetchDataFromPrimarySource();
        }).exceptionally(ex -> {
            System.out.println("Primary source failed, trying backup");
            return fetchDataFromBackupSource();
        }).thenApply(data -> {
            return processData(data);
        }).exceptionally(ex -> {
            System.out.println("Processing failed: " + ex.getMessage());
            return "Default processed data";
        });

        System.out.println("Result: " + future.join());
        System.out.println();
    }

    /**
     * Example 7: Chaining with Proper Exception Handling
     * Real-world example: API call -> Process -> Save with error handling
     */
    private static void example7_ChainingWithExceptionHandling() {
        System.out.println("--- Example 7: Real-world Chaining with Error Handling ---");

        CompletableFuture<String> pipeline = CompletableFuture
            .supplyAsync(() -> {
                System.out.println("1. Fetching data from API");
                return callExternalAPI();
            })
            .exceptionally(ex -> {
                System.out.println("API call failed: " + ex.getMessage());
                return "{}"; // Return empty JSON
            })
            .thenApply(jsonData -> {
                System.out.println("2. Parsing JSON");
                if (jsonData.equals("{}")) {
                    return null;
                }
                return parseJSON(jsonData);
            })
            .thenApply(data -> {
                System.out.println("3. Processing data");
                return processBusinessLogic(data);
            })
            .handle((result, ex) -> {
                if (ex != null) {
                    System.out.println("Processing error: " + ex.getMessage());
                    return "Error Report";
                }
                return result;
            })
            .whenComplete((result, ex) -> {
                System.out.println("4. Logging completion");
                if (ex == null) {
                    System.out.println("Pipeline completed: " + result);
                }
            });

        System.out.println("Final: " + pipeline.join());
        System.out.println();
    }

    // Helper methods to simulate operations

    private static String fetchDataFromPrimarySource() {
        throw new RuntimeException("Primary source unavailable");
    }

    private static String fetchDataFromBackupSource() {
        return "Backup data";
    }

    private static String processData(String data) {
        if (data.equals("Backup data")) {
            return "Processed: " + data;
        }
        throw new RuntimeException("Invalid data format");
    }

    private static String callExternalAPI() {
        // Simulate random failure
        if (Math.random() > 0.5) {
            throw new RuntimeException("API timeout");
        }
        return "{\"status\":\"success\"}";
    }

    private static String parseJSON(String json) {
        return json.replace("{", "").replace("}", "");
    }

    private static String processBusinessLogic(String data) {
        if (data == null) {
            throw new RuntimeException("No data to process");
        }
        return "Processed: " + data;
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
 * EXCEPTION HANDLING SUMMARY:
 *
 * +------------------+-------------------------+------------------+----------------------------+
 * | Method           | Called When             | Returns          | Re-throws Exception        |
 * +------------------+-------------------------+------------------+----------------------------+
 * | exceptionally()  | Only on exception       | T (recovery)     | No - swallows exception    |
 * | handle()         | Always (success/fail)   | R (transform)    | No - handles both cases    |
 * | whenComplete()   | Always (success/fail)   | Same result      | Yes - re-throws if present |
 * +------------------+-------------------------+------------------+----------------------------+
 *
 * WHEN TO USE:
 *
 * 1. exceptionally():
 *    - Provide fallback/default value on error
 *    - Graceful degradation
 *    - Similar to catch block with return
 *
 * 2. handle():
 *    - Transform result OR handle error
 *    - Need access to both result and exception
 *    - Convert error to success or vice versa
 *
 * 3. whenComplete():
 *    - Logging, metrics, cleanup
 *    - Side effects without changing result
 *    - Similar to finally block but sees result/exception
 *
 * EXCEPTION TYPES:
 * - Checked exceptions wrapped in CompletionException
 * - Access original: exception.getCause()
 * - join() throws unchecked CompletionException
 * - get() throws checked ExecutionException
 *
 * BEST PRACTICES:
 * 1. Always handle exceptions - don't let them bubble silently
 * 2. Use exceptionally() for simple fallbacks
 * 3. Use handle() when error handling logic is complex
 * 4. Use whenComplete() for observing without modifying
 * 5. Log exceptions for debugging
 * 6. Consider retry logic for transient failures
 * 7. Clean up resources in whenComplete()
 */
