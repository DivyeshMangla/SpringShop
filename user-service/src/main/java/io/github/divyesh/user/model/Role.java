package io.github.divyesh.user.model;

import jakarta.persistence.*;

/**
 * Represents a role entity in the system.
 * Roles are used for authorization and access control.
 */
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleName name;

    /**
     * Default constructor for JPA.
     */
    public Role() {}

    /**
     * Constructs a new Role with the specified details.
     * @param id The unique identifier of the role.
     * @param name The name of the role.
     */
    public Role(Long id, RoleName name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Constructs a new Role with the specified name.
     * @param name The name of the role.
     */
    public Role(RoleName name) {
        this.name = name;
    }

    /**
     * Returns the unique identifier of the role.
     * @return The role ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the role.
     * @param id The role ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the name of the role.
     * @return The role name.
     */
    public RoleName getName() {
        return name;
    }

    /**
     * Sets the name of the role.
     * @param name The role name to set.
     */
    public void setName(RoleName name) {
        this.name = name;
    }

    /**
     * Creates a new builder for {@link Role}.
     * @return A new {@link RoleBuilder}.
     */
    public static RoleBuilder builder() {
        return new RoleBuilder();
    }

    /**
     * Builder for {@link Role}.
     */
    public static class RoleBuilder {
        private Long id;
        private RoleName name;

        /**
         * Private constructor to enforce the use of {@link #builder()}.
         */
        private RoleBuilder() {}

        /**
         * Sets the ID for the role.
         * @param id The role ID.
         * @return The builder instance.
         */
        public RoleBuilder id(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name for the role.
         * @param name The role name.
         * @return The builder instance.
         */
        public RoleBuilder name(RoleName name) {
            this.name = name;
            return this;
        }

        /**
         * Builds a {@link Role} instance.
         * @return A new {@link Role}.
         */
        public Role build() {
            return new Role(id, name);
        }
    }
}
