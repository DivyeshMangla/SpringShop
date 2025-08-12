package io.github.divyesh.springshop.product.controller;

import io.github.divyesh.springshop.product.dto.ProductRequest;
import io.github.divyesh.springshop.product.dto.ProductResponse;
import io.github.divyesh.springshop.product.model.Product;
import io.github.divyesh.springshop.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing products.
 * Provides endpoints for CRUD operations on products.
 */
@RestController
@RequestMapping("/api/products")
@Tag(name = "Product", description = "Product management APIs")
public class ProductController {

    private final ProductService productService;

    /**
     * Constructs a ProductController with the given ProductService.
     * @param productService The service for product business logic.
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Creates a new product.
     * @param productRequest The product data to create.
     * @return The created product.
     */
    @PostMapping
    @Operation(summary = "Create a new product", description = "Adds a new product to the database")
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        Product product = mapToProduct(productRequest);
        Product savedProduct = productService.saveProduct(product);
        return mapToProductResponse(savedProduct);
    }

    /**
     * Retrieves a list of all products.
     * @return A list of all products.
     */
    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves a list of all products")
    public List<ProductResponse> getProducts() {
        return productService.getAllProducts().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a product by its unique ID.
     * @param id The ID of the product to retrieve.
     * @return The product with the given ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieves a product by its unique ID")
    public ProductResponse getProduct(@PathVariable String id) {
        Product product = productService.getProductById(id);
        return mapToProductResponse(product);
    }

    /**
     * Deletes a product by its unique ID.
     * @param id The ID of the product to delete.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product by ID", description = "Deletes a product by its unique ID")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

    /**
     * Updates an existing product identified by its ID.
     * @param id The ID of the product to update.
     * @param productRequest The updated product data.
     * @return The updated product.
     * @throws IllegalArgumentException if the ID in the path does not match the ID in the request body.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update product by ID", description = "Updates an existing product identified by its ID")
    public ProductResponse updateProduct(@PathVariable String id, @RequestBody ProductRequest productRequest) {
        Product product = mapToProduct(productRequest);
        product.setId(id); // Set the ID from the path for update
        Product updatedProduct = productService.saveProduct(product);
        return mapToProductResponse(updatedProduct);
    }

    /**
     * Maps a ProductRequest DTO to a Product entity.
     * @param request The ProductRequest DTO to map.
     * @return The mapped Product entity.
     */
    private Product mapToProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setSku(request.getSku());
        product.setImageUrl(request.getImageUrl());
        product.setAttributes(request.getAttributes());
        return product;
    }

    /**
     * Maps a Product entity to a ProductResponse DTO.
     * @param product The Product entity to map.
     * @return The mapped ProductResponse DTO.
     */
    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setQuantity(product.getQuantity());
        response.setSku(product.getSku());
        response.setImageUrl(product.getImageUrl());
        response.setAttributes(product.getAttributes());
        return response;
    }
}
