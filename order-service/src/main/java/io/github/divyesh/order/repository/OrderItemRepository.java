package io.github.divyesh.order.repository;

import io.github.divyesh.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for {@link OrderItem} entities.
 * Provides standard CRUD operations for OrderItem objects.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
