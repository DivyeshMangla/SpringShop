package io.github.divyesh.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for product creation and update requests.
 * Contains fields that can be sent by the client.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String sku;
    private String imageUrl;
    private Map<String, String> attributes;
}