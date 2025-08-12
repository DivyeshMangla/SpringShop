package io.github.divyesh.user.model;

import jakarta.persistence.*;

/**
 * Represents a user entity in the system.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;

    /**
     * Default constructor for JPA.
     */
    public User() {}

    /**
     * Constructs a new User with the specified details.
     * @param id The unique identifier of the user.
     * @param username The username of the user.
     * @param email The email address of the user.
     * @param password The password of the user.
     */
    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the unique identifier of the user.
     * @return The user ID.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the username of the user.
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the email address of the user.
     * @return The email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the password of the user.
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the unique identifier of the user.
     * @param id The user ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets the username of the user.
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the email address of the user.
     * @param email The email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the password of the user.
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Creates a new builder for {@link User}.
     * @return A new {@link UserBuilder}.
     */
    public static UserBuilder builder() {
        return new UserBuilder();
    }

    /**
     * Builder for {@link User}.
     */
    public static class UserBuilder {
        private long id;
        private String username;
        private String email;
        private String password;

        /**
         * Private constructor to enforce the use of {@link #builder()}.
         */
        private UserBuilder() {}

        /**
         * Sets the ID for the user.
         * @param id The user ID.
         * @return The builder instance.
         */
        public UserBuilder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the username for the user.
         * @param username The username.
         * @return The builder instance.
         */
        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Sets the email for the user.
         * @param email The email address.
         * @return The builder instance.
         */
        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        /**
         * Sets the password for the user.
         * @param password The password.
         * @return The builder instance.
         */
        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * Builds a {@link User} instance.
         * @return A new {@link User}.
         */
        public User build() {
            return new User(id, username, email, password);
        }
    }
}
