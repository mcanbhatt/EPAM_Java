package com.epam.practice.java8.ubs;
import java.util.*;
import java.util.stream.Collectors;

public class DynamicFizzBuzz {

    public static void main(String[] args) {

        RuleEngine engine = new RuleEngine();

        // Add rules dynamically
        engine.addRule(3, "Bizz");
        engine.addRule(5, "Fizz");
        engine.addRule(9, "Dizz");

        // Modify rule anytime
        // engine.addRule(7, "Jazz");

        // Remove rule
        // engine.removeRule(5);

        engine.print(20);
    }
}

// 🔹 Rule Engine (Core Logic)
class RuleEngine {

    private final Map<Integer, String> rules = new LinkedHashMap<>();

    public void addRule(int divisor, String word) {
        rules.put(divisor, word);
    }

    public void removeRule(int divisor) {
        rules.remove(divisor);
    }

    public void print(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(evaluate(i));
        }
    }

    private String evaluate(int num) {
        String result = rules.entrySet()
                .stream()
                .filter(e -> num % e.getKey() == 0)
                .map(Map.Entry::getValue)
                .collect(Collectors.joining());

        return result.isEmpty() ? String.valueOf(num) : result;
    }
}