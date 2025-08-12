package io.github.divyesh.springshop.product.repository;

import io.github.divyesh.springshop.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for Product entities.
 * Extends MongoRepository to provide basic CRUD operations for Product.
 */
public interface ProductRepository extends MongoRepository<Product, String> {}
