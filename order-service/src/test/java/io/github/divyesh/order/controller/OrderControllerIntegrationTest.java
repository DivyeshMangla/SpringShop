package io.github.divyesh.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.divyesh.order.dto.OrderItemRequest;
import io.github.divyesh.order.dto.OrderRequest;
import io.github.divyesh.order.exception.OrderNotFoundException;
import io.github.divyesh.order.model.Order;
import io.github.divyesh.order.model.OrderItem;
import io.github.divyesh.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the OrderController class.
 * These tests focus on the web layer, ensuring correct request mapping, request body parsing,
 * response serialization, and exception handling.
 * The OrderService is mocked to isolate the controller's behavior.
 */
@WebMvcTest(controllers = OrderController.class)
class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Tests that createOrder endpoint successfully creates a new order.
     */
    @Test
    void createOrder_shouldReturnCreatedOrder() throws Exception {
        OrderItemRequest itemRequest = OrderItemRequest.builder()
                .productId("prod1")
                .quantity(2)
                .price(10.0)
                .build();
        OrderRequest orderRequest = OrderRequest.builder()
                .userId(1L)
                .orderItemRequests(List.of(itemRequest))
                .build();

        Order createdOrderEntity = new Order();
        createdOrderEntity.setId(1L);
        createdOrderEntity.setUserId(1L);
        createdOrderEntity.setOrderDate(LocalDateTime.now());
        createdOrderEntity.setStatus("PENDING");
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId("prod1");
        orderItem.setQuantity(2);
        orderItem.setPrice(10.0);
        orderItem.setOrder(createdOrderEntity);
        createdOrderEntity.setOrderItems(List.of(orderItem));
        createdOrderEntity.setTotalAmount(20.0);

        when(orderService.createOrder(any(OrderRequest.class))).thenReturn(createdOrderEntity);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isCreated());
    }

    /**
     * Tests that getAllOrders endpoint returns a list of all orders.
     */
    @Test
    void getAllOrders_shouldReturnListOfOrders() throws Exception {
        Order order1 = new Order();
        order1.setId(1L);
        order1.setUserId(1L);
        order1.setOrderDate(LocalDateTime.now());
        order1.setStatus("PENDING");
        order1.setOrderItems(List.of(OrderItem.builder().productId("p1").quantity(1).price(10.0).build()));
        order1.setTotalAmount(10.0);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setUserId(2L);
        order2.setOrderDate(LocalDateTime.now());
        order2.setStatus("CONFIRMED");
        order2.setOrderItems(List.of(OrderItem.builder().productId("p2").quantity(2).price(20.0).build()));
        order2.setTotalAmount(40.0);

        List<Order> allOrders = Arrays.asList(order1, order2);

        when(orderService.getAllOrders()).thenReturn(allOrders);

        mockMvc.perform(get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[1].userId").value(2L));
    }

    /**
     * Tests that getOrderById endpoint returns an order when found.
     */
    @Test
    void getOrderById_shouldReturnOrder_whenFound() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setUserId(1L);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setOrderItems(List.of(OrderItem.builder().productId("p1").quantity(1).price(10.0).build()));
        order.setTotalAmount(10.0);

        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/api/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Tests that getOrderById endpoint returns 404 Not Found when the order is not found.
     */
    @Test
    void getOrderById_shouldReturnNotFound_whenNotFound() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Tests that updateOrder endpoint successfully updates an existing order.
     */
    @Test
    void updateOrder_shouldReturnUpdatedOrder() throws Exception {
        OrderItemRequest itemRequest = OrderItemRequest.builder()
                .productId("prod1")
                .quantity(3)
                .price(12.0)
                .build();
        OrderRequest orderRequest = OrderRequest.builder()
                .userId(1L)
                .orderItemRequests(List.of(itemRequest))
                .build();

        Order updatedOrderEntity = new Order();
        updatedOrderEntity.setId(1L);
        updatedOrderEntity.setUserId(1L);
        updatedOrderEntity.setOrderDate(LocalDateTime.now());
        updatedOrderEntity.setStatus("CONFIRMED");
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId("prod1");
        orderItem.setQuantity(3);
        orderItem.setPrice(12.0);
        orderItem.setOrder(updatedOrderEntity);
        updatedOrderEntity.setOrderItems(List.of(orderItem));
        updatedOrderEntity.setTotalAmount(36.0);

        when(orderService.updateOrder(eq(1L), any(Order.class))).thenReturn(Optional.of(updatedOrderEntity));

        mockMvc.perform(put("/api/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".status").value("CONFIRMED"));
    }

    /**
     * Tests that updateOrder endpoint returns 404 Not Found when the order to update is not found.
     */
    @Test
    void updateOrder_shouldReturnNotFound_whenNotFound() throws Exception {
        OrderItemRequest itemRequest = OrderItemRequest.builder()
                .productId("prod1")
                .quantity(3)
                .price(12.0)
                .build();
        OrderRequest orderRequest = OrderRequest.builder()
                .userId(1L)
                .orderItemRequests(List.of(itemRequest))
                .build();

        when(orderService.updateOrder(eq(1L), any(Order.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isNotFound());
    }

    /**
     * Tests that deleteOrder endpoint successfully deletes an order.
     */
    @Test
    void deleteOrder_shouldReturnNoContent_whenDeleted() throws Exception {
        doNothing().when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/api/orders/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    /**
     * Tests that deleteOrder endpoint returns 404 Not Found when the order to delete is not found.
     */
    @Test
    void deleteOrder_shouldReturnNotFound_whenNotFound() throws Exception {
        doThrow(new OrderNotFoundException("Order not found")).when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/api/orders/{id}", 1L))
                .andExpect(status().isNotFound());
    }
}