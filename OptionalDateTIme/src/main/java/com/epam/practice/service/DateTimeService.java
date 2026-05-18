package com.epam.practice.service;

import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class DateTimeService {

    // ==================== LocalDate Methods ====================

    // LocalDate.now() - Get current date
    public String demonstrateLocalDateNow() {
        LocalDate today = LocalDate.now();
        LocalDate todayInTokyo = LocalDate.now(ZoneId.of("Asia/Tokyo"));
        return String.format("Today: %s, Tokyo: %s", today, todayInTokyo);
    }

    // LocalDate.of() - Create specific date
    public String demonstrateLocalDateOf() {
        LocalDate date = LocalDate.of(2024, 12, 25);
        LocalDate date2 = LocalDate.of(2024, Month.DECEMBER, 25);
        return String.format("Christmas 2024: %s (or %s)", date, date2);
    }

    // LocalDate.parse() - Parse from string
    public String demonstrateLocalDateParse() {
        try {
            LocalDate date1 = LocalDate.parse("2024-12-25");
            LocalDate date2 = LocalDate.parse("25/12/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            return String.format("Parsed: %s and %s", date1, date2);
        } catch (DateTimeParseException e) {
            return "Parse error: " + e.getMessage();
        }
    }

    // LocalDate manipulation methods
    public String demonstrateLocalDateManipulation() {
        LocalDate date = LocalDate.of(2024, 6, 15);
        LocalDate plusDays = date.plusDays(10);
        LocalDate plusWeeks = date.plusWeeks(2);
        LocalDate plusMonths = date.plusMonths(3);
        LocalDate plusYears = date.plusYears(1);
        LocalDate minusDays = date.minusDays(5);

        return String.format("Original: %s, +10 days: %s, +2 weeks: %s, +3 months: %s, +1 year: %s, -5 days: %s",
                date, plusDays, plusWeeks, plusMonths, plusYears, minusDays);
    }

    // LocalDate with() methods - Adjust date
    public String demonstrateLocalDateWith() {
        LocalDate date = LocalDate.of(2024, 6, 15);
        LocalDate withYear = date.withYear(2025);
        LocalDate withMonth = date.withMonth(12);
        LocalDate withDay = date.withDayOfMonth(1);

        return String.format("Original: %s, withYear(2025): %s, withMonth(12): %s, withDay(1): %s",
                date, withYear, withMonth, withDay);
    }

    // LocalDate TemporalAdjusters
    public String demonstrateLocalDateAdjusters() {
        LocalDate date = LocalDate.of(2024, 6, 15);
        LocalDate firstDay = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDay = date.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate nextMonday = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate firstMonday = date.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

        return String.format("Date: %s, First of month: %s, Last of month: %s, Next Monday: %s, First Monday: %s",
                date, firstDay, lastDay, nextMonday, firstMonday);
    }

    // LocalDate comparison methods
    public String demonstrateLocalDateComparison() {
        LocalDate date1 = LocalDate.of(2024, 6, 15);
        LocalDate date2 = LocalDate.of(2024, 12, 25);

        return String.format("date1: %s, date2: %s, isBefore: %s, isAfter: %s, isEqual: %s, compareTo: %d",
                date1, date2, date1.isBefore(date2), date1.isAfter(date2),
                date1.isEqual(date2), date1.compareTo(date2));
    }

    // LocalDate query methods
    public String demonstrateLocalDateQuery() {
        LocalDate date = LocalDate.of(2024, 6, 15);
        return String.format("Date: %s, Year: %d, Month: %s, Day: %d, DayOfWeek: %s, DayOfYear: %d, " +
                        "LengthOfMonth: %d, LengthOfYear: %d, IsLeapYear: %s",
                date, date.getYear(), date.getMonth(), date.getDayOfMonth(),
                date.getDayOfWeek(), date.getDayOfYear(),
                date.lengthOfMonth(), date.lengthOfYear(), date.isLeapYear());
    }

    // ==================== LocalTime Methods ====================

    // LocalTime.now() and of()
    public String demonstrateLocalTime() {
        LocalTime now = LocalTime.now();
        LocalTime specific = LocalTime.of(14, 30, 45);
        LocalTime withNanos = LocalTime.of(14, 30, 45, 123456789);
        LocalTime parsed = LocalTime.parse("14:30:45");

        return String.format("Now: %s, Specific: %s, WithNanos: %s, Parsed: %s",
                now, specific, withNanos, parsed);
    }

    // LocalTime manipulation
    public String demonstrateLocalTimeManipulation() {
        LocalTime time = LocalTime.of(10, 30, 0);
        LocalTime plusHours = time.plusHours(5);
        LocalTime plusMinutes = time.plusMinutes(45);
        LocalTime plusSeconds = time.plusSeconds(30);
        LocalTime minusHours = time.minusHours(2);

        return String.format("Original: %s, +5h: %s, +45m: %s, +30s: %s, -2h: %s",
                time, plusHours, plusMinutes, plusSeconds, minusHours);
    }

    // LocalTime with() methods
    public String demonstrateLocalTimeWith() {
        LocalTime time = LocalTime.of(10, 30, 45);
        LocalTime withHour = time.withHour(14);
        LocalTime withMinute = time.withMinute(0);
        LocalTime withSecond = time.withSecond(0);

        return String.format("Original: %s, withHour(14): %s, withMinute(0): %s, withSecond(0): %s",
                time, withHour, withMinute, withSecond);
    }

    // LocalTime comparison and query
    public String demonstrateLocalTimeQuery() {
        LocalTime time = LocalTime.of(14, 30, 45);
        return String.format("Time: %s, Hour: %d, Minute: %d, Second: %d, Nano: %d",
                time, time.getHour(), time.getMinute(), time.getSecond(), time.getNano());
    }

    // ==================== LocalDateTime Methods ====================

    // LocalDateTime.now() and of()
    public String demonstrateLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime specific = LocalDateTime.of(2024, 12, 25, 10, 30, 0);
        LocalDateTime fromDateAndTime = LocalDateTime.of(LocalDate.of(2024, 12, 25), LocalTime.of(10, 30));
        LocalDateTime parsed = LocalDateTime.parse("2024-12-25T10:30:00");

        return String.format("Now: %s, Specific: %s, FromDateAndTime: %s, Parsed: %s",
                now, specific, fromDateAndTime, parsed);
    }

    // LocalDateTime manipulation
    public String demonstrateLocalDateTimeManipulation() {
        LocalDateTime dt = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
        LocalDateTime plusDays = dt.plusDays(5);
        LocalDateTime plusHours = dt.plusHours(3);
        LocalDateTime minusMonths = dt.minusMonths(2);

        return String.format("Original: %s, +5 days: %s, +3 hours: %s, -2 months: %s",
                dt, plusDays, plusHours, minusMonths);
    }

    // LocalDateTime conversion
    public String demonstrateLocalDateTimeConversion() {
        LocalDateTime dt = LocalDateTime.of(2024, 6, 15, 10, 30, 0);
        LocalDate date = dt.toLocalDate();
        LocalTime time = dt.toLocalTime();

        return String.format("DateTime: %s, Date: %s, Time: %s", dt, date, time);
    }

    // ==================== ZonedDateTime Methods ====================

    // ZonedDateTime.now() and of()
    public String demonstrateZonedDateTime() {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokyo = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        ZonedDateTime nyc = ZonedDateTime.now(ZoneId.of("America/New_York"));
        ZonedDateTime specific = ZonedDateTime.of(2024, 12, 25, 10, 30, 0, 0, ZoneId.of("UTC"));

        return String.format("Local: %s, Tokyo: %s, NYC: %s, Specific UTC: %s",
                now, tokyo, nyc, specific);
    }

    // ZonedDateTime zone conversion
    public String demonstrateZoneConversion() {
        ZonedDateTime utc = ZonedDateTime.of(2024, 6, 15, 12, 0, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime tokyo = utc.withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
        ZonedDateTime nyc = utc.withZoneSameInstant(ZoneId.of("America/New_York"));

        return String.format("UTC: %s, Tokyo: %s, NYC: %s", utc, tokyo, nyc);
    }

    // Available zone IDs
    public String demonstrateAvailableZones() {
        Set<String> zones = ZoneId.getAvailableZoneIds();
        List<String> sampleZones = zones.stream().limit(10).toList();
        return String.format("Total zones: %d, Sample: %s", zones.size(), String.join(", ", sampleZones));
    }

    // ==================== Instant Methods ====================

    // Instant.now() and epoch operations
    public String demonstrateInstant() {
        Instant now = Instant.now();
        Instant epochStart = Instant.ofEpochSecond(0);
        Instant fromMilli = Instant.ofEpochMilli(1700000000000L);
        long epochSecond = now.getEpochSecond();
        long epochMilli = now.toEpochMilli();

        return String.format("Now: %s, Epoch start: %s, From milli: %s, Seconds: %d, Millis: %d",
                now, epochStart, fromMilli, epochSecond, epochMilli);
    }

    // Instant manipulation and comparison
    public String demonstrateInstantOperations() {
        Instant instant = Instant.now();
        Instant plus5Sec = instant.plusSeconds(5);
        Instant plus1Hour = instant.plus(1, ChronoUnit.HOURS);
        Instant minus10Min = instant.minus(10, ChronoUnit.MINUTES);

        return String.format("Now: %s, +5s: %s, +1h: %s, -10m: %s",
                instant, plus5Sec, plus1Hour, minus10Min);
    }

    // Instant to/from ZonedDateTime
    public String demonstrateInstantConversion() {
        Instant instant = Instant.now();
        ZonedDateTime zdt = instant.atZone(ZoneId.of("Asia/Tokyo"));
        Instant backToInstant = zdt.toInstant();

        return String.format("Instant: %s, As Tokyo ZDT: %s, Back to Instant: %s",
                instant, zdt, backToInstant);
    }

    // ==================== Duration Methods ====================

    // Duration - time-based amount
    public String demonstrateDuration() {
        Duration duration1 = Duration.ofHours(5);
        Duration duration2 = Duration.ofMinutes(30);
        Duration duration3 = Duration.ofSeconds(45);
        Duration duration4 = Duration.of(2, ChronoUnit.DAYS);

        return String.format("5 hours: %s, 30 minutes: %s, 45 seconds: %s, 2 days: %s",
                duration1, duration2, duration3, duration4);
    }

    // Duration between times
    public String demonstrateDurationBetween() {
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 30);
        Duration duration = Duration.between(start, end);

        long hours = duration.toHours();
        long minutes = duration.toMinutes();
        long seconds = duration.getSeconds();

        return String.format("Between %s and %s: %s (Hours: %d, Minutes: %d, Seconds: %d)",
                start, end, duration, hours, minutes, seconds);
    }

    // Duration arithmetic
    public String demonstrateDurationArithmetic() {
        Duration d1 = Duration.ofHours(2);
        Duration d2 = Duration.ofMinutes(30);
        Duration sum = d1.plus(d2);
        Duration diff = d1.minus(d2);
        Duration multiplied = d1.multipliedBy(3);
        Duration divided = d1.dividedBy(2);

        return String.format("d1: %s, d2: %s, sum: %s, diff: %s, d1*3: %s, d1/2: %s",
                d1, d2, sum, diff, multiplied, divided);
    }

    // ==================== Period Methods ====================

    // Period - date-based amount
    public String demonstratePeriod() {
        Period period1 = Period.ofYears(2);
        Period period2 = Period.ofMonths(6);
        Period period3 = Period.ofWeeks(3);
        Period period4 = Period.ofDays(10);
        Period combined = Period.of(1, 6, 15); // 1 year, 6 months, 15 days

        return String.format("2 years: %s, 6 months: %s, 3 weeks: %s, 10 days: %s, combined: %s",
                period1, period2, period3, period4, combined);
    }

    // Period between dates
    public String demonstratePeriodBetween() {
        LocalDate start = LocalDate.of(2020, 1, 1);
        LocalDate end = LocalDate.of(2024, 6, 15);
        Period period = Period.between(start, end);

        return String.format("Between %s and %s: %s (Years: %d, Months: %d, Days: %d)",
                start, end, period, period.getYears(), period.getMonths(), period.getDays());
    }

    // Period arithmetic
    public String demonstratePeriodArithmetic() {
        Period p1 = Period.ofYears(2);
        Period p2 = Period.ofMonths(6);
        Period sum = p1.plus(p2);
        Period multiplied = p1.multipliedBy(3);

        return String.format("p1: %s, p2: %s, sum: %s, p1*3: %s", p1, p2, sum, multiplied);
    }

    // ==================== DateTimeFormatter Methods ====================

    // Predefined formatters
    public String demonstratePredefinedFormatters() {
        LocalDateTime dt = LocalDateTime.of(2024, 6, 15, 14, 30, 45);

        String iso = dt.format(DateTimeFormatter.ISO_DATE_TIME);
        String isoDate = dt.format(DateTimeFormatter.ISO_DATE);
        String isoTime = dt.format(DateTimeFormatter.ISO_TIME);
        String basic = dt.format(DateTimeFormatter.BASIC_ISO_DATE);

        return String.format("ISO_DATE_TIME: %s, ISO_DATE: %s, ISO_TIME: %s, BASIC_ISO_DATE: %s",
                iso, isoDate, isoTime, basic);
    }

    // Custom formatters
    public String demonstrateCustomFormatters() {
        LocalDateTime dt = LocalDateTime.of(2024, 6, 15, 14, 30, 45);

        DateTimeFormatter custom1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter custom2 = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        DateTimeFormatter custom3 = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm");
        DateTimeFormatter custom4 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        return String.format("Pattern 1: %s, Pattern 2: %s, Pattern 3: %s, Pattern 4: %s",
                dt.format(custom1), dt.format(custom2), dt.format(custom3), dt.format(custom4));
    }

    // Parsing with formatters
    public String demonstrateFormatterParsing() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime parsed = LocalDateTime.parse("15/06/2024 14:30", formatter);
            return "Parsed: " + parsed;
        } catch (DateTimeParseException e) {
            return "Parse error: " + e.getMessage();
        }
    }

    // ==================== Real-world Scenarios ====================

    // Calculate age
    public String calculateAge(LocalDate birthDate) {
        Period age = Period.between(birthDate, LocalDate.now());
        return String.format("Age: %d years, %d months, %d days",
                age.getYears(), age.getMonths(), age.getDays());
    }

    // Calculate days until event
    public String daysUntilEvent(LocalDate eventDate) {
        long days = ChronoUnit.DAYS.between(LocalDate.now(), eventDate);
        return String.format("Days until %s: %d", eventDate, days);
    }

    // Business days calculation
    public String calculateBusinessDays(LocalDate start, LocalDate end) {
        long businessDays = start.datesUntil(end.plusDays(1))
                .filter(date -> date.getDayOfWeek() != DayOfWeek.SATURDAY
                        && date.getDayOfWeek() != DayOfWeek.SUNDAY)
                .count();

        return String.format("Business days between %s and %s: %d", start, end, businessDays);
    }

    // Time zone meeting scheduler
    public String scheduleMeeting(int hour, int minute) {
        ZonedDateTime meeting = ZonedDateTime.of(
                LocalDate.now(), LocalTime.of(hour, minute), ZoneId.of("America/New_York")
        );

        ZonedDateTime tokyo = meeting.withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
        ZonedDateTime london = meeting.withZoneSameInstant(ZoneId.of("Europe/London"));
        ZonedDateTime sydney = meeting.withZoneSameInstant(ZoneId.of("Australia/Sydney"));

        return String.format("Meeting at %s NYC is:\nTokyo: %s\nLondon: %s\nSydney: %s",
                meeting.toLocalTime(), tokyo.toLocalTime(), london.toLocalTime(), sydney.toLocalTime());
    }

    // Time remaining until deadline
    public String timeUntilDeadline(LocalDateTime deadline) {
        LocalDateTime now = LocalDateTime.now();
        Duration remaining = Duration.between(now, deadline);

        if (remaining.isNegative()) {
            return "Deadline has passed!";
        }

        long days = remaining.toDays();
        long hours = remaining.toHours() % 24;
        long minutes = remaining.toMinutes() % 60;
        
        // java 9 and later has toDaysPart(), toHoursPart(), toMinutesPart() for cleaner code:
        long days_9 = remaining.toDaysPart();
        long hours_9 = remaining.toHoursPart();
        long minutes_9 = remaining.toMinutesPart();
        
        System.out.println(String.format("Time remaining: %d days, %d hours, %d minutes", days_9, hours_9, minutes_9));

        return String.format("Time remaining: %d days, %d hours, %d minutes", days, hours, minutes);
    }
}
