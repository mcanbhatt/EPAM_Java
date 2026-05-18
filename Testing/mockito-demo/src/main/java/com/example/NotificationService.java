package com.example;

public interface NotificationService {
    void notifyOutOfStock(Long userId);
    void notifyPaymentFailed(Long userId);
    void notifyOrderSuccess(Long userId, Ordr order);
    void notifyCancellation(Long userId);
}
