package com.example;

import java.util.List;

public interface InventoryService {
    boolean checkAvailability(List<String> items);
    void reserveItems(List<String> items);
    void releaseItems(List<String> items);
    void deductItems(List<String> items);
}
