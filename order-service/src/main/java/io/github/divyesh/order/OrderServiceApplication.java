package io.github.divyesh.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main entry point for the Order Service application.
 * This application handles all operations related to customer orders,
 * including creation, retrieval, and management of order details.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderServiceApplication {

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
