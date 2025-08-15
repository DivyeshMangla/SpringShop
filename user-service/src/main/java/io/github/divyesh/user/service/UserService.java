package io.github.divyesh.user.service;

import io.github.divyesh.user.exception.UserNotFoundException;
import io.github.divyesh.user.model.User;
import io.github.divyesh.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing user-related business logic.
 */
@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a {@code UserService} with the given {@code UserRepository} and {@code PasswordEncoder}.
     * @param userRepository The repository for user data access.
     * @param passwordEncoder The encoder for hashing passwords.
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new user after encoding their password.
     * @param user The user object to be created.
     * @return The created user object.
     */
    public User createUser(User user) {
        log.info("Creating new user: {}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Retrieves all users.
     * @return A list of all users.
     */
    public List<User> getAllUsers() {
        log.info("Retrieving all users");
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by their ID.
     * @param id The ID of the user to retrieve.
     * @return An Optional containing the user if found, or empty if not found.
     */
    public Optional<User> getUserById(long id) {
        log.info("Retrieving user with ID: {}", id);
        return userRepository.findById(id);
    }

    /**
     * Updates an existing user. If the password is provided, it will be encoded.
     * @param id The ID of the user to update.
     * @param userDetails The user object with updated details.
     * @return An Optional containing the updated user if found, or empty if not found.
     */
    public Optional<User> updateUser(long id, User userDetails) {
        log.info("Updating user with ID: {}", id);
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setEmail(userDetails.getEmail());
                    if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
                    }
                    return userRepository.save(user);
                });
    }

    /**
     * Deletes a user by their ID.
     * @param id The ID of the user to delete.
     */
    public void deleteUser(long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        userRepository.deleteById(id);
    }

    /**
     * Authenticates a user with username and password.
     * @param username The username.
     * @param password The password.
     * @return An Optional containing the user if authentication is successful.
     */
    public Optional<User> authenticateUser(String username, String password) {
        log.info("Authenticating user: {}", username);
        return userRepository.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    /**
     * Finds a user by their username.
     * @param username The username to search for.
     * @return An Optional containing the user if found.
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
