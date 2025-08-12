package io.github.divyesh.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.divyesh.product.controller.ProductController;
import io.github.divyesh.product.dto.ProductRequest;
import io.github.divyesh.product.exception.ProductNotFoundException;
import io.github.divyesh.product.model.Product;
import io.github.divyesh.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the ProductController class.
 * These tests focus on the web layer, ensuring correct request mapping, request body parsing,
 * response serialization, and exception handling.
 * The ProductService is mocked to isolate the controller's behavior.
 */
@WebMvcTest(controllers = ProductController.class)
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Tests that createProduct endpoint successfully creates a new product.
     */
    @Test
    void createProduct_shouldReturnCreatedProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Test Product");
        productRequest.setDescription("Description");
        productRequest.setPrice(10.0);
        productRequest.setQuantity(100);
        productRequest.setSku("SKU123");
        productRequest.setImageUrl("http://example.com/image.jpg");
        productRequest.setAttributes(Map.of("color", "red"));

        Product productEntity = new Product();
        productEntity.setId("new-id");
        productEntity.setName(productRequest.getName());

        when(productService.saveProduct(any(Product.class))).thenReturn(productEntity);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".id").exists())
                .andExpect(jsonPath(".name").value("Test Product"));
    }

    /**
     * Tests that getAllProducts endpoint returns a list of all products.
     */
    @Test
    void getAllProducts_shouldReturnListOfProducts() throws Exception {
        Product product1 = new Product();
        product1.setId("1");
        product1.setName("Product 1");
        Product product2 = new Product();
        product2.setId("2");
        product2.setName("Product 2");
        List<Product> allProducts = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(allProducts);

        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Product 1"));
    }

    /**
     * Tests that getProductById endpoint returns a product when found.
     */
    @Test
    void getProductById_shouldReturnProduct_whenFound() throws Exception {
        Product product = new Product();
        product.setId("1");
        product.setName("Test Product");

        when(productService.getProductById("1")).thenReturn(product);

        mockMvc.perform(get("/api/products/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".id").value("1"))
                .andExpect(jsonPath(".name").value("Test Product"));
    }

    /**
     * Tests that getProductById endpoint returns 404 Not Found when the product is not found.
     */
    @Test
    void getProductById_shouldReturnNotFound_whenNotFound() throws Exception {
        doThrow(new ProductNotFoundException("Product not found")).when(productService).getProductById("1");

        mockMvc.perform(get("/api/products/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    /**
     * Tests that updateProduct endpoint successfully updates an existing product.
     */
    @Test
    void updateProduct_shouldReturnUpdatedProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Updated Product");
        productRequest.setDescription("Updated Description");
        productRequest.setPrice(15.0);
        productRequest.setQuantity(50);
        productRequest.setSku("SKU123");
        productRequest.setImageUrl("http://example.com/updated_image.jpg");
        productRequest.setAttributes(java.util.Map.of("color", "blue"));

        Product updatedProductEntity = new Product();
        updatedProductEntity.setId("1");
        updatedProductEntity.setName(productRequest.getName());
        // ... copy other fields from productRequest to updatedProductEntity

        when(productService.saveProduct(any(Product.class))).thenReturn(updatedProductEntity);

        mockMvc.perform(put("/api/products/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".name").value("Updated Product"));
    }

    /**
     * Tests that deleteProduct endpoint successfully deletes a product.
     */
    @Test
    void deleteProduct_shouldReturnNoContent_whenDeleted() throws Exception {
        doNothing().when(productService).deleteProduct("1");

        mockMvc.perform(delete("/api/products/{id}", "1"))
                .andExpect(status().isOk());
    }

    /**
     * Tests that deleteProduct endpoint returns 404 Not Found when the product to delete is not found.
     */
    @Test
    void deleteProduct_shouldReturnNotFound_whenNotFound() throws Exception {
        doThrow(new ProductNotFoundException("Product not found")).when(productService).deleteProduct("1");

        mockMvc.perform(delete("/api/products/{id}", "1"))
                .andExpect(status().isNotFound());
    }
}