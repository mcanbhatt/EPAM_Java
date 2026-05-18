package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

/**
 * Comprehensive Mockito Testing Examples
 * Demonstrates ALL major Mockito features and annotations
 */
@ExtendWith(MockitoExtension.class)
class AdvancedMockitoTest {

    @Mock
    private PaymentProcessor paymentProcessor;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private OrderService orderService;

    // @Captor - Captures arguments passed to mocks
    @Captor
    private ArgumentCaptor<Long> userIdCaptor;

    @Captor
    private ArgumentCaptor<List<String>> itemsCaptor;

    @Captor
    private ArgumentCaptor<Ordr> orderCaptor;

    private Ordr testOrder;

    @BeforeEach
    void setUp() {
        testOrder = new Ordr(1L, Arrays.asList("item1", "item2"));
    }

    // ===========================================
    // 1. BASIC MOCKING - @Mock and @InjectMocks
    // ===========================================

    @Test
    void testBasicMocking() {
        // @Mock creates a mock object
        // @InjectMocks injects mocks into OrderService

        assertNotNull(orderService);
        assertNotNull(paymentProcessor);
        assertNotNull(inventoryService);
    }

    // ===========================================
    // 2. STUBBING - when().thenReturn()
    // ===========================================

    @Test
    void testWhenThenReturn() {
        // Arrange - stub the mock
        when(inventoryService.checkAvailability(anyList())).thenReturn(true);
        when(paymentProcessor.processPayment(anyLong(), anyDouble())).thenReturn(true);

        // Act
        boolean result = orderService.processOrder(testOrder);

        // Assert
        assertTrue(result);
    }

    // ===========================================
    // 3. VERIFICATION - verify()
    // ===========================================

    @Test
    void testVerify() {
        when(inventoryService.checkAvailability(anyList())).thenReturn(true);
        when(paymentProcessor.processPayment(anyLong(), anyDouble())).thenReturn(true);

        orderService.processOrder(testOrder);

        // Verify method was called
        verify(inventoryService).checkAvailability(anyList());
        verify(paymentProcessor).processPayment(1L, 20.0);
        verify(notificationService).notifyOrderSuccess(eq(1L), any(Ordr.class));
    }

    @Test
    void testVerifyNever() {
        // Arrange - inventory not available
        when(inventoryService.checkAvailability(anyList())).thenReturn(false);

        // Act
        orderService.processOrder(testOrder);

        // Assert - payment should never be processed
        verify(paymentProcessor, never()).processPayment(anyLong(), anyDouble());
        verify(notificationService).notifyOutOfStock(1L);
    }

    @Test
    void testVerifyTimes() {
        when(inventoryService.checkAvailability(anyList())).thenReturn(true);
        when(paymentProcessor.processPayment(anyLong(), anyDouble())).thenReturn(true);

        orderService.processOrder(testOrder);
        orderService.processOrder(testOrder);

        // Verify called exactly 2 times
        verify(inventoryService, times(2)).checkAvailability(anyList());

        // Verify at least once
        verify(paymentProcessor, atLeast(1)).processPayment(anyLong(), anyDouble());

        // Verify at most 3 times
        verify(paymentProcessor, atMost(3)).processPayment(anyLong(), anyDouble());
    }

    // ===========================================
    // 4. ARGUMENT CAPTORS - @Captor
    // ===========================================

    @Test
    void testArgumentCaptor() {
        when(inventoryService.checkAvailability(anyList())).thenReturn(true);
        when(paymentProcessor.processPayment(anyLong(), anyDouble())).thenReturn(true);

        orderService.processOrder(testOrder);

        // Capture the arguments
        verify(notificationService).notifyOrderSuccess(userIdCaptor.capture(), orderCaptor.capture());

        // Assert captured values
        assertEquals(1L, userIdCaptor.getValue());
        assertEquals("COMPLETED", orderCaptor.getValue().getStatus());
        assertEquals(2, orderCaptor.getValue().getItems().size());
    }

    @Test
    void testMultipleArgumentCapture() {
        when(inventoryService.checkAvailability(anyList())).thenReturn(true);
        when(paymentProcessor.processPayment(anyLong(), anyDouble())).thenReturn(true);

        Ordr order1 = new Ordr(1L, Arrays.asList("item1"));
        Ordr order2 = new Ordr(2L, Arrays.asList("item2"));

        orderService.processOrder(order1);
        orderService.processOrder(order2);

        // Capture all invocations
        verify(notificationService, times(2)).notifyOrderSuccess(userIdCaptor.capture(), any());

        List<Long> capturedUserIds = userIdCaptor.getAllValues();
        assertEquals(Arrays.asList(1L, 2L), capturedUserIds);
    }

    // ===========================================
    // 5. EXCEPTION TESTING - assertThrows
    // ===========================================

