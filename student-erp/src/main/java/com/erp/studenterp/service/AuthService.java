package com.erp.studenterp.service;

import com.erp.studenterp.dto.AuthResponse;
import com.erp.studenterp.dto.LoginRequest;
import com.erp.studenterp.entity.User;
import com.erp.studenterp.exception.BadRequestException;
import com.erp.studenterp.repository.UserRepository;
import com.erp.studenterp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse login(LoginRequest loginRequest) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Get user details
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new BadRequestException("User not found"));

        // Generate JWT token
        String jwt = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

        // Return response
        return new AuthResponse(
                jwt,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );
    }

    public User registerAdmin(String username, String password, String email) {
        // Check if username exists
        if (userRepository.existsByUsername(username)) {
            throw new BadRequestException("Username already exists");
        }

        // Check if email exists
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("Email already exists");
        }

        // Create admin user
        User admin = new User();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setEmail(email);
        admin.setRole(User.Role.ADMIN);
        admin.setActive(true);

        return userRepository.save(admin);
    }
}