package io.github.divyesh.user.filter;

import io.github.divyesh.user.service.UserDetailsServiceImpl;
import io.github.divyesh.user.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT authentication filter that intercepts incoming requests to validate JWT tokens.
 * This filter extends {@link OncePerRequestFilter} to ensure it is executed only once per request.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Constructs a {@code JwtAuthenticationFilter} with the given {@code JwtUtil} and {@code UserDetailsServiceImpl}.
     * @param jwtUtil The utility for handling JWTs.
     * @param userDetailsService The service for loading user-specific data.
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Performs the actual filtering logic.
     * This method extracts the JWT from the Authorization header, validates it, and sets the authentication
     * in the SecurityContextHolder if the token is valid.
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @param filterChain The filter chain.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        username = jwtUtil.getUsernameFromToken(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
