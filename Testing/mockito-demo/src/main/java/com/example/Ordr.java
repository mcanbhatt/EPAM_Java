package com.example;

import java.util.List;

public class Ordr {
    private Long id;
    private Long userId;
    private List<String> items;
    private String status;
    private double totalAmount;

    public Ordr(Long userId, List<String> items) {
        this.userId = userId;
        this.items = items;
        this.status = "PENDING";
        this.totalAmount = items.size() * 10.0; // Simple calculation
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
