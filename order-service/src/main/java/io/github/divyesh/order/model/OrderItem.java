package io.github.divyesh.order.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/**
 * Represents an item within an order.
 * Each order item corresponds to a specific product with a quantity and price at the time of order.
 */
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId; // Assuming productId to link to product-service
    private Integer quantity;
    private Double price; // Price at the time of order

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    /**
     * Default constructor for JPA.
     */
    public OrderItem() {}

    /**
     * Constructs a new OrderItem with the specified details.
     *
     * @param id The unique identifier of the order item.
     * @param productId The ID of the product associated with this order item.
     * @param quantity The quantity of the product in this order item.
     * @param price The price of the product at the time of order.
     * @param order The order to which this item belongs.
     */
    public OrderItem(Long id, String productId, Integer quantity, Double price, Order order) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }

    /**
     * Returns the unique identifier of the order item.
     *
     * @return The order item ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the ID of the product associated with this order item.
     *
     * @return The product ID.
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Returns the quantity of the product in this order item.
     *
     * @return The quantity.
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Returns the price of the product at the time of order.
     *
     * @return The price.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Returns the order to which this item belongs.
     *
     * @return The associated order.
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Sets the unique identifier of the order item.
     *
     * @param id The order item ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the ID of the product associated with this order item.
     *
     * @param productId The product ID to set.
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Sets the quantity of the product in this order item.
     *
     * @param quantity The quantity to set.
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Sets the price of the product at the time of order.
     *
     * @param price The price to set.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Sets the order to which this item belongs.
     *
     * @param order The associated order to set.
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Creates a new builder for {@link OrderItem}.
     *
     * @return A new {@link OrderItemBuilder}.
     */
    public static OrderItemBuilder builder() {
        return new OrderItemBuilder();
    }

    /**
     * Builder for {@link OrderItem}.
     */
    public static class OrderItemBuilder {
        private Long id;
        private String productId;
        private Integer quantity;
        private Double price;
        private Order order;

        /**
         * Private constructor to enforce the use of {@link #builder()}.
         */
        private OrderItemBuilder() {}
        /**
         * Sets the ID for the order item.
         *
         * @param id The order item ID.
         * @return The builder instance.
         */
        public OrderItemBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the product ID for the order item.
         *
         * @param productId The product ID.
         * @return The builder instance.
         */
        public OrderItemBuilder productId(String productId) {
            this.productId = productId;
            return this;
        }

        /**
         * Sets the quantity for the order item.
         *
         * @param quantity The quantity.
         * @return The builder instance.
         */
        public OrderItemBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        /**
         * Sets the price for the order item.
         *
         * @param price The price.
         * @return The builder instance.
         */
        public OrderItemBuilder price(Double price) {
            this.price = price;
            return this;
        }

        /**
         * Sets the order for the order item.
         *
         * @param order The order.
         * @return The builder instance.
         */
        public OrderItemBuilder order(Order order) {
            this.order = order;
            return this;
        }

        /**
         * Builds an {@link OrderItem} instance.
         *
         * @return A new {@link OrderItem}.
         */
        public OrderItem build() {
            return new OrderItem(id, productId, quantity, price, order);
        }
    }
}
