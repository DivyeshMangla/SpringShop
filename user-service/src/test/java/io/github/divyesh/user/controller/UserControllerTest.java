package io.github.divyesh.user.controller;

import io.github.divyesh.user.config.SecurityConfig;
import io.github.divyesh.user.model.Role;
import io.github.divyesh.user.model.RoleName;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(RoleName.ROLE_USER));
        User savedUser = User.builder().id(1L).username("testuser").email("test@example.com").roles(roles).build();
        when(userService.createUser(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/api/users/register").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"testuser\", \"email\": \"test@example.com\", \"password\": \"password\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void login_shouldReturnJwt() throws Exception {
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("testuser", "password", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("test-jwt");

        mockMvc.perform(post("/api/users/login").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"username\": \"testuser\", \"password\": \"password\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("test-jwt"));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void getAllUsers_shouldReturnOkForAdmin() throws Exception {
        User user1 = User.builder().id(1L).username("user1").build();
        User user2 = User.builder().id(2L).username("user2").build();
        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void getAllUsers_shouldReturnForbiddenForUser() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    void deleteUser_shouldReturnNoContentForAdmin() throws Exception {
        doNothing().when(userService).deleteUser(anyLong());

        mockMvc.perform(delete("/api/users/{id}", 1L).with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void deleteUser_shouldReturnForbiddenForUser() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 1L).with(csrf()))
                .andExpect(status().isForbidden());
    }
}
