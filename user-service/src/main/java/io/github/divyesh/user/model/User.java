package io.github.divyesh.user.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user entity in the system.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

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
     * @param roles The roles assigned to the user.
     */
    public User(Long id, String username, String email, String password, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
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
     * Returns the roles assigned to the user.
     * @return A set of roles.
     */
    public Set<Role> getRoles() {
        return roles;
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
     * Sets the roles assigned to the user.
     * @param roles A set of roles to set.
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
        private Long id;
        private String username;
        private String email;
        private String password;
        private Set<Role> roles = new HashSet<>();

        /**
         * Private constructor to enforce the use of {@link #builder()}.
         */
        private UserBuilder() {}

        /**
         * Sets the ID for the user.
         * @param id The user ID.
         * @return The builder instance.
         */
        public UserBuilder id(Long id) {
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
         * Sets the roles for the user.
         * @param roles The roles.
         * @return The builder instance.
         */
        public UserBuilder roles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        /**
         * Builds a {@link User} instance.
         * @return A new {@link User}.
         */
        public User build() {
            return new User(id, username, email, password, roles);
        }
    }
}
