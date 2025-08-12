package io.github.divyesh.product.service;

import io.github.divyesh.product.exception.ProductNotFoundException;
import io.github.divyesh.product.model.Product;
import io.github.divyesh.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing products.
 * Provides business logic for CRUD operations on products.
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * Constructs a ProductService with the given ProductRepository.
     * @param productRepository The repository for product data.
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Saves a product to the database. If the product has an ID, it updates the existing product.
     * Otherwise, it creates a new product.
     * @param product The product to save.
     * @return The saved product.
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Retrieves all products from the database.
     * @return A list of all products.
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a product by its ID.
     * @param id The ID of the product to retrieve.
     * @return The product with the given ID.
     * @throws ProductNotFoundException if the product is not found.
     */
    public Product getProductById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }

    /**
     * Deletes a product by its ID.
     * Checks if the product exists before attempting to delete to provide a more informative error.
     * @param id The ID of the product to delete.
     * @throws ProductNotFoundException if the product is not found.
     */
    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }

        productRepository.deleteById(id);
    }
}
