package io.github.divyesh.user.model;

/**
 * Enumeration for defining user roles within the application.
 * Each role has a corresponding authority string that Spring Security uses for authorization.
 */
public enum RoleName {
    /**
     * Represents a regular user with basic access.
     */
    ROLE_USER("ROLE_USER"),

    /**
     * Represents an administrator with elevated privileges.
     */
    ROLE_ADMIN("ROLE_ADMIN");

    private final String authority;

    /**
     * Constructs a {@code RoleName} with the given authority string.
     * @param authority The authority string associated with the role.
     */
    RoleName(String authority) {
        this.authority = authority;
    }

    /**
     * Returns the authority string associated with the role.
     * @return The authority string.
     */
    public String getAuthority() {
        return authority;
    }
}
