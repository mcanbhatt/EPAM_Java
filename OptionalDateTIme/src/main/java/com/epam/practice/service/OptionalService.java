package com.epam.practice.service;

import com.epam.practice.model.Address;
import com.epam.practice.model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class OptionalService {

    private final Map<Long, User> userDatabase = new HashMap<>();

    public OptionalService() {
        // Initialize sample data
        userDatabase.put(1L, new User(1L, "John Doe", "john@example.com", "123-456-7890",
                new Address("123 Main St", "New York", "10001")));
        userDatabase.put(2L, new User(2L, "Jane Smith", "jane@example.com", null,
                new Address("456 Oak Ave", "Los Angeles", "90001")));
        userDatabase.put(3L, new User(3L, "Bob Johnson", null, "555-555-5555", null));
    }

    // 1. Optional.of() - Creates Optional with non-null value
    public String demonstrateOf() {
        try {
            Optional<String> name = Optional.of("John");
            // Optional<String> nullValue = Optional.of(null); // This will throw NullPointerException
            return "Optional.of() creates Optional with value: " + name.get();
        } catch (NullPointerException e) {
            return "Optional.of() with null throws NullPointerException";
        }
    }

    // 2. Optional.ofNullable() - Creates Optional that may contain null
    public String demonstrateOfNullable() {
        Optional<String> presentValue = Optional.ofNullable("Hello");
        Optional<String> emptyValue = Optional.ofNullable(null);

        return String.format("ofNullable with value: %s, ofNullable with null: %s",
                presentValue.isPresent(), emptyValue.isPresent());
    }

    // 3. Optional.empty() - Creates empty Optional
    public String demonstrateEmpty() {
        Optional<String> empty = Optional.empty();
        return "Optional.empty() is present: " + empty.isPresent();
    }

    // 4. isPresent() - Check if value exists
    public String demonstrateIsPresent(Long userId) {
        Optional<User> user = Optional.ofNullable(userDatabase.get(userId));
        return user.isPresent() ?
                "User found: " + user.get().getName() :
                "User not found";
    }

    // 5. isEmpty() - Check if Optional is empty (Java 11+)
    public String demonstrateIsEmpty(Long userId) {
        Optional<User> user = Optional.ofNullable(userDatabase.get(userId));
        return user.isEmpty() ? "User not found" : "User exists: " + user.get().getName();
    }

    // 6. ifPresent() - Execute action if value present
    public String demonstrateIfPresent(Long userId) {
        List<String> result = new ArrayList<>();
        Optional<User> user = Optional.ofNullable(userDatabase.get(userId));

        user.ifPresent(u -> result.add("User found: " + u.getName()));

        return result.isEmpty() ? "No user found" : result.get(0);
    }

    // 7. ifPresentOrElse() - Execute action if present, else execute empty action (Java 9+)
    public String demonstrateIfPresentOrElse(Long userId) {
        List<String> result = new ArrayList<>();
        Optional<User> user = Optional.ofNullable(userDatabase.get(userId));

        user.ifPresentOrElse(
                u -> result.add("User: " + u.getName()),
                () -> result.add("User not found, using default action")
        );

        return result.get(0);
    }

    // 8. orElse() - Return value or default
    public String demonstrateOrElse(Long userId) {
        User user = Optional.ofNullable(userDatabase.get(userId))
                .orElse(new User(0L, "Default User", "default@example.com", null, null));

        return "Retrieved user: " + user.getName();
    }

    // 9. orElseGet() - Return value or compute default lazily
    public String demonstrateOrElseGet(Long userId) {
        User user = Optional.ofNullable(userDatabase.get(userId))
                .orElseGet(() -> {
                    System.out.println("Creating default user...");
                    return new User(0L, "Generated User", "generated@example.com", null, null);
                });

        return "Retrieved user: " + user.getName();
    }

    // 10. orElseThrow() - Throw exception if empty
    public String demonstrateOrElseThrow(Long userId) {
        try {
            User user = Optional.ofNullable(userDatabase.get(userId))
                    .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));
            return "User found: " + user.getName();
        } catch (NoSuchElementException e) {
            return "Exception thrown: " + e.getMessage();
        }
    }

    // 11. map() - Transform value if present
    public String demonstrateMap(Long userId) {
        Optional<String> email = Optional.ofNullable(userDatabase.get(userId))
                .map(User::getEmail)
                .map(String::toUpperCase);

        return "Email (uppercase): " + email.orElse("No email");
    }

    // 12. flatMap() - Transform value to Optional (avoid nested Optionals)
    public String demonstrateFlatMap(Long userId) {
        Optional<String> city = Optional.ofNullable(userDatabase.get(userId))
                .flatMap(user -> Optional.ofNullable(user.getAddress()))
                .map(Address::getCity);

        return "City: " + city.orElse("No address");
    }

    // 13. filter() - Filter value based on predicate
    public String demonstrateFilter(Long userId) {
        Optional<User> gmailUser = Optional.ofNullable(userDatabase.get(userId))
                .filter(user -> user.getEmail() != null && user.getEmail().contains("@example.com"));

        return gmailUser.isPresent() ?
                "User with example.com email: " + gmailUser.get().getName() :
                "No user with example.com email";
    }

    // 14. or() - Return alternative Optional if empty (Java 9+)
    public String demonstrateOr(Long userId) {
        Optional<User> user = Optional.ofNullable(userDatabase.get(userId))
                .or(() -> Optional.of(new User(999L, "Alternative User", "alt@example.com", null, null)));

        return "User: " + user.get().getName();
    }

    // 15. stream() - Convert Optional to Stream (Java 9+)
    public String demonstrateStream() {
        List<String> names = Stream.of(1L, 2L, 99L)
                .map(id -> Optional.ofNullable(userDatabase.get(id)))
                .flatMap(Optional::stream)
                .map(User::getName)
                .toList();

        return "Found users: " + String.join(", ", names);
    }

    // Real-world scenario: Chaining Optional operations
    public String getFormattedUserInfo(Long userId) {
        return Optional.ofNullable(userDatabase.get(userId))
                .filter(user -> user.getEmail() != null)
                .map(user -> String.format("User: %s (%s) - %s",
                        user.getName(),
                        user.getEmail(),
                        Optional.ofNullable(user.getAddress())
                                .map(Address::getCity)
                                .orElse("No city")))
                .orElse("User information not available");
    }

    // Real-world scenario: Safe navigation with Optional
    public String getUserPhoneOrDefault(Long userId, String defaultPhone) {
        return Optional.ofNullable(userDatabase.get(userId))
                .map(User::getPhone)
                .filter(phone -> !phone.isEmpty())
                .orElse(defaultPhone);
    }

    // Real-world scenario: Combining multiple Optional sources
    public String getContactInfo(Long userId) {
        Optional<User> userOpt = Optional.ofNullable(userDatabase.get(userId));

        String email = userOpt.map(User::getEmail).orElse("N/A");
        String phone = userOpt.map(User::getPhone).orElse("N/A");

        return String.format("Contact Info - Email: %s, Phone: %s", email, phone);
    }

    // Get all users
    public Map<Long, User> getAllUsers() {
        return userDatabase;
    }
}
