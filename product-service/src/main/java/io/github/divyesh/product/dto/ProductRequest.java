package io.github.divyesh.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

/**
 * DTO for product creation and update requests.
 *
 * @param name The name of the product.
 * @param description The description of the product.
 * @param price The price of the product.
 * @param quantity The quantity of the product.
 * @param sku The Stock Keeping Unit of the product.
 * @param imageUrl The URL of the product image.
 * @param attributes Additional attributes of the product (e.g., color, size).
 */
public record ProductRequest(
    @Schema(description = "Name of the product", example = "Laptop")
    @NotBlank(message = "Product name is required")
    String name,

    @Schema(description = "Description of the product", example = "Powerful laptop for gaming and work")
    @NotBlank(message = "Product description is required")
    String description,

    @Schema(description = "Price of the product", example = "1200.00")
    @Min(value = 0, message = "Price must be non-negative")
    double price,

    @Schema(description = "Available quantity of the product", example = "50")
    @Min(value = 0, message = "Quantity must be non-negative")
    int quantity,

    @Schema(description = "Stock Keeping Unit of the product", example = "LAPTOP-GAMING-XYZ")
    @NotBlank(message = "SKU is required")
    String sku,

    @Schema(description = "URL of the product image", example = "https://example.com/laptop.jpg")
    String imageUrl,

    @Schema(description = "Additional attributes of the product (e.g., color, size)")
    Map<String, String> attributes) {

    /**
     * Entry point for creating a builder.
     * @return A new {@link ProductRequest}.
     */
    public static ProductRequestBuilder builder() {
        return new ProductRequestBuilder();
    }

    /**
     * Builder for {@link ProductRequest}.
     */
    public static final class ProductRequestBuilder {
        private String name;
        private String description;
        private double price;
        private int quantity;
        private String sku;
        private String imageUrl;
        private Map<String, String> attributes;

        /**
         * Private constructor to enforce the use of {@link #builder()()}.
         */
        private ProductRequestBuilder() {}

        /**
         * Sets the name for the product request.
         * @param name The product name.
         * @return The builder instance.
         */
        public ProductRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the description for the product request.
         * @param description The product description.
         * @return The builder instance.
         */
        public ProductRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the price for the product request.
         * @param price The product price.
         * @return The builder instance.
         */
        public ProductRequestBuilder price(double price) {
            this.price = price;
            return this;
        }

        /**
         * Sets the quantity for the product request.
         * @param quantity The product quantity.
         * @return The builder instance.
         */
        public ProductRequestBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        /**
         * Sets the SKU for the product request.
         * @param sku The product SKU.
         * @return The builder instance.
         */
        public ProductRequestBuilder sku(String sku) {
            this.sku = sku;
            return this;
        }

        /**
         * Sets the image URL for the product request.
         * @param imageUrl The product image URL.
         * @return The builder instance.
         */
        public ProductRequestBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        /**
         * Sets the attributes for the product request.
         * @param attributes The product attributes.
         * @return The builder instance.
         */
        public ProductRequestBuilder attributes(Map<String, String> attributes) {
            this.attributes = attributes;
            return this;
        }

        /**
         * Builds a {@link ProductRequest} instance.
         * @return A new {@link ProductRequest}.
         */
        public ProductRequest build() {
            return new ProductRequest(name, description, price, quantity, sku, imageUrl, attributes);
        }
    }
}
