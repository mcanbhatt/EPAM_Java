package com.example;

import java.util.List;

public class OrderService {

    private final PaymentProcessor paymentProcessor;
    private final InventoryService inventoryService;
    private final NotificationService notificationService;

    public OrderService(PaymentProcessor paymentProcessor,
                       InventoryService inventoryService,
                       NotificationService notificationService) {
        this.paymentProcessor = paymentProcessor;
        this.inventoryService = inventoryService;
        this.notificationService = notificationService;
    }

    public Ordr createOrder(Long userId, List<String> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain items");
        }

        Ordr order = new Ordr(userId, items);
        return order;
    }

    public boolean processOrder(Ordr order) {
        // Check inventory
        if (!inventoryService.checkAvailability(order.getItems())) {
            notificationService.notifyOutOfStock(order.getUserId());
            return false;
        }

        // Reserve items
        inventoryService.reserveItems(order.getItems());

        // Process payment
        boolean paymentSuccess = paymentProcessor.processPayment(
            order.getUserId(),
            order.getTotalAmount()
        );

        if (!paymentSuccess) {
            inventoryService.releaseItems(order.getItems());
            notificationService.notifyPaymentFailed(order.getUserId());
            return false;
        }

        // Complete order
        order.setStatus("COMPLETED");
        inventoryService.deductItems(order.getItems());
        notificationService.notifyOrderSuccess(order.getUserId(), order);

        return true;
    }

    public void cancelOrder(Ordr order) {
        if (order.getStatus().equals("COMPLETED")) {
            paymentProcessor.refund(order.getUserId(), order.getTotalAmount());
        }
        inventoryService.releaseItems(order.getItems());
        notificationService.notifyCancellation(order.getUserId());
    }
}
