package io.github.divyesh.user.dto;

import io.swagger.v3.oas.annotations.media.Schema; // Added import

/**
 * Represents a response containing user details.
 *
 * @param id The unique identifier of the user.
 * @param username The username of the user.
 * @param email The email address of the user.
 */
public record UserResponse(
    @Schema(description = "Unique identifier of the user", example = "1")
    Long id,
    @Schema(description = "Username of the user", example = "john.doe")
    String username,
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    String email) {

    /**
     * Creates a new builder for {@link UserResponse}.
     * @return A new {@link UserResponseBuilder}.
     */
    public static UserResponseBuilder builder() {
        return new UserResponseBuilder();
    }

    /**
     * Builder for {@link UserResponse}.
     */
    public static final class UserResponseBuilder {
        private Long id;
        private String username;
        private String email;

        /**
         * Private constructor to enforce the use of {@link #builder()}.
         */
        private UserResponseBuilder() {}

        /**
         * Sets the ID for the user response.
         * @param id The user ID.
         * @return The builder instance.
         */
        public UserResponseBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the username for the user response.
         * @param username The username.
         * @return The builder instance.
         */
        public UserResponseBuilder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Sets the email for the user response.
         * @param email The email address.
         * @return The builder instance.
         */
        public UserResponseBuilder email(String email) {
            this.email = email;
            return this;
        }

        /**
         * Builds a {@link UserResponse} instance.
         * @return A new {@link UserResponse}.
         */
        public UserResponse build() {
            return new UserResponse(id, username, email);
        }
    }
}
