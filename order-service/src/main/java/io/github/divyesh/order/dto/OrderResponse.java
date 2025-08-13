package io.github.divyesh.order.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrderResponse(
    @Schema(description = "Unique identifier of the order", example = "1")
    Long id,

    @Schema(description = "ID of the user who placed the order", example = "1")
    Long userId,

    @Schema(description = "Date and time when the order was placed", example = "2023-10-26T10:00:00")
    LocalDateTime orderDate,

    @Schema(description = "Total amount of the order", example = "150.75")
    Double totalAmount,

    @Schema(description = "Current status of the order", example = "PENDING")
    String status,

    @Schema(description = "List of items in the order")
    List<OrderItemResponse> orderItemResponses) {

    /**
     * Entry point for creating a builder.
     * @return A new {@link OrderResponseBuilder}.
     */
    public static OrderResponseBuilder builder() {
        return new OrderResponseBuilder();
    }

    /**
     * Builder for {@link OrderResponse}.
     */
    public static final class OrderResponseBuilder {
        private Long id;
        private Long userId;
        private LocalDateTime orderDate;
        private Double totalAmount;
        private String status;
        private List<OrderItemResponse> orderItemResponses;

        /**
         * Private constructor to enforce the use of {@link #builder()}.
         */
        private OrderResponseBuilder() {}

        /**
         * Sets the ID for the order response.
         * @param id The order ID.
         * @return The builder instance.
         */
        public OrderResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the user ID for the order response.
         * @param userId The user ID.
         * @return The builder instance.
         */
        public OrderResponseBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        /**
         * Sets the order date for the order response.
         * @param orderDate The order date.
         * @return The builder instance.
         */
        public OrderResponseBuilder orderDate(LocalDateTime orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        /**
         * Sets the total amount for the order response.
         * @param totalAmount The total amount.
         * @return The builder instance.
         */
        public OrderResponseBuilder totalAmount(Double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        /**
         * Sets the status for the order response.
         * @param status The order status.
         * @return The builder instance.
         */
        public OrderResponseBuilder status(String status) {
            this.status = status;
            return this;
        }

        /**
         * Sets the order item responses for the order response.
         * @param orderItemResponses The order item responses.
         * @return The builder instance.
         */
        public OrderResponseBuilder orderItemResponses(List<OrderItemResponse> orderItemResponses) {
            this.orderItemResponses = orderItemResponses;
            return this;
        }

        /**
         * Builds an {@link OrderResponse} instance.
         * @return A new {@link OrderResponse}.
         */
        public OrderResponse build() {
            return new OrderResponse(id, userId, orderDate, totalAmount, status, orderItemResponses);
        }
    }
}