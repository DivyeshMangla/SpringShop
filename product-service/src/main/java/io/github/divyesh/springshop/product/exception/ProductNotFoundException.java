package io.github.divyesh.springshop.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to indicate that a product was not found.
 * This exception maps to an HTTP 404 Not Found status.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

    /**
     * Constructs a ProductNotFoundException with the specified detail message.
     * @param message The detail message.
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}