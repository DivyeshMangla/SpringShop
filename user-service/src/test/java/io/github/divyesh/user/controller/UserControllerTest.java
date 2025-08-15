package io.github.divyesh.user.controller;

import io.github.divyesh.user.config.SecurityConfig;
import io.github.divyesh.user.dto.LoginRequest;
import io.github.divyesh.user.model.User;
import io.github.divyesh.user.service.UserDetailsServiceImpl;
import io.github.divyesh.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {

    private @Autowired MockMvc mockMvc;
    private @MockBean UserService userService;
    private @MockBean UserDetailsServiceImpl userDetailsService;
    private @MockBean AuthenticationManager authenticationManager;

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
    void login_shouldReturnOk() throws Exception {
        LoginRequest loginRequest = new LoginRequest("testuser", "password");
        User user = User.builder().id(1L).username("testuser").email("test@example.com").password("password").build();
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(new org.springframework.security.core.userdetails.User("testuser", "password", new ArrayList<>()));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
        when(authenticationManager.authenticate(any())).thenReturn(authToken);
        when(userService.findByUsername("testuser")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/users/login").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"testuser\", \"password\": \"password\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }
}
