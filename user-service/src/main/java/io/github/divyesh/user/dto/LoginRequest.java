package io.github.divyesh.user.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a login request.
 *
 * @param username The username of the user.
 * @param password The password of the user.
 */
public record LoginRequest(
    @Schema(description = "Username of the user", example = "john.doe")
    @NotBlank(message = "Username is required")
    String username,

    @Schema(description = "Password of the user", example = "securepassword123")
    @NotBlank(message = "Password is required")
    String password) {
}