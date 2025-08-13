package io.github.divyesh.order.service;

import io.github.divyesh.order.dto.OrderItemRequest;
import io.github.divyesh.order.dto.OrderRequest;
import io.github.divyesh.order.exception.OrderNotFoundException;
import io.github.divyesh.order.model.Order;
import io.github.divyesh.order.model.OrderItem;
import io.github.divyesh.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the OrderService class.
 * These tests focus on the business logic of the OrderService in isolation,
 * mocking the OrderRepository dependency.
 */
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests that createOrder successfully creates and saves a new order.
     */
    @Test
    void createOrder_shouldReturnSavedOrder() {
        OrderItemRequest itemRequest = OrderItemRequest.builder()
                .productId("prod1")
                .quantity(2)
                .price(10.0)
                .build();
        OrderRequest orderRequest = OrderRequest.builder()
                .userId(1L)
                .orderItemRequests(List.of(itemRequest))
                .build();

        Order order = new Order();
        order.setId(1L);
        order.setUserId(1L);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId("prod1");
        orderItem.setQuantity(2);
        orderItem.setPrice(10.0);
        orderItem.setOrder(order);
        order.setOrderItems(List.of(orderItem));
        order.setTotalAmount(20.0);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.createOrder(orderRequest);

        assertNotNull(createdOrder);
        assertEquals(1L, createdOrder.getUserId());
        assertEquals("PENDING", createdOrder.getStatus());
        assertEquals(1, createdOrder.getOrderItems().size());
        assertEquals(20.0, createdOrder.getTotalAmount());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    /**
     * Tests that getOrderById returns an order when found.
     */
    @Test
    void getOrderById_shouldReturnOrder_whenFound() {
        Order order = Order.builder().id(1L).userId(1L).build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> retrievedOrder = orderService.getOrderById(1L);

        assertTrue(retrievedOrder.isPresent());
        assertEquals(1L, retrievedOrder.get().getId());
        verify(orderRepository, times(1)).findById(1L);
    }

    /**
     * Tests that getOrderById returns empty optional when order is not found.
     */
    @Test
    void getOrderById_shouldReturnEmptyOptional_whenNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Order> retrievedOrder = orderService.getOrderById(1L);

        assertFalse(retrievedOrder.isPresent());
        verify(orderRepository, times(1)).findById(1L);
    }

    /**
     * Tests that getAllOrders returns a list of all orders.
     */
    @Test
    void getAllOrders_shouldReturnListOfOrders() {
        Order order1 = Order.builder().id(1L).userId(1L).build();
        Order order2 = Order.builder().id(2L).userId(2L).build();
        List<Order> orders = Arrays.asList(order1, order2);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> retrievedOrders = orderService.getAllOrders();

        assertNotNull(retrievedOrders);
        assertEquals(2, retrievedOrders.size());
        verify(orderRepository, times(1)).findAll();
    }

    /**
     * Tests that updateOrder successfully updates an existing order.
     */
    @Test
    void updateOrder_shouldReturnUpdatedOrder_whenFound() {
        Order existingOrder = Order.builder().id(1L).userId(1L).status("PENDING").build();
        Order updatedDetails = Order.builder().userId(1L).status("CONFIRMED").build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Order> result = orderService.updateOrder(1L, updatedDetails);

        assertTrue(result.isPresent());
        assertEquals("CONFIRMED", result.get().getStatus());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    /**
     * Tests that updateOrder returns empty optional when order to update is not found.
     */
    @Test
    void updateOrder_shouldReturnEmptyOptional_whenNotFound() {
        Order updatedDetails = Order.builder().userId(1L).status("CONFIRMED").build();
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.updateOrder(1L, updatedDetails);

        assertFalse(result.isPresent());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, never()).save(any(Order.class));
    }

    /**
     * Tests that deleteOrder successfully deletes an existing order.
     */
    @Test
    void deleteOrder_shouldDeleteOrder_whenFound() {
        when(orderRepository.existsById(1L)).thenReturn(true);
        doNothing().when(orderRepository).deleteById(1L);

        orderService.deleteOrder(1L);

        verify(orderRepository, times(1)).existsById(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }

    /**
     * Tests that deleteOrder throws OrderNotFoundException when order to delete is not found.
     */
    @Test
    void deleteOrder_shouldThrowOrderNotFoundException_whenNotFound() {
        when(orderRepository.existsById(1L)).thenReturn(false);

        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder(1L));

        verify(orderRepository, times(1)).existsById(1L);
        verify(orderRepository, never()).deleteById(anyLong());
    }
}
