package io.github.divyesh.user.repository;

import io.github.divyesh.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for {@link User} entities.
 * Provides standard CRUD operations for User objects.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Finds a user by their username.
     * @param username The username to search for.
     * @return An Optional containing the user if found.
     */
    Optional<User> findByUsername(String username);
}