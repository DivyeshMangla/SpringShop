package io.github.divyesh.user.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for handling JWT tokens.
 * Provides methods for generating, parsing, and validating JWTs.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Key key;

    /**
     * Initializes the secret key for signing JWTs.
     * This method is called after the bean has been constructed.
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Extracts the username from a JWT token.
     * @param token The JWT token.
     * @return The username from the token.
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from a JWT token.
     * @param token The JWT token.
     * @return The expiration date from the token.
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim from a JWT token.
     * @param token The JWT token.
     * @param claimsResolver A function to extract the claim.
     * @param <T> The type of the claim.
     * @return The extracted claim.
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        Date expr = getExpirationDateFromToken(token);
        return expr.before(new Date());
    }

    /**
     * Generates a JWT token for a user.
     * @param userDetails The user details.
     * @return A JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(key, SignatureAlgorithm.HS512).compact();
    }

    /**
     * Validates a JWT token.
     * @param token The JWT token.
     * @param userDetails The user details.
     * @return True if the token is valid, false otherwise.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
