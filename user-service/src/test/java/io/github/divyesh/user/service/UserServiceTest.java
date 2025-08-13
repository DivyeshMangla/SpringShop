package io.github.divyesh.user.service;

import io.github.divyesh.user.exception.UserNotFoundException;
import io.github.divyesh.user.model.User;
import io.github.divyesh.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the UserService class.
 * These tests focus on the business logic of the UserService in isolation,
 * mocking the UserRepository dependency.
 */
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests that createUser successfully saves a new user.
     */
    @Test
    void createUser_shouldReturnSavedUser() {
        User user = User.builder().username("testuser").email("test@example.com").password("password").build();
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.createUser(user);

        assertNotNull(savedUser);
        assertEquals("testuser", savedUser.getUsername());
        verify(userRepository, times(1)).save(user);
    }

    /**
     * Tests that getAllUsers returns a list of all users.
     */
    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        User user1 = User.builder().id(1L).username("user1").build();
        User user2 = User.builder().id(2L).username("user2").build();
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<User> retrievedUsers = userService.getAllUsers();

        assertNotNull(retrievedUsers);
        assertEquals(2, retrievedUsers.size());
        verify(userRepository, times(1)).findAll();
    }

    /**
     * Tests that getUserById returns a user when found.
     */
    @Test
    void getUserById_shouldReturnUser_whenFound() {
        User user = User.builder().id(1L).username("testuser").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> retrievedUser = userService.getUserById(1L);

        assertTrue(retrievedUser.isPresent());
        assertEquals("testuser", retrievedUser.get().getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    /**
     * Tests that getUserById returns empty optional when user is not found.
     */
    @Test
    void getUserById_shouldReturnEmptyOptional_whenNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> retrievedUser = userService.getUserById(1L);

        assertFalse(retrievedUser.isPresent());
        verify(userRepository, times(1)).findById(1L);
    }

    /**
     * Tests that updateUser successfully updates an existing user.
     */
    @Test
    void updateUser_shouldReturnUpdatedUser_whenFound() {
        User existingUser = User.builder().id(1L).username("olduser").email("old@example.com").password("oldpass").build();
        User updatedDetails = User.builder().username("newuser").email("new@example.com").password("newpass").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<User> result = userService.updateUser(1L, updatedDetails);

        assertTrue(result.isPresent());
        assertEquals("newuser", result.get().getUsername());
        assertEquals("new@example.com", result.get().getEmail());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    /**
     * Tests that updateUser returns empty optional when user to update is not found.
     */
    @Test
    void updateUser_shouldReturnEmptyOptional_whenNotFound() {
        User updatedDetails = User.builder().username("newuser").build();
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.updateUser(1L, updatedDetails);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Tests that deleteUser successfully deletes an existing user.
     */
    @Test
    void deleteUser_shouldDeleteUser_whenFound() {
        User user = User.builder().id(1L).username("testuser").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user)); // Mock findById
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).findById(1L); // Verify findById is called
        verify(userRepository, times(1)).deleteById(1L); // Verify deleteById is called
    }

    /**
     * Tests that deleteUser throws UserNotFoundException when user to delete is not found.
     */
    @Test
    void deleteUser_shouldThrowUserNotFoundException_whenNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty()); // Mock findById to return empty

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));

        verify(userRepository, times(1)).findById(1L); // Verify findById is called
        verify(userRepository, never()).deleteById(anyLong()); // Verify deleteById is NOT called
    }
}
