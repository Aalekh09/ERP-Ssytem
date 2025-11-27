package com.erp.studenterp.controller;

import com.erp.studenterp.dto.AuthResponse;
import com.erp.studenterp.dto.LoginRequest;
import com.erp.studenterp.entity.User;
import com.erp.studenterp.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<Map<String, String>> registerAdmin(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        String email = request.get("email");

        User admin = authService.registerAdmin(username, password, email);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Admin registered successfully");
        response.put("username", admin.getUsername());
        response.put("email", admin.getEmail());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Auth API is working!");
        return ResponseEntity.ok(response);
    }
}