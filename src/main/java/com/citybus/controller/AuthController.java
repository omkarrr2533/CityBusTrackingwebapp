package com.citybus.controller;

import com.citybus.model.User;
import com.citybus.service.AuthService;
import com.citybus.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            if (username == null || username.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Username is required"));
            }

            if (password == null || password.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("Password is required"));
            }

            Optional<User> userOpt = userService.findByUsername(username.trim());
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (userService.verifyPassword(password, user.getPasswordHash())) {

                    // Only allow driver login through this endpoint
                    if (!"driver".equals(user.getRole())) {
                        return ResponseEntity.badRequest()
                                .body(createErrorResponse("Access denied. Driver credentials required."));
                    }

                    String token = authService.generateToken(
                            user.getId(),
                            user.getUsername(),
                            user.getRole(),
                            user.getBusId()
                    );

                    Map<String, Object> response = new HashMap<>();
                    response.put("accessToken", token);
                    response.put("role", user.getRole());
                    response.put("busId", user.getBusId());
                    response.put("username", user.getUsername());
                    response.put("success", true);

                    return ResponseEntity.ok(response);
                }
            }

            return ResponseEntity.badRequest()
                    .body(createErrorResponse("Invalid username or password"));

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(createErrorResponse("Login failed. Please try again."));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            // Extract token from Authorization header
            String token = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            }

            // Validate token if provided
            if (token != null) {
                String username = authService.getUsernameFromToken(token);
                if (username != null) {
                    // Token is valid, proceed with logout
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "Logged out successfully");
                    response.put("success", true);
                    return ResponseEntity.ok(response);
                }
            }

            // Even if token is invalid, return success for logout
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Logged out successfully");
            response.put("success", true);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Even if there's an error, return success for logout
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Logged out successfully");
            response.put("success", true);
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body(createErrorResponse("Invalid authorization header"));
            }

            String token = authHeader.substring(7);
            String username = authService.getUsernameFromToken(token);
            String role = authService.getRoleFromToken(token);
            String busId = authService.getBusIdFromToken(token);

            if (username != null && role != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("valid", true);
                response.put("username", username);
                response.put("role", role);
                response.put("busId", busId);
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.badRequest().body(createErrorResponse("Invalid or expired token"));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(createErrorResponse("Token validation failed"));
        }
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("error", message);
        error.put("success", false);
        return error;
    }
}