# CompletableFuture - Complete Guide

## Overview
CompletableFuture is a powerful class introduced in Java 8 that represents a future result of an asynchronous computation. It implements both `Future` and `CompletionStage` interfaces.

## Key Features
- **Non-blocking**: Asynchronous execution without blocking threads
- **Composable**: Chain multiple operations together
- **Exception Handling**: Built-in error handling mechanisms
- **Flexible Execution**: Control over thread pools and execution context

## Project Structure

```
CompletiableFuture/
├── src/
│   ├── 1_BasicCompletableFuture.java          # Creating and completing futures
│   ├── 2_TransformationMethods.java           # thenApply, thenAccept, thenRun
│   ├── 3_ChainingOperations.java              # thenCompose, thenCombine
│   ├── 4_ExceptionHandling.java               # exceptionally, handle, whenComplete
│   ├── 5_Multiplefutures.java                # allOf, anyOf
│   ├── 6_AsyncVariations.java                 # Async vs Non-Async methods
│   ├── 7_TimeoutHandling.java                 # Timeout operations (Java 9+)
│   ├── 8_RealWorldExamples.java               # Practical use cases
│   └── 9_BestPractices.java                   # Common patterns and pitfalls
└── README.md
```

## How to Run

### Compile all files:
```bash
cd C:\Users\NaveenBhatt\Project\EPAM\Java\CompletiableFuture
javac src/*.java
```

### Run specific example:
```bash
java -cp src 1_BasicCompletableFuture
java -cp src 2_TransformationMethods
# ... and so on
```

## Quick Reference

### Creation Methods
- `CompletableFuture.supplyAsync()` - Returns a value
- `CompletableFuture.runAsync()` - No return value
- `CompletableFuture.completedFuture()` - Already completed

### Transformation Methods
- `thenApply()` - Transform result (Function)
- `thenAccept()` - Consume result (Consumer)
- `thenRun()` - Execute action (Runnable)

### Combining Methods
- `thenCompose()` - Chain dependent futures (flatMap)
- `thenCombine()` - Combine two independent futures
- `allOf()` - Wait for all futures
- `anyOf()` - Wait for any future

### Exception Handling
- `exceptionally()` - Handle exception and return default
- `handle()` - Handle both result and exception
- `whenComplete()` - Perform action after completion

## Key Concepts

### Async vs Non-Async
- **Non-Async** (e.g., `thenApply`): Executes in the same thread that completes the previous stage
- **Async** (e.g., `thenApplyAsync`): Executes in a separate thread from ForkJoinPool.commonPool()
- **Async with Executor**: Custom thread pool for execution

### Thread Pool
By default, CompletableFuture uses `ForkJoinPool.commonPool()`. You can provide custom executor:
```java
ExecutorService executor = Executors.newFixedThreadPool(10);
CompletableFuture.supplyAsync(() -> "Hello", executor);
```

## Common Patterns

### Pattern 1: Sequential Chaining
```java
CompletableFuture.supplyAsync(() -> getData())
    .thenApply(data -> process(data))
    .thenApply(result -> format(result))
    .thenAccept(output -> print(output));
```

### Pattern 2: Parallel Execution
```java
CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> task1());
CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> task2());
CompletableFuture.allOf(future1, future2).join();
```

### Pattern 3: Error Recovery
```java
CompletableFuture.supplyAsync(() -> riskyOperation())
    .exceptionally(ex -> defaultValue())
    .thenAccept(result -> use(result));
```

## Performance Tips
1. Use custom thread pools for blocking operations
2. Avoid blocking calls like `get()` in production code
3. Use `thenCompose()` instead of nested `thenApply()`
4. Handle exceptions properly to avoid silent failures
5. Consider timeout for long-running operations

## Java Version Features
- **Java 8**: Core CompletableFuture API
- **Java 9**: Added `orTimeout()`, `completeOnTimeout()`, `completedStage()`, `failedStage()`, `copy()`
- **Java 12**: Added `exceptionallyAsync()`, `exceptionallyCompose()`

## Resources
- [Official JavaDoc](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/CompletableFuture.html)
- Each example file contains detailed comments explaining the concept
