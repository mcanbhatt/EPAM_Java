package com.example;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Ignore;
import static org.junit.Assert.*;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeClass
    public static void setUpClass() {
        System.out.println("@BeforeClass - runs once before all tests");
    }

    @Before
    public void setUp() {
        calculator = new Calculator();
        System.out.println("@Before - runs before each test");
    }

    @Test
    public void testAdd() {
        assertEquals(5, calculator.add(2, 3));
        assertEquals(0, calculator.add(-1, 1));
    }

    @Test
    public void testSubtract() {
        assertEquals(1, calculator.subtract(3, 2));
        assertEquals(-2, calculator.subtract(-1, 1));
    }

    @Test
    public void testMultiply() {
        assertEquals(6, calculator.multiply(2, 3));
        assertEquals(0, calculator.multiply(0, 5));
    }

    @Test
    public void testDivide() {
        assertEquals(2.0, calculator.divide(6, 3), 0.001);
        assertEquals(2.5, calculator.divide(5, 2), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivideByZero() {
        calculator.divide(10, 0);
    }

    @Test(timeout = 1000)
    public void testPerformance() {
        for (int i = 0; i < 1000; i++) {
            calculator.add(i, i);
        }
    }

    @Ignore("Not ready yet")
    @Test
    public void testComplexOperation() {
        fail("This test is not implemented");
    }

    @After
    public void tearDown() {
        calculator = null;
        System.out.println("@After - runs after each test");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("@AfterClass - runs once after all tests");
    }
}
