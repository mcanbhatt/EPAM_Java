package com.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.EmptySource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("String Processor Tests")
class StringProcessorTest {

    private StringProcessor processor;

    @BeforeAll
    static void initAll() {
        System.out.println("@BeforeAll - runs once before all tests");
    }

    @BeforeEach
    void init() {
        processor = new StringProcessor();
        System.out.println("@BeforeEach - runs before each test");
    }

    @Test
    @DisplayName("Should reverse a simple string")
    void testReverse() {
        assertEquals("olleh", processor.reverse("hello"));
        assertEquals("54321", processor.reverse("12345"));
    }

    @Test
    @DisplayName("Should throw exception for null input")
    void testReverseNull() {
        assertThrows(IllegalArgumentException.class, () -> processor.reverse(null));
    }

    @Test
    @DisplayName("Should convert to uppercase")
    void testToUpperCase() {
        assertEquals("HELLO", processor.toUpperCase("hello"));
        assertNull(processor.toUpperCase(null));
    }

    @ParameterizedTest
    @DisplayName("Should identify palindromes")
    @ValueSource(strings = {"racecar", "madam", "A man a plan a canal Panama", "noon"})
    void testPalindromes(String input) {
        assertTrue(processor.isPalindrome(input));
    }

    @ParameterizedTest
    @DisplayName("Should count vowels correctly")
    @CsvSource({
        "hello, 2",
        "world, 1",
        "aeiou, 5",
        "xyz, 0",
        "HELLO, 2",
        "'HELLO, 2',2"
    })
    void testCountVowels(String input, int expected) {
        assertEquals(expected, processor.countVowels(input));
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @DisplayName("Should handle null and empty strings")
    void testPalindromeEdgeCases(String input) {
        assertFalse(processor.isPalindrome(input));
    }

    @Test
    @DisplayName("Should complete within timeout")
    @Timeout(1)
    void testTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> {
            processor.reverse("test");
        });
    }

    @Nested
    @DisplayName("Grouped tests for special characters")
    class SpecialCharacterTests {

        @Test
        @DisplayName("Should handle special characters in reverse")
        void testReverseSpecialChars() {
            assertEquals("!@#", processor.reverse("#@!"));
        }

        @Test
        @DisplayName("Should handle special characters in vowel count")
        void testVowelsWithSpecialChars() {
            assertEquals(1, processor.countVowels("h@ll#o"));
        }
    }

    @RepeatedTest(3)
    @DisplayName("Repeated test example")
    void testRepeated(RepetitionInfo repetitionInfo) {
        System.out.println("Execution " + repetitionInfo.getCurrentRepetition());
        assertNotNull(processor);
    }

    @Test
    @Disabled("Not implemented yet")
    void testFutureFeature() {
        fail("To be implemented");
    }

    @AfterEach
    void tearDown() {
        processor = null;
        System.out.println("@AfterEach - runs after each test");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("@AfterAll - runs once after all tests");
    }
}
