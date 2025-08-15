package io.github.divyesh.user.controller;

import io.github.divyesh.user.config.SecurityConfig;
import io.github.divyesh.user.model.User;
import io.github.divyesh.user.service.UserDetailsServiceImpl;
import io.github.divyesh.user.service.UserService;
import io.github.divyesh.user.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void registerUser_shouldReturnCreated() throws Exception {
        User savedUser = User.builder().id(1L).username("testuser").email("test@example.com").build();
        when(userService.createUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users/register").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"testuser\", \"email\": \"test@example.com\", \"password\": \"password\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void login_shouldReturnJwt() throws Exception {
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("testuser", "password", new ArrayList<>());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("test-jwt");

        mockMvc.perform(post("/api/users/login").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"testuser\", \"password\": \"password\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("test-jwt"));
    }
}
