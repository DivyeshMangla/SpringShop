package io.github.divyesh.user.repository;

import io.github.divyesh.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for {@link User} entities.
 * Provides standard CRUD operations for User objects.
 */
public interface UserRepository extends JpaRepository<User, Long> {}