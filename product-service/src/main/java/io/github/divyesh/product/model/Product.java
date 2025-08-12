package io.github.divyesh.product.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * Represents a product entity in the system.
 */
@Document(collection = "products")
public class Product {
    @Id
    private String id;

    private String name;
    private String description;
    private double price;
    private int quantity;
    private String sku;
    private String imageUrl;
    private Map<String, String> attributes;

    /**
     * Default constructor for MongoDB.
     */
    public Product() {}

    /**
     * Constructs a new Product with the specified details.
     * @param id The unique identifier of the product.
     * @param name The name of the product.
     * @param description The description of the product.
     * @param price The price of the product.
     * @param quantity The available quantity of the product.
     * @param sku The Stock Keeping Unit of the product.
     * @param imageUrl The URL of the product image.
     * @param attributes Additional attributes of the product.
     */
    public Product(String id, String name, String description, double price, int quantity, String sku, String imageUrl, Map<String, String> attributes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.sku = sku;
        this.imageUrl = imageUrl;
        this.attributes = attributes;
    }

    /**
     * Returns the unique identifier of the product.
     * @return The product ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the name of the product.
     * @return The product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the product.
     * @return The product description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the price of the product.
     * @return The product price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the available quantity of the product.
     * @return The product quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the Stock Keeping Unit of the product.
     * @return The product SKU.
     */
    public String getSku() {
        return sku;
    }

    /**
     * Returns the URL of the product image.
     * @return The product image URL.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Returns the additional attributes of the product.
     * @return A map of product attributes.
     */
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * Sets the unique identifier of the product.
     * @param id The product ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the name of the product.
     * @param name The product name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the description of the product.
     * @param description The product description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the price of the product.
     * @param price The product price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the quantity of the product.
     * @param quantity The product quantity to set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Sets the Stock Keeping Unit of the product.
     * @param sku The product SKU to set.
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     * Sets the URL of the product image.
     * @param imageUrl The product image URL to set.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Sets the additional attributes of the product.
     * @param attributes A map of product attributes to set.
     */
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    /**
     * Creates a new builder for {@link Product}.
     * @return A new {@link ProductBuilder}.
     */
    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    /**
     * Builder for {@link Product}.
     */
    public static class ProductBuilder {
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
        private ProductBuilder() {
        }

        /**
         * Sets the ID for the product.
         * @param id The product ID.
         * @return The builder instance.
         */
        public ProductBuilder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name for the product.
         * @param name The product name.
         * @return The builder instance.
         */
        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the description for the product.
         * @param description The product description.
         * @return The builder instance.
         */
        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets the price for the product.
         * @param price The product price.
         * @return The builder instance.
         */
        public ProductBuilder price(double price) {
            this.price = price;
            return this;
        }

        /**
         * Sets the quantity for the product.
         * @param quantity The product quantity.
         * @return The builder instance.
         */
        public ProductBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        /**
         * Sets the SKU for the product.
         * @param sku The product SKU.
         * @return The builder instance.
         */
        public ProductBuilder sku(String sku) {
            this.sku = sku;
            return this;
        }

        /**
         * Sets the image URL for the product.
         * @param imageUrl The product image URL.
         * @return The builder instance.
         */
        public ProductBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        /**
         * Sets the attributes for the product.
         * @param attributes The product attributes.
         * @return The builder instance.
         */
        public ProductBuilder attributes(Map<String, String> attributes) {
            this.attributes = attributes;
            return this;
        }

        /**
         * Builds a {@link Product} instance.
         * @return A new {@link Product}.
         */
        public Product build() {
            return new Product(id, name, description, price, quantity, sku, imageUrl, attributes);
        }
    }
}
