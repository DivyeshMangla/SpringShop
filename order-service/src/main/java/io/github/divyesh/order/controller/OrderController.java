package io.github.divyesh.order.controller;

import io.github.divyesh.order.dto.OrderItemResponse;
import io.github.divyesh.order.dto.OrderRequest;
import io.github.divyesh.order.dto.OrderResponse;
import io.github.divyesh.order.exception.OrderNotFoundException;
import io.github.divyesh.order.model.Order;
import io.github.divyesh.order.model.OrderItem;
import io.github.divyesh.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing orders.
 * Provides endpoints for CRUD operations on orders.
 */
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order", description = "Order management APIs")
public class OrderController {

    private final OrderService orderService;

    /**
     * Constructs an OrderController with the given OrderService.
     * @param orderService The service for order business logic.
     */
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Creates a new order.
     * @param orderRequest The order data to create.
     * @return The created order.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new order", description = "Adds a new order to the database")
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        Order createdOrder = orderService.createOrder(orderRequest);
        return mapToOrderResponse(createdOrder);
    }

    /**
     * Retrieves a list of all orders.
     * @return A list of all orders.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all orders", description = "Retrieves a list of all orders")
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders().stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    /**
     * Retrieves an order by its unique ID.
     * @param id The ID of the order to retrieve.
     * @return The order with the given ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieves an order by its unique ID")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(order -> new ResponseEntity<>(mapToOrderResponse(order), HttpStatus.OK))
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
    }

    /**
     * Updates an existing order identified by its ID.
     * @param id The ID of the order to update.
     * @param orderRequest The updated order data.
     * @return The updated order.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update order by ID", description = "Updates an existing order identified by its ID")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderRequest orderRequest) {
        Order orderToUpdate = Order.builder()
                .userId(orderRequest.userId())
                .orderItems(orderRequest.orderItemRequests().stream()
                        .map(itemReq -> OrderItem.builder()
                                .productId(itemReq.productId())
                                .quantity(itemReq.quantity())
                                .price(itemReq.price())
                                .build())
                        .toList())
                .build();

        return orderService.updateOrder(id, orderToUpdate)
                .map(order -> new ResponseEntity<>(mapToOrderResponse(order), HttpStatus.OK))
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
    }

    /**
     * Deletes an order by its unique ID.
     * @param id The ID of the order to delete.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete order by ID", description = "Deletes an order by its unique ID")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    /**
     * Maps an Order entity to an OrderResponse DTO.
     * @param order The Order entity to map.
     * @return The mapped OrderResponse DTO.
     */
    private OrderResponse mapToOrderResponse(Order order) {
        List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
                .map(this::mapToOrderItemResponse)
                .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .orderItemResponses(itemResponses)
                .build();
    }

    /**
     * Maps an OrderItem entity to an OrderItemResponse DTO.
     * @param orderItem The OrderItem entity to map.
     * @return The mapped OrderItemResponse DTO.
     */
    private OrderItemResponse mapToOrderItemResponse(OrderItem orderItem) {
        return OrderItemResponse.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProductId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }
}
