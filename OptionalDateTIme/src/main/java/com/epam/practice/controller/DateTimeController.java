package com.epam.practice.controller;

import com.epam.practice.service.DateTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/datetime")
public class DateTimeController {

    @Autowired
    private DateTimeService dateTimeService;

    // ==================== LocalDate Endpoints ====================

    @GetMapping("/localdate/now")
    public ResponseEntity<Map<String, String>> localDateNow() {
        Map<String, String> response = new HashMap<>();
        response.put("method", "LocalDate.now()");
        response.put("result", dateTimeService.demonstrateLocalDateNow());
        response.put("description", "Gets the current date in the default timezone and another timezone");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/localdate/of")
    public ResponseEntity<Map<String, String>> localDateOf() {
        Map<String, String> response = new HashMap<>();
        response.put("method", "LocalDate.of()");
        response.put("result", dateTimeService.demonstrateLocalDateOf());
        response.put("description", "Creates a LocalDate from year, month, and day");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/localdate/parse")
    public ResponseEntity<Map<String, String>> localDateParse() {
        Map<String, String> response = new HashMap<>();
        response.put("method", "LocalDate.parse()");
        response.put("result", dateTimeService.demonstrateLocalDateParse());
        response.put("description", "Parses a string to LocalDate with default or custom pattern");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/localdate/manipulation")
    public ResponseEntity<Map<String, String>> localDateManipulation() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "plusDays(), plusWeeks(), plusMonths(), plusYears(), minusDays()");
        response.put("result", dateTimeService.demonstrateLocalDateManipulation());
        response.put("description", "Add or subtract time periods from dates");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/localdate/with")
    public ResponseEntity<Map<String, String>> localDateWith() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "withYear(), withMonth(), withDayOfMonth()");
        response.put("result", dateTimeService.demonstrateLocalDateWith());
        response.put("description", "Adjust specific fields of a date");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/localdate/adjusters")
    public ResponseEntity<Map<String, String>> localDateAdjusters() {
        Map<String, String> response = new HashMap<>();
        response.put("method", "TemporalAdjusters");
        response.put("result", dateTimeService.demonstrateLocalDateAdjusters());
        response.put("description", "Use TemporalAdjusters for complex date adjustments");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/localdate/comparison")
    public ResponseEntity<Map<String, String>> localDateComparison() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "isBefore(), isAfter(), isEqual(), compareTo()");
        response.put("result", dateTimeService.demonstrateLocalDateComparison());
        response.put("description", "Compare two dates");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/localdate/query")
    public ResponseEntity<Map<String, String>> localDateQuery() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "getYear(), getMonth(), getDayOfMonth(), getDayOfWeek(), etc.");
        response.put("result", dateTimeService.demonstrateLocalDateQuery());
        response.put("description", "Query various properties of a date");
        return ResponseEntity.ok(response);
    }

    // ==================== LocalTime Endpoints ====================

    @GetMapping("/localtime/basic")
    public ResponseEntity<Map<String, String>> localTime() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "LocalTime.now(), of(), parse()");
        response.put("result", dateTimeService.demonstrateLocalTime());
        response.put("description", "Create LocalTime instances");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/localtime/manipulation")
    public ResponseEntity<Map<String, String>> localTimeManipulation() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "plusHours(), plusMinutes(), plusSeconds(), minusHours()");
        response.put("result", dateTimeService.demonstrateLocalTimeManipulation());
        response.put("description", "Add or subtract time from LocalTime");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/localtime/with")
    public ResponseEntity<Map<String, String>> localTimeWith() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "withHour(), withMinute(), withSecond()");
        response.put("result", dateTimeService.demonstrateLocalTimeWith());
        response.put("description", "Adjust specific fields of time");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/localtime/query")
    public ResponseEntity<Map<String, String>> localTimeQuery() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "getHour(), getMinute(), getSecond(), getNano()");
        response.put("result", dateTimeService.demonstrateLocalTimeQuery());
        response.put("description", "Query time components");
        return ResponseEntity.ok(response);
    }

    // ==================== LocalDateTime Endpoints ====================

    @GetMapping("/localdatetime/basic")
    public ResponseEntity<Map<String, String>> localDateTime() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "LocalDateTime.now(), of(), parse()");
        response.put("result", dateTimeService.demonstrateLocalDateTime());
        response.put("description", "Create LocalDateTime instances");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/localdatetime/manipulation")
    public ResponseEntity<Map<String, String>> localDateTimeManipulation() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "plusDays(), plusHours(), minusMonths()");
        response.put("result", dateTimeService.demonstrateLocalDateTimeManipulation());
        response.put("description", "Add or subtract both date and time");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/localdatetime/conversion")
    public ResponseEntity<Map<String, String>> localDateTimeConversion() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "toLocalDate(), toLocalTime()");
        response.put("result", dateTimeService.demonstrateLocalDateTimeConversion());
        response.put("description", "Convert LocalDateTime to LocalDate or LocalTime");
        return ResponseEntity.ok(response);
    }

    // ==================== ZonedDateTime Endpoints ====================

    @GetMapping("/zoneddatetime/basic")
    public ResponseEntity<Map<String, String>> zonedDateTime() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "ZonedDateTime.now(), of()");
        response.put("result", dateTimeService.demonstrateZonedDateTime());
        response.put("description", "Create ZonedDateTime with timezone information");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/zoneddatetime/conversion")
    public ResponseEntity<Map<String, String>> zoneConversion() {
        Map<String, String> response = new HashMap<>();
        response.put("method", "withZoneSameInstant()");
        response.put("result", dateTimeService.demonstrateZoneConversion());
        response.put("description", "Convert time between different timezones");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/zoneddatetime/zones")
    public ResponseEntity<Map<String, String>> availableZones() {
        Map<String, String> response = new HashMap<>();
        response.put("method", "ZoneId.getAvailableZoneIds()");
        response.put("result", dateTimeService.demonstrateAvailableZones());
        response.put("description", "List all available timezone IDs");
        return ResponseEntity.ok(response);
    }

    // ==================== Instant Endpoints ====================

    @GetMapping("/instant/basic")
    public ResponseEntity<Map<String, String>> instant() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "Instant.now(), ofEpochSecond(), ofEpochMilli()");
        response.put("result", dateTimeService.demonstrateInstant());
        response.put("description", "Work with machine-readable timestamps");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/instant/operations")
    public ResponseEntity<Map<String, String>> instantOperations() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "plusSeconds(), plus(), minus()");
        response.put("result", dateTimeService.demonstrateInstantOperations());
        response.put("description", "Manipulate Instant values");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/instant/conversion")
    public ResponseEntity<Map<String, String>> instantConversion() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "atZone(), toInstant()");
        response.put("result", dateTimeService.demonstrateInstantConversion());
        response.put("description", "Convert between Instant and ZonedDateTime");
        return ResponseEntity.ok(response);
    }

    // ==================== Duration Endpoints ====================

    @GetMapping("/duration/basic")
    public ResponseEntity<Map<String, String>> duration() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "Duration.ofHours(), ofMinutes(), ofSeconds(), of()");
        response.put("result", dateTimeService.demonstrateDuration());
        response.put("description", "Create time-based durations");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/duration/between")
    public ResponseEntity<Map<String, String>> durationBetween() {
        Map<String, String> response = new HashMap<>();
        response.put("method", "Duration.between()");
        response.put("result", dateTimeService.demonstrateDurationBetween());
        response.put("description", "Calculate duration between two time points");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/duration/arithmetic")
    public ResponseEntity<Map<String, String>> durationArithmetic() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "plus(), minus(), multipliedBy(), dividedBy()");
        response.put("result", dateTimeService.demonstrateDurationArithmetic());
        response.put("description", "Perform arithmetic operations on durations");
        return ResponseEntity.ok(response);
    }

    // ==================== Period Endpoints ====================

    @GetMapping("/period/basic")
    public ResponseEntity<Map<String, String>> period() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "Period.ofYears(), ofMonths(), ofWeeks(), ofDays(), of()");
        response.put("result", dateTimeService.demonstratePeriod());
        response.put("description", "Create date-based periods");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/period/between")
    public ResponseEntity<Map<String, String>> periodBetween() {
        Map<String, String> response = new HashMap<>();
        response.put("method", "Period.between()");
        response.put("result", dateTimeService.demonstratePeriodBetween());
        response.put("description", "Calculate period between two dates");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/period/arithmetic")
    public ResponseEntity<Map<String, String>> periodArithmetic() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "plus(), multipliedBy()");
        response.put("result", dateTimeService.demonstratePeriodArithmetic());
        response.put("description", "Perform arithmetic operations on periods");
        return ResponseEntity.ok(response);
    }

    // ==================== DateTimeFormatter Endpoints ====================

    @GetMapping("/formatter/predefined")
    public ResponseEntity<Map<String, String>> predefinedFormatters() {
        Map<String, String> response = new HashMap<>();
        response.put("methods", "ISO_DATE_TIME, ISO_DATE, ISO_TIME, BASIC_ISO_DATE");
        response.put("result", dateTimeService.demonstratePredefinedFormatters());
        response.put("description", "Use predefined formatters");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/formatter/custom")
    public ResponseEntity<Map<String, String>> customFormatters() {
        Map<String, String> response = new HashMap<>();
        response.put("method", "DateTimeFormatter.ofPattern()");
        response.put("result", dateTimeService.demonstrateCustomFormatters());
        response.put("description", "Create custom date/time formats");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/formatter/parsing")
    public ResponseEntity<Map<String, String>> formatterParsing() {
        Map<String, String> response = new HashMap<>();
        response.put("method", "LocalDateTime.parse(string, formatter)");
        response.put("result", dateTimeService.demonstrateFormatterParsing());
        response.put("description", "Parse strings with custom formatters");
        return ResponseEntity.ok(response);
    }

    // ==================== Real-world Scenario Endpoints ====================

    @GetMapping("/scenario/age")
    public ResponseEntity<Map<String, String>> calculateAge(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate) {
        Map<String, String> response = new HashMap<>();
        response.put("scenario", "Calculate age from birth date");
        response.put("result", dateTimeService.calculateAge(birthDate));
        response.put("example", "Use: /api/datetime/scenario/age?birthDate=1990-01-15");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/scenario/days-until")
    public ResponseEntity<Map<String, String>> daysUntilEvent(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate eventDate) {
        Map<String, String> response = new HashMap<>();
        response.put("scenario", "Calculate days until an event");
        response.put("result", dateTimeService.daysUntilEvent(eventDate));
        response.put("example", "Use: /api/datetime/scenario/days-until?eventDate=2024-12-25");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/scenario/business-days")
    public ResponseEntity<Map<String, String>> calculateBusinessDays(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        Map<String, String> response = new HashMap<>();
        response.put("scenario", "Calculate business days between two dates");
        response.put("result", dateTimeService.calculateBusinessDays(start, end));
        response.put("example", "Use: /api/datetime/scenario/business-days?start=2024-06-01&end=2024-06-30");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/scenario/meeting")
    public ResponseEntity<Map<String, String>> scheduleMeeting(
            @RequestParam int hour,
            @RequestParam int minute) {
        Map<String, String> response = new HashMap<>();
        response.put("scenario", "Schedule meeting across timezones");
        response.put("result", dateTimeService.scheduleMeeting(hour, minute));
        response.put("example", "Use: /api/datetime/scenario/meeting?hour=14&minute=30");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/scenario/deadline")
    public ResponseEntity<Map<String, String>> timeUntilDeadline(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deadline) {
        Map<String, String> response = new HashMap<>();
        response.put("scenario", "Calculate time remaining until deadline");
        response.put("result", dateTimeService.timeUntilDeadline(deadline));
        response.put("example", "Use: /api/datetime/scenario/deadline?deadline=2024-12-31T23:59:59");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/methods")
    public ResponseEntity<Map<String, Object>> listAllMethods() {
        Map<String, Object> categories = new HashMap<>();

        Map<String, String> localDate = new HashMap<>();
        localDate.put("1", "GET /api/datetime/localdate/now");
        localDate.put("2", "GET /api/datetime/localdate/of");
        localDate.put("3", "GET /api/datetime/localdate/parse");
        localDate.put("4", "GET /api/datetime/localdate/manipulation");
        localDate.put("5", "GET /api/datetime/localdate/with");
        localDate.put("6", "GET /api/datetime/localdate/adjusters");
        localDate.put("7", "GET /api/datetime/localdate/comparison");
        localDate.put("8", "GET /api/datetime/localdate/query");

        Map<String, String> localTime = new HashMap<>();
        localTime.put("1", "GET /api/datetime/localtime/basic");
        localTime.put("2", "GET /api/datetime/localtime/manipulation");
        localTime.put("3", "GET /api/datetime/localtime/with");
        localTime.put("4", "GET /api/datetime/localtime/query");

        Map<String, String> localDateTime = new HashMap<>();
        localDateTime.put("1", "GET /api/datetime/localdatetime/basic");
        localDateTime.put("2", "GET /api/datetime/localdatetime/manipulation");
        localDateTime.put("3", "GET /api/datetime/localdatetime/conversion");

        Map<String, String> zonedDateTime = new HashMap<>();
        zonedDateTime.put("1", "GET /api/datetime/zoneddatetime/basic");
        zonedDateTime.put("2", "GET /api/datetime/zoneddatetime/conversion");
        zonedDateTime.put("3", "GET /api/datetime/zoneddatetime/zones");

        Map<String, String> instant = new HashMap<>();
        instant.put("1", "GET /api/datetime/instant/basic");
        instant.put("2", "GET /api/datetime/instant/operations");
        instant.put("3", "GET /api/datetime/instant/conversion");

        Map<String, String> duration = new HashMap<>();
        duration.put("1", "GET /api/datetime/duration/basic");
        duration.put("2", "GET /api/datetime/duration/between");
        duration.put("3", "GET /api/datetime/duration/arithmetic");

        Map<String, String> period = new HashMap<>();
        period.put("1", "GET /api/datetime/period/basic");
        period.put("2", "GET /api/datetime/period/between");
        period.put("3", "GET /api/datetime/period/arithmetic");

        Map<String, String> formatter = new HashMap<>();
        formatter.put("1", "GET /api/datetime/formatter/predefined");
        formatter.put("2", "GET /api/datetime/formatter/custom");
        formatter.put("3", "GET /api/datetime/formatter/parsing");

        categories.put("LocalDate", localDate);
        categories.put("LocalTime", localTime);
        categories.put("LocalDateTime", localDateTime);
        categories.put("ZonedDateTime", zonedDateTime);
        categories.put("Instant", instant);
        categories.put("Duration", duration);
        categories.put("Period", period);
        categories.put("DateTimeFormatter", formatter);

        return ResponseEntity.ok(categories);
    }
}
