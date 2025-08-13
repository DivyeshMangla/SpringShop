package io.github.divyesh.user.controller;

import io.github.divyesh.user.dto.UserRequest;
import io.github.divyesh.user.dto.UserResponse;
import io.github.divyesh.user.model.User;
import io.github.divyesh.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing user-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting users.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User management APIs")
public class UserController {

    private final UserService userService;

    /**
     * Constructs a {@code UserController} with the given {@code UserService}.
     * @param userService The service responsible for user business logic.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user.
     * @param userRequest The user request object containing user details.
     * @return A {@code ResponseEntity} with the created user's response and HTTP status.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user", description = "Registers a new user in the system.")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.username())
                .email(userRequest.email())
                .password(userRequest.password())
                .build();
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(mapToUserResponse(savedUser), HttpStatus.CREATED);
    }

    /**
     * Retrieves all users.
     * @return A list of all user responses.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all users", description = "Retrieves a list of all registered users.")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a user by ID.
     * @param id The ID of the user to retrieve.
     * @return A {@code ResponseEntity} with the user response and HTTP status.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique ID.")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> new ResponseEntity<>(mapToUserResponse(user), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Updates an existing user.
     * @param id The ID of the user to update.
     * @param userRequest The user request object with updated details.
     * @return A {@code ResponseEntity} with the updated user response and HTTP status.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update user by ID", description = "Updates an existing user's details by their unique ID.")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        User userDetails = User.builder()
                .username(userRequest.username())
                .email(userRequest.email())
                .password(userRequest.password())
                .build();
        return userService.updateUser(id, userDetails)
                .map(user -> new ResponseEntity<>(mapToUserResponse(user), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a user by ID.
     * @param id The ID of the user to delete.
     * @return A {@code ResponseEntity} with no content and HTTP status.
     */
        @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user by ID", description = "Deletes a user from the system by their unique ID.")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Maps a {@link User} entity to a {@link UserResponse} DTO.
     * @param user The {@link User} entity to map.
     * @return The mapped {@link UserResponse} DTO.
     */
    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