    @Test
    void testExceptionWithAssertThrows() {
        // Test that exception is thrown
        assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(1L, null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(1L, Arrays.asList());
        });
    }

    @Test
    void testExceptionWithMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(1L, null);
        });

        assertEquals("Order must contain items", exception.getMessage());
    }

    // ===========================================
    // 6. STUBBING EXCEPTIONS - thenThrow()
    // ===========================================

    @Test
    void testWhenThenThrow() {
        // Stub to throw exception
        when(inventoryService.checkAvailability(anyList()))
            .thenThrow(new RuntimeException("Inventory system down"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            orderService.processOrder(testOrder);
        });
    }

    // ===========================================
    // 7. VOID METHOD STUBBING - doThrow, doNothing
    // ===========================================

    @Test
    void testDoThrow() {
        // For void methods, use doThrow
        doThrow(new RuntimeException("Notification failed"))
            .when(notificationService).notifyOrderSuccess(anyLong(), any(Ordr.class));

        when(inventoryService.checkAvailability(anyList())).thenReturn(true);
        when(paymentProcessor.processPayment(anyLong(), anyDouble())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            orderService.processOrder(testOrder);
        });
    }

    @Test
    void testDoNothing() {
        // Explicitly do nothing (default behavior)
        doNothing().when(notificationService).notifyOrderSuccess(anyLong(), any(Ordr.class));

        when(inventoryService.checkAvailability(anyList())).thenReturn(true);
        when(paymentProcessor.processPayment(anyLong(), anyDouble())).thenReturn(true);

        assertTrue(orderService.processOrder(testOrder));
        verify(notificationService).notifyOrderSuccess(anyLong(), any(Ordr.class));
    }

    // ===========================================
    // 8. ANSWER - Custom Behavior
    // ===========================================

    @Test
    void testAnswer() {
        // Custom behavior using Answer
        when(paymentProcessor.processPayment(anyLong(), anyDouble()))
            .thenAnswer((Answer<Boolean>) invocation -> {
                Long userId = invocation.getArgument(0);
                Double amount = invocation.getArgument(1);
                // Custom logic
                return userId != null && amount > 0;
            });

        when(inventoryService.checkAvailability(anyList())).thenReturn(true);

        assertTrue(orderService.processOrder(testOrder));
    }

    @Test
    void testDoAnswer() {
        // doAnswer for void methods
        doAnswer(invocation -> {
            Long userId = invocation.getArgument(0);
            System.out.println("Notifying user: " + userId);
            return null; // void method
        }).when(notificationService).notifyOrderSuccess(anyLong(), any(Ordr.class));

        when(inventoryService.checkAvailability(anyList())).thenReturn(true);
        when(paymentProcessor.processPayment(anyLong(), anyDouble())).thenReturn(true);

        orderService.processOrder(testOrder);

        verify(notificationService).notifyOrderSuccess(1L, testOrder);
    }

    // ===========================================
    // 9. INORDER VERIFICATION - Verify Order
    // ===========================================

    @Test
    void testInOrder() {
        when(inventoryService.checkAvailability(anyList())).thenReturn(true);
        when(paymentProcessor.processPayment(anyLong(), anyDouble())).thenReturn(true);

        orderService.processOrder(testOrder);

        // Verify methods called in specific order
        InOrder inOrder = inOrder(inventoryService, paymentProcessor, notificationService);

        inOrder.verify(inventoryService).checkAvailability(anyList());
        inOrder.verify(inventoryService).reserveItems(anyList());
        inOrder.verify(paymentProcessor).processPayment(anyLong(), anyDouble());
        inOrder.verify(inventoryService).deductItems(anyList());
        inOrder.verify(notificationService).notifyOrderSuccess(anyLong(), any(Ordr.class));
    }

    // ===========================================
    // 10. CONSECUTIVE CALLS - Multiple thenReturn
    // ===========================================

    @Test
    void testConsecutiveCalls() {
        // Different return values for consecutive calls
        when(inventoryService.checkAvailability(anyList()))
            .thenReturn(true)   // First call
            .thenReturn(false)  // Second call
            .thenReturn(true);  // Third call

        assertTrue(inventoryService.checkAvailability(Arrays.asList("item1")));
        assertFalse(inventoryService.checkAvailability(Arrays.asList("item2")));
        assertTrue(inventoryService.checkAvailability(Arrays.asList("item3")));
    }

    // ===========================================
    // 11. BDD STYLE - given/when/then
    // ===========================================

    @Test
    void testBDDStyle() {
        // Given (Arrange)
        given(inventoryService.checkAvailability(anyList())).willReturn(true);
        given(paymentProcessor.processPayment(anyLong(), anyDouble())).willReturn(true);

        // When (Act)
        boolean result = orderService.processOrder(testOrder);

        // Then (Assert)
        then(inventoryService).should().checkAvailability(anyList());
        then(paymentProcessor).should().processPayment(1L, 20.0);
        then(notificationService).should().notifyOrderSuccess(eq(1L), any(Ordr.class));
        assertTrue(result);
    }

    // ===========================================
    // 12. ARGUMENT MATCHERS - any, eq, contains
    // ===========================================

    @Test
    void testArgumentMatchers() {
        when(inventoryService.checkAvailability(anyList())).thenReturn(true);
        when(paymentProcessor.processPayment(anyLong(), anyDouble())).thenReturn(true);

        orderService.processOrder(testOrder);

        // Various matchers
        verify(inventoryService).checkAvailability(anyList());
        verify(paymentProcessor).processPayment(eq(1L), eq(20.0));
        verify(notificationService).notifyOrderSuccess(anyLong(), any(Ordr.class));
    }

    // ===========================================
    // 13. VERIFY NO MORE INTERACTIONS
    // ===========================================

    @Test
    void testVerifyNoMoreInteractions() {
        when(inventoryService.checkAvailability(anyList())).thenReturn(false);

        orderService.processOrder(testOrder);

        verify(inventoryService).checkAvailability(anyList());
        verify(notificationService).notifyOutOfStock(1L);

        // Verify no other methods were called
        verifyNoMoreInteractions(inventoryService);
        verifyNoMoreInteractions(notificationService);
        verifyNoInteractions(paymentProcessor); // Nothing called at all
    }

    // ===========================================
    // 14. RESET MOCKS
    // ===========================================

    @Test
    void testReset() {
        when(inventoryService.checkAvailability(anyList())).thenReturn(true);

        inventoryService.checkAvailability(Arrays.asList("item1"));
        verify(inventoryService).checkAvailability(anyList());

        // Reset mock - clears all interactions and stubs
        reset(inventoryService);

        // Previous stub is gone
        assertNull(inventoryService.checkAvailability(Arrays.asList("item2")));

        // Re-stub after reset
        when(inventoryService.checkAvailability(anyList())).thenReturn(false);
        assertFalse(inventoryService.checkAvailability(Arrays.asList("item3")));
    }

    // ===========================================
    // 15. CLEAR INVOCATIONS
    // ===========================================

    @Test
    void testClearInvocations() {
        when(inventoryService.checkAvailability(anyList())).thenReturn(true);

        inventoryService.checkAvailability(Arrays.asList("item1"));
        verify(inventoryService, times(1)).checkAvailability(anyList());

        // Clear invocations but keep stubs
        clearInvocations(inventoryService);

        // Stub still works
        assertTrue(inventoryService.checkAvailability(Arrays.asList("item2")));

        // But only 1 invocation counted (after clear)
        verify(inventoryService, times(1)).checkAvailability(anyList());
    }

    // ===========================================
    // 16. LENIENT STUBBING
    // ===========================================

    @Test
    void testLenient() {
        // Lenient stubbing - no UnnecessaryStubbingException even if not used
        lenient().when(inventoryService.checkAvailability(anyList())).thenReturn(true);
        lenient().when(paymentProcessor.processPayment(anyLong(), anyDouble())).thenReturn(true);

        // Not using the stubs, but no exception thrown
        assertTrue(true);
    }

    // ===========================================
    // 17. VERIFY WITH TIMEOUT
    // ===========================================

    @Test
    void testVerifyTimeout() {
        when(inventoryService.checkAvailability(anyList())).thenReturn(true);
        when(paymentProcessor.processPayment(anyLong(), anyDouble())).thenReturn(true);

        orderService.processOrder(testOrder);

        // Verify within timeout (useful for async operations)
        verify(notificationService, timeout(1000)).notifyOrderSuccess(anyLong(), any(Ordr.class));
    }

    // ===========================================
    // 18. ARGUMENT MATCHER - Custom Matcher
    // ===========================================

    @Test
    void testCustomArgumentMatcher() {
        when(inventoryService.checkAvailability(argThat(items ->
            items != null && items.size() > 0
        ))).thenReturn(true);

        assertTrue(inventoryService.checkAvailability(Arrays.asList("item1")));
        assertNull(inventoryService.checkAvailability(Arrays.asList())); // Doesn't match
    }

    // ===========================================
    // 19. MULTIPLE MOCKING PATTERNS
    // ===========================================

    @Test
    void testComplexScenario() {
        // Scenario: Payment fails, items should be released

        // Given
        given(inventoryService.checkAvailability(anyList())).willReturn(true);
        given(paymentProcessor.processPayment(anyLong(), anyDouble())).willReturn(false);

        // When
        boolean result = orderService.processOrder(testOrder);

        // Then
        assertFalse(result);

        // Verify order of operations
        InOrder inOrder = inOrder(inventoryService, paymentProcessor, notificationService);
        inOrder.verify(inventoryService).checkAvailability(anyList());
        inOrder.verify(inventoryService).reserveItems(anyList());
        inOrder.verify(paymentProcessor).processPayment(1L, 20.0);
        inOrder.verify(inventoryService).releaseItems(anyList());
        inOrder.verify(notificationService).notifyPaymentFailed(1L);

        // Verify items not deducted
        verify(inventoryService, never()).deductItems(anyList());
    }
}
