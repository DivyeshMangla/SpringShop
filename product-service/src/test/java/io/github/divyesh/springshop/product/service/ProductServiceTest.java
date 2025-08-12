package io.github.divyesh.springshop.product.service;

import io.github.divyesh.springshop.product.exception.ProductNotFoundException;
import io.github.divyesh.springshop.product.model.Product;
import io.github.divyesh.springshop.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ProductService class.
 * These tests focus on the business logic of the ProductService in isolation,
 * mocking the ProductRepository dependency.
 */
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests that saveProduct successfully saves a new product.
     */
    @Test
    void saveProduct_shouldReturnSavedProduct() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.saveProduct(product);
        assertNotNull(savedProduct);
        verify(productRepository, times(1)).save(product);
    }

    /**
     * Tests that getAllProducts returns a list of all products.
     */
    @Test
    void getAllProducts_shouldReturnListOfProducts() {
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> products = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(products);
        List<Product> retrievedProducts = productService.getAllProducts();
        assertNotNull(retrievedProducts);
        assertEquals(2, retrievedProducts.size());
        verify(productRepository, times(1)).findAll();
    }

    /**
     * Tests that getProductById returns a product when found.
     */
    @Test
    void getProductById_shouldReturnProduct_whenFound() {
        Product product = new Product();
        product.setId("1");
        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        Product retrievedProduct = productService.getProductById("1");
        assertNotNull(retrievedProduct);
        assertEquals("1", retrievedProduct.getId());
        verify(productRepository, times(1)).findById("1");
    }

    /**
     * Tests that getProductById throws ProductNotFoundException when the product is not found.
     */
    @Test
    void getProductById_shouldThrowProductNotFoundException_whenNotFound() {
        when(productRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById("1"));
        verify(productRepository, times(1)).findById("1");
    }

    /**
     * Tests that deleteProduct successfully deletes an existing product.
     */
    @Test
    void deleteProduct_shouldDeleteProduct_whenFound() {
        when(productRepository.existsById("1")).thenReturn(true);
        productService.deleteProduct("1");
        verify(productRepository, times(1)).existsById("1");
        verify(productRepository, times(1)).deleteById("1");
    }

    /**
     * Tests that deleteProduct throws ProductNotFoundException when the product to delete is not found.
     */
    @Test
    void deleteProduct_shouldThrowProductNotFoundException_whenNotFound() {
        when(productRepository.existsById("1")).thenReturn(false);
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct("1"));
        verify(productRepository, times(1)).existsById("1");
        verify(productRepository, never()).deleteById(anyString());
    }
}