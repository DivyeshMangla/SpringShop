package io.github.divyesh.order.service;

import io.github.divyesh.order.dto.OrderRequest;
import io.github.divyesh.order.model.Order;
import io.github.divyesh.order.model.OrderItem;
import io.github.divyesh.order.repository.OrderRepository;
import io.github.divyesh.order.exception.OrderNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing orders.
 * Provides business logic for creating, retrieving, and managing orders.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Constructs an OrderService with the given OrderRepository.
     * @param orderRepository The repository for order data.
     */
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Creates a new order based on the provided order request.
     * This method calculates the total amount and sets the order date and initial status.
     *
     * @param orderRequest The DTO containing details for the new order.
     * @return The created and saved Order entity.
     */
    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setUserId(orderRequest.userId());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING"); // Initial status

        List<OrderItem> orderItems = orderRequest.orderItemRequests().stream()
                .map(itemRequest -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductId(itemRequest.productId());
                    orderItem.setQuantity(itemRequest.quantity());
                    orderItem.setPrice(itemRequest.price());
                    orderItem.setOrder(order); // Set the parent order
                    return orderItem;
                }).collect(Collectors.toList());

        order.setOrderItems(orderItems);

        // Calculate total amount
        Double totalAmount = orderItems.stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    /**
     * Retrieves an order by its unique ID.
     *
     * @param id The ID of the order to retrieve.
     * @return An Optional containing the Order if found, or empty if not.
     */
    @Transactional(readOnly = true)
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    /**
     * Retrieves all orders from the database.
     *
     * @return A list of all orders.
     */
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Updates an existing order.
     *
     * @param id The ID of the order to update.
     * @param updatedOrder The Order entity with updated details.
     * @return An Optional containing the updated Order if found, or empty if not.
     */
    @Transactional
    public Optional<Order> updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id).map(existingOrder -> {
            existingOrder.setUserId(updatedOrder.getUserId());
            existingOrder.setOrderDate(updatedOrder.getOrderDate());
            existingOrder.setTotalAmount(updatedOrder.getTotalAmount());
            existingOrder.setStatus(updatedOrder.getStatus());
            // Note: Updating nested collections like orderItems requires more complex logic
            // For simplicity, this example assumes orderItems are managed separately or replaced entirely
            // A more robust solution would involve comparing and updating individual order items
            return orderRepository.save(existingOrder);
        });
    }

    /**
     * Deletes an order by its unique ID.
     *
     * @param id The ID of the order to delete.
     * @throws OrderNotFoundException if the order with the given ID does not exist.
     */
    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found with ID: " + id);
        }
        orderRepository.deleteById(id);
    }
}
