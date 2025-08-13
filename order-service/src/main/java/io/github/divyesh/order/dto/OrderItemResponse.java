package io.github.divyesh.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO for representing an order item response.
 *
 * @param id The unique identifier of the order item.
 * @param productId The ID of the product associated with this order item.
 * @param quantity The quantity of the product.
 * @param price The price of the product at the time of order.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderItemResponse(
    @Schema(description = "Unique identifier of the order item", example = "1")
    Long id,

    @Schema(description = "ID of the product associated with this order item", example = "60d0fe4f5e36a0001c03b8a0")
    String productId,

    @Schema(description = "Quantity of the product", example = "2")
    Integer quantity,

    @Schema(description = "Price of the product at the time of order", example = "75.50")
    Double price) {

    /**
     * Entry point for creating a builder.
     * @return A new {@link OrderItemResponseBuilder}.
     */
    public static OrderItemResponseBuilder builder() {
        return new OrderItemResponseBuilder();
    }

    /**
     * Builder for {@link OrderItemResponse}.
     */
    public static final class OrderItemResponseBuilder {
        private Long id;
        private String productId;
        private Integer quantity;
        private Double price;

        /**
         * Private constructor to enforce the use of {@link #builder()()}.
         */
        private OrderItemResponseBuilder() {}

        /**
         * Sets the ID for the order item response.
         * @param id The order item ID.
         * @return The builder instance.
         */
        public OrderItemResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the product ID for the order item response.
         * @param productId The product ID.
         * @return The builder instance.
         */
        public OrderItemResponseBuilder productId(String productId) {
            this.productId = productId;
            return this;
        }

        /**
         * Sets the quantity for the order item response.
         * @param quantity The quantity.
         * @return The builder instance.
         */
        public OrderItemResponseBuilder quantity(Integer quantity) {
            this.quantity = quantity;
            return this;
        }

        /**
         * Sets the price for the order item response.
         * @param price The price.
         * @return The builder instance.
         */
        public OrderItemResponseBuilder price(Double price) {
            this.price = price;
            return this;
        }

        /**
         * Builds an {@link OrderItemResponse} instance.
         * @return A new {@link OrderItemResponse}.
         */
        public OrderItemResponse build() {
            return new OrderItemResponse(id, productId, quantity, price);
        }
    }
}