package io.github.divyesh.springshop.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO for product responses.
 * Contains all product fields, including the server-generated ID.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String sku;
    private String imageUrl;
    private Map<String, String> attributes;
}