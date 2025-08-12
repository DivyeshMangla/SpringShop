package io.github.divyesh.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Main application class for the Product Service.
 * This service handles product-related operations and registers itself with Eureka for discovery.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProductServiceApplication {

    /**
     * Main method to run the Product Service application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}