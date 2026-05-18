package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "John Doe", "john@example.com");
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1L)).thenReturn(testUser);

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateUser() {
        userService.createUser(testUser);

        verify(userRepository, times(1)).save(testUser);
        verify(emailService, times(1)).sendEmail(
            eq("john@example.com"),
            eq("Welcome"),
            anyString()
        );
    }

    @Test
    void testCreateUserWithArgumentCaptor() {
        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);

        userService.createUser(testUser);

        verify(emailService).sendEmail(
            emailCaptor.capture(),
            subjectCaptor.capture(),
            anyString()
        );

        assertEquals("john@example.com", emailCaptor.getValue());
        assertEquals("Welcome", subjectCaptor.getValue());
    }

    @Test
    void testCreateUserWithEmptyName() {
        User invalidUser = new User(2L, "", "test@example.com");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(invalidUser);
        });

        verify(userRepository, never()).save(any(User.class));
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void testDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(testUser);

        userService.deleteUser(1L);

        verify(userRepository).delete(1L);
        verify(emailService).sendEmail(
            eq("john@example.com"),
            eq("Account Deleted"),
            anyString()
        );
    }

    @Test
    void testDeleteNonExistentUser() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(99L);
        });

        verify(userRepository, never()).delete(anyLong());
    }

    @Test
    void testUserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.existsById(99L)).thenReturn(false);

        assertTrue(userService.userExists(1L));
        assertFalse(userService.userExists(99L));

        verify(userRepository, times(2)).existsById(anyLong());
    }

    @Test
    void testMultipleInteractions() {
        when(userRepository.findById(1L)).thenReturn(testUser);

        userService.getUserById(1L);
        userService.getUserById(1L);
        userService.getUserById(1L);

        verify(userRepository, times(3)).findById(1L);
        verify(userRepository, atLeast(2)).findById(1L);
        verify(userRepository, atMost(5)).findById(1L);
    }

    @Test
    void testWithDoAnswer() {
        doAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(100L);
            return null;
        }).when(userRepository).save(any(User.class));

        User newUser = new User(null, "Jane Doe", "jane@example.com");
        userService.createUser(newUser);

        assertEquals(100L, newUser.getId());
    }
}
