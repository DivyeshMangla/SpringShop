package io.github.divyesh.user.repository;

import io.github.divyesh.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
