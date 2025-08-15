package io.github.divyesh.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a JWT response.
 *
 * @param token The JWT token.
 */
public record JwtResponse(
    @Schema(description = "JWT token", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqb2huLmRvZSIsImlhdCI6MTY3ODg4NjQyMywiZXhwIjoxNjc4OTcyODIwfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    String token) {
}
