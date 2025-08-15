package io.github.divyesh.user.repository;

import io.github.divyesh.user.model.Role;
import io.github.divyesh.user.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for {@link Role} entities.
 * Provides standard CRUD operations for Role objects.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Finds a role by its name.
     * @param name The name of the role to search for.
     * @return An Optional containing the role if found.
     */
    Optional<Role> findByName(RoleName name);
}
