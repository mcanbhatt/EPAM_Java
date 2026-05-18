package com.example;

public class StringProcessor {

    public String reverse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return new StringBuilder(input).reverse().toString();
    }

    public String toUpperCase(String input) {
        return input != null ? input.toUpperCase() : null;
    }

    public boolean isPalindrome(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        String cleaned = input.replaceAll("\\s+", "").toLowerCase();
        return cleaned.equals(new StringBuilder(cleaned).reverse().toString());
    }

    public int countVowels(String input) {
        if (input == null) return 0;
        return (int) input.toLowerCase()
                .chars()
                .filter(c -> "aeiou".indexOf(c) != -1)
                .count();
    }
}
