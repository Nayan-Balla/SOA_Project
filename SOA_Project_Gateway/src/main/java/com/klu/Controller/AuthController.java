package com.klu.Controller;

import com.klu.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/test")
    public String test() {
        return "Auth controller working";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        String role = null;

        // ADMIN credentials
        if ("admin".equals(username) && "password123".equals(password)) {
            role = "ADMIN";
        }

        // USER credentials
        else if ("user".equals(username) && "user123".equals(password)) {
            role = "USER";
        }

        // Invalid credentials
        else {
            return ResponseEntity
                    .status(401)
                    .body(Map.of("message", "Invalid credentials"));
        }

        // Generate JWT with username + role
        String token = jwtUtil.generateToken(username, role);

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "username", username,
                        "role", role
                )
        );
    }
}