package com.example;

/**
 * Configuration manager with static fields
 * Used to demonstrate static field access and mocking
 */
public class ConfigManager {

    // Static fields
    public static String APPLICATION_NAME = "MyApp";
    public static String VERSION = "1.0.0";
    public static int MAX_CONNECTIONS = 100;
    public static boolean DEBUG_MODE = false;

    // Static final field (cannot be mocked directly)
    public static final String ENVIRONMENT = "production";

    // Static method that uses static fields
    public static String getApplicationInfo() {
        return APPLICATION_NAME + " v" + VERSION;
    }

    // Static method that depends on static field
    public static boolean isDebugEnabled() {
        return DEBUG_MODE;
    }

    // Static method with static field logic
    public static boolean canAcceptConnection(int currentConnections) {
        return currentConnections < MAX_CONNECTIONS;
    }

    private ConfigManager() {
        throw new AssertionError("Utility class cannot be instantiated");
    }
}
