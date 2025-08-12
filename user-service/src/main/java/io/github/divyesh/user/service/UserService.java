package io.github.divyesh.user.service;

import io.github.divyesh.user.model.User;
import io.github.divyesh.user.repository.UserRepository;
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

    /**
     * Constructs a {@code UserService} with the given {@code UserRepository}.
     * @param userRepository The repository for user data access.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user.
     * @param user The user object to be created.
     * @return The created user object.
     */
    public User createUser(User user) {
        log.info("Creating new user: {}", user.getUsername());
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
     * Updates an existing user.
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
                    user.setPassword(userDetails.getPassword());
                    return userRepository.save(user);
                });
    }

    /**
     * Deletes a user by their ID.
     * @param id The ID of the user to delete.
     */
    public void deleteUser(long id) {
        log.info("Deleting user with ID: {}", id);
        userRepository.deleteById(id);
    }
}