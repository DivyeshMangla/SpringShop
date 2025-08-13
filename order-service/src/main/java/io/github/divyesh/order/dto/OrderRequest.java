package io.github.divyesh.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
 * DTO for creating or updating an order.
 *
 * @param userId The ID of the user placing the order.
 * @param orderItemRequests A list of items included in the order.
 */
public record OrderRequest(
    @Schema(description = "ID of the user placing the order", example = "1")
    @NotNull(message = "User ID is required")
    Long userId,

    @Schema(description = "List of items in the order")
    @NotNull(message = "Order items are required")
    @Size(min = 1, message = "Order must contain at least one item")
    List<OrderItemRequest> orderItemRequests) {

    /**
     * Entry point for creating a builder.
     * @return A new {@link OrderRequestBuilder}.
     */
    public static OrderRequestBuilder builder() {
        return new OrderRequestBuilder();
    }

    /**
     * Builder for {@link OrderRequest}.
     */
    public static final class OrderRequestBuilder {
        private Long userId;
        private List<OrderItemRequest> orderItemRequests;

        /**
         * Private constructor to enforce the use of {@link #builder()()}.
         */
        private OrderRequestBuilder() {}

        /**
         * Sets the user ID for the order request.
         * @param userId The user ID.
         * @return The builder instance.
         */
        public OrderRequestBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        /**
         * Sets the order item requests for the order request.
         * @param orderItemRequests The order item requests.
         * @return The builder instance.
         */
        public OrderRequestBuilder orderItemRequests(List<OrderItemRequest> orderItemRequests) {
            this.orderItemRequests = orderItemRequests;
            return this;
        }

        /**
         * Builds an {@link OrderRequest} instance.
         * @return A new {@link OrderRequest}.
         */
        public OrderRequest build() {
            return new OrderRequest(userId, orderItemRequests);
        }
    }
}