package com.example;

import java.time.LocalDateTime;

/**
 * Service that depends on static methods
 * Used to demonstrate testing with static method dependencies
 */
public class ReportService {

    public String generateReport(String title) {
        LocalDateTime now = DateUtils.getCurrentDateTime();
        String formattedDate = DateUtils.formatDate(now);

        return String.format("Report: %s\nGenerated: %s\nVersion: %s",
            title, formattedDate, ConfigManager.VERSION);
    }

    public boolean isReportTimely(LocalDateTime reportDate) {
        return !DateUtils.isInFuture(reportDate);
    }

    public String getSystemInfo() {
        return ConfigManager.getApplicationInfo() +
               (ConfigManager.isDebugEnabled() ? " (DEBUG)" : "");
    }

    public boolean validateConnectionLimit(int connections) {
        return ConfigManager.canAcceptConnection(connections);
    }
}
