package com.epam.practice.controller;

import com.epam.practice.service.OptionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/optional")
public class OptionalController {

    @Autowired
    private OptionalService optionalService;

    @GetMapping("/demo/of")
    public ResponseEntity<Map<String, String>> demonstrateOf() {
        Map<String, String> response = new HashMap<>();
        response.put("method", "Optional.of()");
        response.put("result", optionalService.demonstrateOf());
        response.put("description", "Creates an Optional with a non-null value. Throws NPE if value is null.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demo/ofNullable")
    public ResponseEntity<Map<String, String>> demonstrateOfNullable() {
        Map<String, String> response = new HashMap<>();
        response.put("method", "Optional.ofNullable()");
        response.put("result", optionalService.demonstrateOfNullable());
        response.put("description", "Creates an Optional that can handle null values safely.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demo/empty")
    public ResponseEntity<Map<String, String>> demonstrateEmpty() {
        Map<String, String> response = new HashMap<>();
        response.put("method", "Optional.empty()");
        response.put("result", optionalService.demonstrateEmpty());
        response.put("description", "Creates an empty Optional instance.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demo/isPresent/{userId}")
    public ResponseEntity<Map<String, String>> demonstrateIsPresent(@PathVariable Long userId) {
        Map<String, String> response = new HashMap<>();
        response.put("method", "isPresent()");
        response.put("result", optionalService.demonstrateIsPresent(userId));
        response.put("description", "Returns true if a value is present.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demo/isEmpty/{userId}")
    public ResponseEntity<Map<String, String>> demonstrateIsEmpty(@PathVariable Long userId) {
        Map<String, String> response = new HashMap<>();
        response.put("method", "isEmpty()");
        response.put("result", optionalService.demonstrateIsEmpty(userId));
        response.put("description", "Returns true if no value is present (Java 11+).");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demo/ifPresent/{userId}")
    public ResponseEntity<Map<String, String>> demonstrateIfPresent(@PathVariable Long userId) {
        Map<String, String> response = new HashMap<>();
        response.put("method", "ifPresent()");
        response.put("result", optionalService.demonstrateIfPresent(userId));
        response.put("description", "Executes the given action if a value is present.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demo/ifPresentOrElse/{userId}")
    public ResponseEntity<Map<String, String>> demonstrateIfPresentOrElse(@PathVariable Long userId) {
        Map<String, String> response = new HashMap<>();
        response.put("method", "ifPresentOrElse()");
        response.put("result", optionalService.demonstrateIfPresentOrElse(userId));
        response.put("description", "Executes action if present, else executes empty action (Java 9+).");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demo/orElse/{userId}")
    public ResponseEntity<Map<String, String>> demonstrateOrElse(@PathVariable Long userId) {
        Map<String, String> response = new HashMap<>();
        response.put("method", "orElse()");
        response.put("result", optionalService.demonstrateOrElse(userId));
        response.put("description", "Returns the value if present, otherwise returns the default value.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demo/orElseGet/{userId}")
    public ResponseEntity<Map<String, String>> demonstrateOrElseGet(@PathVariable Long userId) {
        Map<String, String> response = new HashMap<>();
        response.put("method", "orElseGet()");
        response.put("result", optionalService.demonstrateOrElseGet(userId));
        response.put("description", "Returns the value if present, otherwise invokes supplier and returns result.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demo/orElseThrow/{userId}")
    public ResponseEntity<Map<String, String>> demonstrateOrElseThrow(@PathVariable Long userId) {
        Map<String, String> response = new HashMap<>();
        response.put("method", "orElseThrow()");
        response.put("result", optionalService.demonstrateOrElseThrow(userId));
        response.put("description", "Returns the value if present, otherwise throws the supplied exception.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demo/map/{userId}")
    public ResponseEntity<Map<String, String>> demonstrateMap(@PathVariable Long userId) {
        Map<String, String> response = new HashMap<>();
        response.put("method", "map()");
        response.put("result", optionalService.demonstrateMap(userId));
        response.put("description", "Transforms the value if present using the provided function.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demo/flatMap/{userId}")
    public ResponseEntity<Map<String, String>> demonstrateFlatMap(@PathVariable Long userId) {
        Map<String, String> response = new HashMap<>();
        response.put("method", "flatMap()");
        response.put("result", optionalService.demonstrateFlatMap(userId));
        response.put("description", "Transforms the value to an Optional, avoiding nested Optionals.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demo/filter/{userId}")
    public ResponseEntity<Map<String, String>> demonstrateFilter(@PathVariable Long userId) {
        Map<String, String> response = new HashMap<>();
        response.put("method", "filter()");
        response.put("result", optionalService.demonstrateFilter(userId));
        response.put("description", "Filters the value based on a predicate.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demo/or/{userId}")
    public ResponseEntity<Map<String, String>> demonstrateOr(@PathVariable Long userId) {
        Map<String, String> response = new HashMap<>();
        response.put("method", "or()");
        response.put("result", optionalService.demonstrateOr(userId));
        response.put("description", "Returns the Optional if present, otherwise returns alternative Optional (Java 9+).");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/demo/stream")
    public ResponseEntity<Map<String, String>> demonstrateStream() {
        Map<String, String> response = new HashMap<>();
        response.put("method", "stream()");
        response.put("result", optionalService.demonstrateStream());
        response.put("description", "Converts Optional to a Stream (Java 9+).");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/scenario/formatted-info/{userId}")
    public ResponseEntity<Map<String, String>> getFormattedUserInfo(@PathVariable Long userId) {
        Map<String, String> response = new HashMap<>();
        response.put("scenario", "Chaining Optional operations");
        response.put("result", optionalService.getFormattedUserInfo(userId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/scenario/phone/{userId}")
    public ResponseEntity<Map<String, String>> getUserPhone(@PathVariable Long userId,
                                                             @RequestParam(defaultValue = "000-000-0000") String defaultPhone) {
        Map<String, String> response = new HashMap<>();
        response.put("scenario", "Safe navigation with Optional");
        response.put("result", optionalService.getUserPhoneOrDefault(userId, defaultPhone));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/scenario/contact/{userId}")
    public ResponseEntity<Map<String, String>> getContactInfo(@PathVariable Long userId) {
        Map<String, String> response = new HashMap<>();
        response.put("scenario", "Combining multiple Optional sources");
        response.put("result", optionalService.getContactInfo(userId));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(optionalService.getAllUsers());
    }

    @GetMapping("/methods")
    public ResponseEntity<?> listAllMethods() {
        Map<String, String> methods = new HashMap<>();
        methods.put("1", "GET /api/optional/demo/of - Optional.of() demonstration");
        methods.put("2", "GET /api/optional/demo/ofNullable - Optional.ofNullable() demonstration");
        methods.put("3", "GET /api/optional/demo/empty - Optional.empty() demonstration");
        methods.put("4", "GET /api/optional/demo/isPresent/{userId} - isPresent() demonstration");
        methods.put("5", "GET /api/optional/demo/isEmpty/{userId} - isEmpty() demonstration");
        methods.put("6", "GET /api/optional/demo/ifPresent/{userId} - ifPresent() demonstration");
        methods.put("7", "GET /api/optional/demo/ifPresentOrElse/{userId} - ifPresentOrElse() demonstration");
        methods.put("8", "GET /api/optional/demo/orElse/{userId} - orElse() demonstration");
        methods.put("9", "GET /api/optional/demo/orElseGet/{userId} - orElseGet() demonstration");
        methods.put("10", "GET /api/optional/demo/orElseThrow/{userId} - orElseThrow() demonstration");
        methods.put("11", "GET /api/optional/demo/map/{userId} - map() demonstration");
        methods.put("12", "GET /api/optional/demo/flatMap/{userId} - flatMap() demonstration");
        methods.put("13", "GET /api/optional/demo/filter/{userId} - filter() demonstration");
        methods.put("14", "GET /api/optional/demo/or/{userId} - or() demonstration");
        methods.put("15", "GET /api/optional/demo/stream - stream() demonstration");
        return ResponseEntity.ok(methods);
    }
}
