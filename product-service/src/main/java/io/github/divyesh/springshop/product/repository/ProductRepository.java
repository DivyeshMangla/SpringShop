package io.github.divyesh.springshop.product.repository;

import io.github.divyesh.springshop.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
