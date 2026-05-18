package com.example;

public interface PaymentProcessor {
    boolean processPayment(Long userId, double amount);
    void refund(Long userId, double amount);
}
