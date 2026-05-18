package com.completable.future.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 8. REAL-WORLD EXAMPLES
 *
 * Practical examples of CompletableFuture usage in real applications:
 * 1. Parallel REST API calls
 * 2. Database operations with fallback
 * 3. E-commerce order processing
 * 4. Microservices orchestration
 * 5. Notification system
 * 6. Search aggregation
 * 7. File processing pipeline
 */
public class RealWorldExamples {

    private static final ExecutorService ioExecutor =
        Executors.newFixedThreadPool(10, r -> {
            Thread t = new Thread(r);
            t.setName("IO-Pool-" + t.getId());
            return t;
        });

    public static void main(String[] args) {
        System.out.println("=== REAL-WORLD EXAMPLES ===\n");

        example1_ParallelAPICallsForUserProfile();
        example2_DatabaseWithCacheFallback();
        example3_EcommerceOrderProcessing();
        example4_MicroservicesOrchestration();
        example5_NotificationSystem();
        example6_SearchAggregation();
        example7_FileProcessingPipeline();

        ioExecutor.shutdown();
        System.out.println("\nAll examples completed!");
    }

    /**
     * Example 1: Parallel REST API Calls for User Profile
     *
     * Scenario: Building user profile by fetching data from multiple services
     * - User service (basic info)
     * - Order service (purchase history)
     * - Recommendation service (suggestions)
     *
     * All calls are independent and can run in parallel
     */
    private static void example1_ParallelAPICallsForUserProfile() {
        System.out.println("--- Example 1: User Profile with Parallel API Calls ---");

        String userId = "user123";

        CompletableFuture<UserInfo> userInfo = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching user info...");
            sleep(500);
            return new UserInfo(userId, "John Doe", "john@example.com");
        }, ioExecutor);

        CompletableFuture<List<Order>> orders = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching order history...");
            sleep(700);
            return Arrays.asList(
                new Order("ORD001", "Laptop", 1200.0),
                new Order("ORD002", "Mouse", 25.0)
            );
        }, ioExecutor);

        CompletableFuture<List<String>> recommendations = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching recommendations...");
            sleep(600);
            return Arrays.asList("Keyboard", "Monitor", "Headphones");
        }, ioExecutor);

        // Combine all results into UserProfile
        CompletableFuture<UserProfile> profile = userInfo.thenCombine(orders, (user, orderList) -> {
            return new UserProfile(user, orderList, null);
        }).thenCombine(recommendations, (prof, recs) -> {
            prof.recommendations = recs;
            return prof;
        });

        UserProfile result = profile.join();
        System.out.println("User Profile: " + result);
        System.out.println("Total time: ~700ms (parallel, not 1800ms sequential)\n");
    }

    /**
     * Example 2: Database Query with Cache Fallback
     *
     * Pattern: Try cache first, fallback to database, with error handling
     */
    private static void example2_DatabaseWithCacheFallback() {
        System.out.println("--- Example 2: Database with Cache Fallback ---");

        String productId = "PROD123";

        CompletableFuture<Product> product = CompletableFuture
            .supplyAsync(() -> {
                System.out.println("Checking cache...");
                return getFromCache(productId);
            })
            .thenCompose(cached -> {
                if (cached != null) {
                    System.out.println("Cache hit!");
                    return CompletableFuture.completedFuture(cached);
                }
                System.out.println("Cache miss, querying database...");
                return getFromDatabase(productId);
            })
            .exceptionally(ex -> {
                System.out.println("Database error: " + ex.getMessage());
                return getDefaultProduct();
            })
            .whenComplete((result, ex) -> {
                if (ex == null && result != null) {
                    System.out.println("Updating cache asynchronously...");
                    updateCacheAsync(productId, result);
                }
            });

        Product result = product.join();
        System.out.println("Product: " + result);
        System.out.println();
    }

    /**
     * Example 3: E-commerce Order Processing Pipeline
     *
     * Workflow:
     * 1. Validate order
     * 2. Check inventory (parallel with payment)
     * 3. Process payment
     * 4. Reserve inventory
     * 5. Send confirmation
     */
    private static void example3_EcommerceOrderProcessing() {
        System.out.println("--- Example 3: E-commerce Order Processing ---");

        Order order = new Order("ORD999", "Laptop", 1200.0);

        CompletableFuture<String> orderProcessing = CompletableFuture
            .supplyAsync(() -> {
                System.out.println("1. Validating order...");
                sleep(200);
                validateOrder(order);
                return order;
            }, ioExecutor)
            .thenApplyAsync(validOrder -> {
                System.out.println("2. Checking inventory...");
                sleep(300);
                checkInventory(validOrder.productName);
                return validOrder;
            }, ioExecutor)
            .thenCompose(validOrder -> {
                // Process payment in parallel with inventory check completion
                CompletableFuture<String> payment = CompletableFuture.supplyAsync(() -> {
                    System.out.println("3. Processing payment...");
                    sleep(500);
                    return processPayment(validOrder.amount);
                }, ioExecutor);

                return payment.thenApply(paymentId -> {
                    System.out.println("4. Payment successful: " + paymentId);
                    return validOrder;
                });
            })
            .thenApplyAsync(paidOrder -> {
                System.out.println("5. Reserving inventory...");
                sleep(200);
                reserveInventory(paidOrder.productName);
                return paidOrder;
            }, ioExecutor)
            .thenApplyAsync(finalOrder -> {
                System.out.println("6. Sending confirmation email...");
                sleep(100);
                sendConfirmation(finalOrder.orderId);
                return "Order " + finalOrder.orderId + " completed successfully!";
            }, ioExecutor)
            .exceptionally(ex -> {
                System.err.println("Order failed: " + ex.getMessage());
                // Compensating transaction would go here
                return "Order failed: " + ex.getMessage();
            });

        String result = orderProcessing.join();
        System.out.println("Result: " + result);
        System.out.println();
    }

    /**
     * Example 4: Microservices Orchestration
     *
     * Scenario: Create booking that requires calling multiple services
     */
    private static void example4_MicroservicesOrchestration() {
        System.out.println("--- Example 4: Microservices Orchestration ---");

        String userId = "user456";
        String flightId = "FL123";

        // Step 1: Validate user
        CompletableFuture<Boolean> userValidation = CompletableFuture
            .supplyAsync(() -> callUserService(userId), ioExecutor);

        // Step 2: Check flight availability (can run in parallel)
        CompletableFuture<Boolean> flightCheck = CompletableFuture
            .supplyAsync(() -> callFlightService(flightId), ioExecutor);

        // Step 3: Wait for both, then create booking
        CompletableFuture<String> booking = userValidation
            .thenCombine(flightCheck, (userValid, flightAvailable) -> {
                if (!userValid) throw new RuntimeException("Invalid user");
                if (!flightAvailable) throw new RuntimeException("Flight not available");
                return true;
            })
            .thenComposeAsync(validated -> {
                System.out.println("Creating booking...");
                return callBookingService(userId, flightId);
            }, ioExecutor)
            .thenComposeAsync(bookingId -> {
                System.out.println("Booking created: " + bookingId);
                // Trigger notifications asynchronously (fire and forget)
                CompletableFuture.runAsync(() -> sendBookingNotification(bookingId), ioExecutor);
                return CompletableFuture.completedFuture(bookingId);
            })
            .exceptionally(ex -> {
                System.err.println("Booking failed: " + ex.getMessage());
                return null;
            });

        String result = booking.join();
        System.out.println("Final booking ID: " + result);
        System.out.println();
    }

    /**
     * Example 5: Multi-channel Notification System
     *
     * Send notifications via multiple channels, don't fail if one channel fails
     */
    private static void example5_NotificationSystem() {
        System.out.println("--- Example 5: Multi-channel Notification System ---");

        String userId = "user789";
        String message = "Your order has shipped!";

        // Send notifications to all channels independently
        CompletableFuture<String> emailNotif = CompletableFuture
            .supplyAsync(() -> sendEmail(userId, message), ioExecutor)
            .exceptionally(ex -> "Email failed: " + ex.getMessage());

        CompletableFuture<String> smsNotif = CompletableFuture
            .supplyAsync(() -> sendSMS(userId, message), ioExecutor)
            .exceptionally(ex -> "SMS failed: " + ex.getMessage());

        CompletableFuture<String> pushNotif = CompletableFuture
            .supplyAsync(() -> sendPushNotification(userId, message), ioExecutor)
            .exceptionally(ex -> "Push failed: " + ex.getMessage());

        // Wait for all and collect results
        CompletableFuture<Void> allNotifications = CompletableFuture.allOf(
            emailNotif, smsNotif, pushNotif
        );

        allNotifications.thenRun(() -> {
            System.out.println("Notification Results:");
            System.out.println("  Email: " + emailNotif.join());
            System.out.println("  SMS: " + smsNotif.join());
            System.out.println("  Push: " + pushNotif.join());
        }).join();

        System.out.println();
    }

    /**
     * Example 6: Search Aggregation from Multiple Sources
     *
     * Search multiple data sources and aggregate results
     */
    private static void example6_SearchAggregation() {
        System.out.println("--- Example 6: Search Aggregation ---");

        String query = "laptop";

        // Search multiple sources in parallel
        CompletableFuture<List<String>> productsDB = CompletableFuture
            .supplyAsync(() -> searchProductDatabase(query), ioExecutor);

        CompletableFuture<List<String>> externalAPI = CompletableFuture
            .supplyAsync(() -> searchExternalAPI(query), ioExecutor);

        CompletableFuture<List<String>> cache = CompletableFuture
            .supplyAsync(() -> searchCache(query), ioExecutor);

        // Aggregate all results
        CompletableFuture<List<String>> aggregated = CompletableFuture
            .allOf(productsDB, externalAPI, cache)
            .thenApply(v -> {
                List<String> results = productsDB.join();
                results.addAll(externalAPI.join());
                results.addAll(cache.join());
                return results.stream().distinct().collect(Collectors.toList());
            });

        List<String> results = aggregated.join();
        System.out.println("Search results (" + results.size() + " total): " + results);
        System.out.println();
    }

    /**
     * Example 7: File Processing Pipeline
     *
     * Read file -> Parse -> Transform -> Validate -> Save
     */
    private static void example7_FileProcessingPipeline() {
        System.out.println("--- Example 7: File Processing Pipeline ---");

        String filename = "data.csv";

        CompletableFuture<String> pipeline = CompletableFuture
            .supplyAsync(() -> {
                System.out.println("1. Reading file: " + filename);
                sleep(300);
                return readFile(filename);
            }, ioExecutor)
            .thenApply(content -> {
                System.out.println("2. Parsing CSV...");
                return parseCSV(content);
            })
            .thenApply(data -> {
                System.out.println("3. Transforming data...");
                return transformData(data);
            })
            .thenApply(transformed -> {
                System.out.println("4. Validating data...");
                validateData(transformed);
                return transformed;
            })
            .thenApplyAsync(validated -> {
                System.out.println("5. Saving to database...");
                sleep(400);
                saveToDatabase(validated);
                return "Processed " + filename + " successfully";
            }, ioExecutor)
            .exceptionally(ex -> {
                System.err.println("Pipeline failed: " + ex.getMessage());
                return "Processing failed";
            })
            .whenComplete((result, ex) -> {
                System.out.println("6. Cleanup and logging...");
            });

        String result = pipeline.join();
        System.out.println("Result: " + result);
        System.out.println();
    }

    // ========== Helper Classes ==========

    static class UserInfo {
        String id, name, email;
        UserInfo(String id, String name, String email) {
            this.id = id; this.name = name; this.email = email;
        }
        @Override
        public String toString() {
            return "UserInfo{id='" + id + "', name='" + name + "', email='" + email + "'}";
        }
    }

    static class Order {
        String orderId, productName;
        double amount;
        Order(String orderId, String productName, double amount) {
            this.orderId = orderId; this.productName = productName; this.amount = amount;
        }
        @Override
        public String toString() {
            return "Order{id='" + orderId + "', product='" + productName + "', amount=" + amount + "}";
        }
    }

    static class UserProfile {
        UserInfo user;
        List<Order> orders;
        List<String> recommendations;
        UserProfile(UserInfo user, List<Order> orders, List<String> recommendations) {
            this.user = user; this.orders = orders; this.recommendations = recommendations;
        }
        @Override
        public String toString() {
            return "UserProfile{user=" + user + ", orders=" + orders.size() + ", recommendations=" + recommendations + "}";
        }
    }

    static class Product {
        String id, name;
        double price;
        Product(String id, String name, double price) {
            this.id = id; this.name = name; this.price = price;
        }
        @Override
        public String toString() {
            return "Product{id='" + id + "', name='" + name + "', price=" + price + "}";
        }
    }

    // ========== Simulated Service Methods ==========

    private static Product getFromCache(String id) {
        sleep(50);
        return Math.random() > 0.5 ? new Product(id, "Cached Product", 99.99) : null;
    }

    private static CompletableFuture<Product> getFromDatabase(String id) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(300);
            return new Product(id, "Database Product", 129.99);
        }, ioExecutor);
    }

    private static Product getDefaultProduct() {
        return new Product("DEFAULT", "Default Product", 0.0);
    }

    private static void updateCacheAsync(String id, Product product) {
        CompletableFuture.runAsync(() -> {
            sleep(50);
            System.out.println("Cache updated for: " + id);
        }, ioExecutor);
    }

    private static void validateOrder(Order order) {
        if (order.amount <= 0) throw new RuntimeException("Invalid order amount");
    }

    private static void checkInventory(String product) {
        if (Math.random() > 0.9) throw new RuntimeException("Out of stock");
    }

    private static String processPayment(double amount) {
        return "PAY" + System.currentTimeMillis();
    }

    private static void reserveInventory(String product) {
        // Reserve inventory
    }

    private static void sendConfirmation(String orderId) {
        // Send email
    }

    private static boolean callUserService(String userId) {
        sleep(200);
        System.out.println("User service validated: " + userId);
        return true;
    }

    private static boolean callFlightService(String flightId) {
        sleep(300);
        System.out.println("Flight available: " + flightId);
        return true;
    }

    private static CompletableFuture<String> callBookingService(String userId, String flightId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(400);
            return "BOOK" + System.currentTimeMillis();
        }, ioExecutor);
    }

    private static void sendBookingNotification(String bookingId) {
        sleep(100);
        System.out.println("Notification sent for: " + bookingId);
    }

    private static String sendEmail(String userId, String message) {
        sleep(200);
        return "Email sent successfully";
    }

    private static String sendSMS(String userId, String message) {
        sleep(150);
        if (Math.random() > 0.8) throw new RuntimeException("SMS gateway error");
        return "SMS sent successfully";
    }

    private static String sendPushNotification(String userId, String message) {
        sleep(100);
        return "Push notification sent successfully";
    }

    private static List<String> searchProductDatabase(String query) {
        sleep(300);
        return Arrays.asList("DB: Laptop Pro", "DB: Laptop Air");
    }

    private static List<String> searchExternalAPI(String query) {
        sleep(500);
        return Arrays.asList("API: Laptop Ultra", "API: Laptop Plus");
    }

    private static List<String> searchCache(String query) {
        sleep(50);
        return Arrays.asList("Cache: Laptop Basic");
    }

    private static String readFile(String filename) {
        return "id,name,value\n1,John,100\n2,Jane,200";
    }

    private static String parseCSV(String content) {
        return content.replace(",", "|");
    }

    private static String transformData(String data) {
        return data.toUpperCase();
    }

    private static void validateData(String data) {
        if (data.isEmpty()) throw new RuntimeException("Empty data");
    }

    private static void saveToDatabase(String data) {
        // Save to DB
    }

    private static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}

