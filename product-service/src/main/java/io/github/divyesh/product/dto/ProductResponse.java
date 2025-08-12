package io.github.divyesh.product.dto;

import io.swagger.v3.oas.annotations.media.Schema; // Added import
import java.util.Map;

/**
 * DTO for product responses.
 *
 * @param id The unique identifier of the product.
 * @param name The name of the product.
 * @param description The description of the product.
 * @param price The price of the product.
 * @param quantity The available quantity of the product.
 * @param sku The Stock Keeping Unit of the product.
 * @param imageUrl The URL of the product image.
 * @param attributes Additional attributes of the product (e.g., color, size).
 */
public record ProductResponse(
    @Schema(description = "Unique identifier of the product", example = "60d0fe4f5e36a0001c03b8a0")
    String id,

    @Schema(description = "Name of the product", example = "Laptop")
    String name,

    @Schema(description = "Description of the product", example = "Powerful laptop for gaming and work")
    String description,

    @Schema(description = "Price of the product", example = "1200.00")
    double price,

    @Schema(description = "Available quantity of the product", example = "50")
    int quantity,

    @Schema(description = "Stock Keeping Unit of the product", example = "LAPTOP-GAMING-XYZ")
    String sku,

    @Schema(description = "URL of the product image", example = "https://example.com/laptop.jpg")
    String imageUrl,

    @Schema(description = "Additional attributes of the product (e.g., color, size)")
    Map<String, String> attributes) {

    /**
     * Entry point for creating a builder.
     * @return A new {@link ProductResponseBuilder}.
     */
    public static ProductResponseBuilder builder() {
        return new ProductResponseBuilder();
    }

    /**
     * Builder for {@link ProductResponse}.
     */
    public static final class ProductResponseBuilder {
        private String id;
        private String name;
        private String description;
        private double price;
        private int quantity;
        private String sku;
        private String imageUrl;
        private Map<String, String> attributes;

        /**
         * Private constructor to enforce the use of {@link #builder()}.
         */
        private ProductResponseBuilder() {
        }

        /**
         * Sets the ID for the product response.
         * @param id The product ID.
         * @return The builder instance.
         */
        public ProductResponseBuilder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name for the product response.
         * @param name The product name.
         * @return The builder instance.
         */
        public ProductResponseBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the description for the product response.
         * @param description The product description.
         * @return The builder instance.
         */
        public ProductResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the price for the product response.
         * @param price The product price.
         * @return The builder instance.
         */
        public ProductResponseBuilder price(double price) {
            this.price = price;
            return this;
        }

        /**
         * Sets the quantity for the product response.
         * @param quantity The product quantity.
         * @return The builder instance.
         */
        public ProductResponseBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        /**
         * Sets the SKU for the product response.
         * @param sku The product SKU.
         * @return The builder instance.
         */
        public ProductResponseBuilder sku(String sku) {
            this.sku = sku;
            return this;
        }

        /**
         * Sets the image URL for the product response.
         * @param imageUrl The product image URL.
         * @return The builder instance.
         */
        public ProductResponseBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        /**
         * Sets the attributes for the product response.
         * @param attributes The product attributes.
         * @return The builder instance.
         */
        public ProductResponseBuilder attributes(Map<String, String> attributes) {
            this.attributes = attributes;
            return this;
        }

        /**
         * Builds a {@link ProductResponse} instance.
         * @return A new {@link ProductResponse}.
         */
        public ProductResponse build() {
            return new ProductResponse(id, name, description, price, quantity, sku, imageUrl, attributes);
        }
    }
}
