package io.github.divyesh.user.controller;

import io.github.divyesh.user.dto.JwtResponse;
import io.github.divyesh.user.dto.LoginRequest;
import io.github.divyesh.user.dto.UserRequest;
import io.github.divyesh.user.dto.UserResponse;
import io.github.divyesh.user.model.User;
import io.github.divyesh.user.service.UserService;
import io.github.divyesh.user.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing user-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting users.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "User management APIs")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /**
     * Constructs a {@code UserController} with the given dependencies.
     * @param userService The service responsible for user business logic.
     * @param authenticationManager The manager for handling authentication.
     * @param jwtUtil The utility for handling JWTs.
     */
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Registers a new user.
     * @param userRequest The user request object containing user details.
     * @return A {@code ResponseEntity} with the created user's response and HTTP status.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user", description = "Registers a new user in the system.")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) {
        User user = User.builder()
                .username(userRequest.username())
                .email(userRequest.email())
                .password(userRequest.password())
                .build();
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(mapToUserResponse(savedUser), HttpStatus.CREATED);
    }

    /**
     * Authenticates a user and returns a JWT.
     * @param loginRequest The login request containing username and password.
     * @return A {@code ResponseEntity} with the JWT response.
     */
    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user and returns a JWT.")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    /**
     * Retrieves all users.
     * @return A list of all user responses.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all users", description = "Retrieves a list of all registered users.")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(this::mapToUserResponse)
                .toList();
    }

    /**
     * Retrieves a user by ID.
     * @param id The ID of the user to retrieve.
     * @return A {@code ResponseEntity} with the user response and HTTP status.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique ID.")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
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
    public ResponseEntity<UserResponse> updateUser(@PathVariable long id, @RequestBody UserRequest userRequest) {
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
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
