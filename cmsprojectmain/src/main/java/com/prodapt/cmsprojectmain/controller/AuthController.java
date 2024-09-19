package com.prodapt.cmsprojectmain.controller;

import java.util.Optional;

import javax.management.relation.RoleNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prodapt.cmsprojectmain.entities.ERole;
import com.prodapt.cmsprojectmain.entities.Role;
import com.prodapt.cmsprojectmain.entities.UserEntity;
import com.prodapt.cmsprojectmain.exceptions.UsernameAlreadyExistsException;
import com.prodapt.cmsprojectmain.security.jwt.JwtUtils;
import com.prodapt.cmsprojectmain.security.payload.request.LoginRequest;
import com.prodapt.cmsprojectmain.security.payload.request.SignupRequest;
import com.prodapt.cmsprojectmain.security.payload.response.JwtResponse;
import com.prodapt.cmsprojectmain.security.payload.response.MessageResponse;
import com.prodapt.cmsprojectmain.security.service.UserDetailsImpl;
import com.prodapt.cmsprojectmain.service.RoleService;
import com.prodapt.cmsprojectmain.service.UserEntityService;

import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
 
    @Autowired
    AuthenticationManager authenticationManager;
 
    @Autowired
    JwtUtils jwtUtils;
 
    @Autowired
    UserEntityService userService;
 
    @Autowired
    RoleService roleService;
 
    @Autowired
    PasswordEncoder passwordEncoder;
 
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws BadCredentialsException {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
 
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
 
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String role = userDetails.getAuthorities().stream().findFirst() // Get the first authority
                    .map(item -> item.getAuthority()) // Map it to its authority string
                    .orElse(null);
 
            JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
                    userDetails.getEmail(), role);
 
            return ResponseEntity.ok(jwtResponse);
    }
 
    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws UsernameAlreadyExistsException, RoleNotFoundException {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            throw new UsernameAlreadyExistsException("Error: Username is already taken!");
        }
 
        UserEntity user = new UserEntity();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        Optional<Role> role = Optional.of(roleService.findRoleByName(ERole.ROLE_CUSTOMER).get());
        
        if(role.isPresent()) {
        	user.setRole(role.get());
        }
 
        userService.addUserEntity(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}