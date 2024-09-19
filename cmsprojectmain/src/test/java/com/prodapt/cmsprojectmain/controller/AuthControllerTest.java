package com.prodapt.cmsprojectmain.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.management.relation.RoleNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.prodapt.cmsprojectmain.entities.ERole;
import com.prodapt.cmsprojectmain.entities.Role;
import com.prodapt.cmsprojectmain.exceptions.UsernameAlreadyExistsException;
import com.prodapt.cmsprojectmain.security.jwt.JwtUtils;
import com.prodapt.cmsprojectmain.security.payload.request.LoginRequest;
import com.prodapt.cmsprojectmain.security.payload.request.SignupRequest;
import com.prodapt.cmsprojectmain.security.payload.response.JwtResponse;
import com.prodapt.cmsprojectmain.security.payload.response.MessageResponse;
import com.prodapt.cmsprojectmain.security.service.UserDetailsImpl;
import com.prodapt.cmsprojectmain.service.RoleService;
import com.prodapt.cmsprojectmain.service.UserEntityService;
 
class AuthControllerTest {
 
    private AuthController authController;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private UserEntityService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
 
    @BeforeEach
    void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        jwtUtils = mock(JwtUtils.class);
        userService = mock(UserEntityService.class);
        roleService = mock(RoleService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        authController = new AuthController();
        authController.authenticationManager = authenticationManager;
        authController.jwtUtils = jwtUtils;
        authController.userService = userService;
        authController.roleService = roleService;
        authController.passwordEncoder = passwordEncoder;
    }
 
    @Test
    void testAuthenticateUser_Success() {
        // Mocking
        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = new UserDetailsImpl(1, "test_user", "email", "password",mock(GrantedAuthority.class));
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("test_token");
 
        // Test
        LoginRequest loginRequest = new LoginRequest("test_user", "password");
        ResponseEntity<JwtResponse> response = authController.authenticateUser(loginRequest);
 
        // Assertion
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("test_token", response.getBody().getAccessToken());
        assertEquals(1, response.getBody().getId());
        assertEquals("test_user", response.getBody().getUsername());
    }
 
    @Test
    void testRegisterUser_Success() throws UsernameAlreadyExistsException, RoleNotFoundException {
        // Mocking
        SignupRequest signupRequest = new SignupRequest("new_user", "password", "email");
        Role role=new Role();
        role.setId(3);
        when(userService.existsByUsername("new_user")).thenReturn(false);
        when(roleService.findRoleByName(ERole.ROLE_CUSTOMER)).thenReturn(java.util.Optional.of(role));
        // Test
        ResponseEntity<MessageResponse> response = authController.registerUser(signupRequest);
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully!", response.getBody().getMessage());
    }
 
    @Test
    void testRegisterUserExistingUsername() {
        // Mocking
        SignupRequest signupRequest = new SignupRequest("existing_user", "email", "password");
        when(userService.existsByUsername("existing_user")).thenReturn(true);
 
        // Test
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            authController.registerUser(signupRequest);
        });
    }
 
}