package io.github.divyesh.product.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @Schema(description = "Unique identifier of the product", example = "60d0fe4f5e36a0001c03b8a0")
    private String id;

    @Schema(description = "Name of the product", example = "Laptop")
    private String name;

    @Schema(description = "Description of the product", example = "Powerful laptop for gaming and work")
    private String description;

    @Schema(description = "Price of the product", example = "1200.00")
    private double price;

    @Schema(description = "Available quantity of the product", example = "50")
    private int quantity;

    @Schema(description = "Stock Keeping Unit of the product", example = "LAPTOP-GAMING-XYZ")
    private String sku;

    @Schema(description = "URL of the product image", example = "https://example.com/laptop.jpg")
    private String imageUrl;

    @Schema(description = "Additional attributes of the product (e.g., color, size)")
    private Map<String, String> attributes;
}
