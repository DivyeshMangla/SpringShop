package io.github.divyesh.user.service;

import io.github.divyesh.user.model.User;
import io.github.divyesh.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

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
    public Optional<User> getUserById(Long id) {
        log.info("Retrieving user with ID: {}", id);
        return userRepository.findById(id);
    }

    /**
     * Updates an existing user.
     * @param id The ID of the user to update. *
     * @param userDetails The user object with updated details.
     * @return An Optional containing the updated user if found, or empty if not found.
     */
    public Optional<User> updateUser(Long id, User userDetails) {
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
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        userRepository.deleteById(id);
    }
}
