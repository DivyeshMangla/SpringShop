package io.github.divyesh.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema; // Added import

/**
 * Represents a request to create or update a user.
 *
 * @param username The username of the user.
 * @param email The email address of the user.
 * @param password The password of the user.
 */
public record UserRequest(
    @Schema(description = "Username of the user", example = "john.doe")
    @NotBlank(message = "Username is required")
    String username,

    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    String email,

    @Schema(description = "Password of the user", example = "securepassword123")
    @NotBlank(message = "Password is required")
    String password) {

    /**
     * Entry point for creating a builder.
     * @return A new {@link UserRequestBuilder}.
     */
    public static UserRequestBuilder builder() {
        return new UserRequestBuilder();
    }

    /**
     * Builder for {@link UserRequest}.
     */
    public static final class UserRequestBuilder {
        private String username;
        private String email;
        private String password;

        /**
         * Private constructor to enforce the use of {@link #builder()}.
         */
        private UserRequestBuilder() {}

        /**
         * Sets the username for the user request.
         * @param username The username.
         * @return The builder instance.
         */
        public UserRequestBuilder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Sets the email for the user request.
         * @param email The email address.
         * @return The builder instance.
         */
        public UserRequestBuilder email(String email) {
            this.email = email;
            return this;
        }

        /**
         * Sets the password for the user request.
         * @param password The password.
         * @return The builder instance.
         */
        public UserRequestBuilder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * Builds a {@link UserRequest} instance.
         * @return A new {@link UserRequest}.
         */
        public UserRequest build() {
            return new UserRequest(username, email, password);
        }
    }
}
