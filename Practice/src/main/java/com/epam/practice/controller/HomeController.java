package com.epam.practice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Welcome to Spring Boot Practice Application!";
    }

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello from the API!";
    }

}
