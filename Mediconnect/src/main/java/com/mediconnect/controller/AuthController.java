package com.mediconnect.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.mediconnect.entity.User;
import com.mediconnect.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // USER REGISTER API
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Error: Username already taken!";
        }
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("PATIENT");
        }
        // Password ke aage {noop} lagana zaroori hai agar plain text use kar rahe hain
        user.setPassword("{noop}" + user.getPassword());
        userRepository.save(user);
        return "User registered successfully!";
    }

    // NAYA API: Current User ka Role pata karne ke liye
    @GetMapping("/current-role")
    public String getCurrentUserRole() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getAuthorities().stream().findFirst().isPresent()) {
            return auth.getAuthorities().stream().findFirst().get().getAuthority();
        }
        return "UNKNOWN";
    }
}