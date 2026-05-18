package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class with static methods for date operations
 * Used to demonstrate static method mocking
 */
public class DateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Static method - returns current time
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    // Static method - formats date
    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }

    // Static method - validates date
    public static boolean isInFuture(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now());
    }

    // Static method - calculates age
    public static int calculateAge(LocalDateTime birthDate) {
        LocalDateTime now = LocalDateTime.now();
        return now.getYear() - birthDate.getYear();
    }

    // Private constructor to prevent instantiation
    private DateUtils() {
        throw new AssertionError("Utility class cannot be instantiated");
    }
}
