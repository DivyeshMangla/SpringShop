package io.github.divyesh.order.repository;

import io.github.divyesh.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link Order} entities.
 * Provides standard CRUD operations for Order objects.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {}
