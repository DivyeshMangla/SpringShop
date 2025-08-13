package io.github.divyesh.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO for requesting an item to be added to an order.
 *
 * @param productId The ID of the product to be ordered.
 * @param quantity The quantity of the product.
 * @param price The price of the product at the time of order.
 */
public record OrderItemRequest(
    @Schema(description = "ID of the product to be ordered", example = "60d0fe4f5e36a0001c03b8a0")
    @NotBlank(message = "Product ID is required")
    String productId,

    @Schema(description = "Quantity of the product", example = "2")
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    Integer quantity,

    @Schema(description = "Price of the product at the time of order", example = "75.50")
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    Double price) {

    /**
     * Entry point for creating a builder.
     * @return A new {@link OrderItemRequestBuilder}.
     */
    public static OrderItemRequestBuilder builder() {
        return new OrderItemRequestBuilder();
    }

    /**
     * Builder for {@link OrderItemRequest}.
     */
    public static final class OrderItemRequestBuilder {
        private String productId;
        private Integer quantity;
        private Double price;

        /**
         * Private constructor to enforce the use of {@link #builder()()}.
         */
        private OrderItemRequestBuilder() {}

        /**
         * Sets the product ID for the order item request.
         * @param productId The product ID.
         * @return The builder instance.
         */
        public OrderItemRequestBuilder productId(String productId) {
            this.productId = productId;
            return this;
        }

        /**
         * Sets the quantity for the order item request.
         * @param quantity The quantity.
         * @return The builder instance.
         */
        public OrderItemRequestBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        /**
         * Sets the price for the order item request.
         * @param price The price.
         * @return The builder instance.
         */
        public OrderItemRequestBuilder price(Double price) {
            this.price = price;
            return this;
        }

        /**
         * Builds an {@link OrderItemRequest} instance.
         * @return A new {@link OrderItemRequest}.
         */
        public OrderItemRequest build() {
            return new OrderItemRequest(productId, quantity, price);
        }
    }
}