/**
 * KEY PATTERNS FROM REAL-WORLD EXAMPLES:
 *
 * 1. PARALLEL API CALLS:
 *    - Launch all independent calls simultaneously
 *    - Combine results with thenCombine()
 *    - Total time = max(call times), not sum
 *
 * 2. FALLBACK PATTERN:
 *    - Try primary source
 *    - Fall back to secondary on failure
 *    - Use thenCompose() for conditional chaining
 *
 * 3. PIPELINE PATTERN:
 *    - Chain dependent operations sequentially
 *    - Each stage depends on previous result
 *    - Use thenApply() or thenApplyAsync()
 *
 * 4. FIRE-AND-FORGET:
 *    - Launch async operations without waiting
 *    - Use runAsync() for side effects
 *    - Good for notifications, logging, metrics
 *
 * 5. AGGREGATION:
 *    - Collect results from multiple sources
 *    - Use allOf() + stream().map(join())
 *    - Handle partial failures gracefully
 *
 * 6. COMPENSATION:
 *    - Implement rollback logic in exceptionally()
 *    - Maintain transaction consistency
 *    - Clean up on failure
 *
 * 7. RESILIENCE:
 *    - Handle exceptions at each stage
 *    - Provide default values
 *    - Don't let one failure break entire flow
 *
 * PERFORMANCE CONSIDERATIONS:
 *
 * - Use custom executor for I/O operations
 * - Group related operations
 * - Avoid unnecessary async boundaries
 * - Monitor thread pool usage
 * - Set appropriate timeouts
 * - Cache frequently accessed data
 * - Batch where possible
 */
