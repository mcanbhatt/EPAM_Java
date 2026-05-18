package com.epam.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OptionalDateTimeApplication {

    public static void main(String[] args) {
        SpringApplication.run(OptionalDateTimeApplication.class, args);
        System.out.println("\n====================================");
        System.out.println("Application Started Successfully!");
        System.out.println("====================================");
        System.out.println("Access the application at: http://localhost:8080");
        System.out.println("\nAvailable Endpoints:");
        System.out.println("- Optional API: http://localhost:8080/api/optional/methods");
        System.out.println("- DateTime API: http://localhost:8080/api/datetime/methods");
        System.out.println("====================================\n");
    }
}
