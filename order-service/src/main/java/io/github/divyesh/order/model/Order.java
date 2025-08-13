package io.github.divyesh.order.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an order placed by a user in the system.
 * An order contains a list of items, the total amount, and the order date.
 */
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // Assuming a userId to link to the user-service
    private LocalDateTime orderDate;
    private Double totalAmount;
    private String status; // e.g., PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    @JsonManagedReference
    private List<OrderItem> orderItems;

    /**
     * Default constructor for JPA.
     */
    public Order() {}

    /**
     * Constructs a new Order with the specified details.
     *
     * @param id The unique identifier of the order.
     * @param userId The ID of the user who placed the order.
     * @param orderDate The date and time when the order was placed.
     * @param totalAmount The total amount of the order.
     * @param status The current status of the order.
     * @param orderItems The list of items included in the order.
     */
    public Order(Long id, Long userId, LocalDateTime orderDate, Double totalAmount, String status, List<OrderItem> orderItems) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderItems = orderItems;
    }

    /**
     * Returns the unique identifier of the order.
     *
     * @return The order ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the ID of the user who placed the order.
     *
     * @return The user ID.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Returns the date and time when the order was placed.
     *
     * @return The order date.
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * Returns the total amount of the order.
     *
     * @return The total amount.
     */
    public Double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Returns the current status of the order.
     *
     * @return The order status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Returns the list of items included in the order.
     *
     * @return The list of order items.
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * Sets the unique identifier of the order.
     *
     * @param id The order ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the ID of the user who placed the order.
     *
     * @param userId The user ID to set.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Sets the date and time when the order was placed.
     *
     * @param orderDate The order date to set.
     */
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Sets the total amount of the order.
     *
     * @param totalAmount The total amount to set.
     */
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Sets the current status of the order.
     *
     * @param status The order status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Sets the list of items included in the order.
     *
     * @param orderItems The list of order items to set.
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     * Creates a new builder for {@link Order}.
     *
     * @return A new {@link OrderBuilder}.
     */
    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    /**
     * Builder for {@link Order}.
     */
    public static class OrderBuilder {
        private Long id;
        private Long userId;
        private LocalDateTime orderDate;
        private Double totalAmount;
        private String status;
        private List<OrderItem> orderItems;

        /**
         * Private constructor to enforce the use of {@link #builder()}.
         */
        private OrderBuilder() {}

        /**
         * Sets the ID for the order.
         *
         * @param id The order ID.
         * @return The builder instance.
         */
        public OrderBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the user ID for the order.
         *
         * @param userId The user ID.
         * @return The builder instance.
         */
        public OrderBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        /**
         * Sets the order date for the order.
         *
         * @param orderDate The order date.
         * @return The builder instance.
         */
        public OrderBuilder orderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        /**
         * Sets the total amount for the order.
         *
         * @param totalAmount The total amount.
         * @return The builder instance.
         */
        public OrderBuilder totalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        /**
         * Sets the status for the order.
         *
         * @param status The order status.
         * @return The builder instance.
         */
        public OrderBuilder status(String status) {
            this.status = status;
            return this;
        }

        /**
         * Sets the order items for the order.
         *
         * @param orderItems The order items.
         * @return The builder instance.
         */
        public OrderBuilder orderItems(List<OrderItem> orderItems) {
            this.orderItems = orderItems;
            return this;
        }

        /**
         * Builds an {@link Order} instance.
         *
         * @return A new {@link Order}.
         */
        public Order build() {
            return new Order(id, userId, orderDate, totalAmount, status, orderItems);
        }
    }
}
