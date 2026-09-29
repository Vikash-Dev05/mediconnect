package com.mediconnect.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.mediconnect.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.mediconnect.entity.User user = userRepository.findByEmailOrPhoneNumber(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email or phone: " + username));

        String role = user.getRole();
        if (role == null || role.isEmpty()) {
            role = "PATIENT";
        }
        role = role.replace("ROLE_", ""); // Normalize

        log.info("LOGIN-LOG: User={} | Final Role={}", user.getEmail(), role);

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(role) // roles() automatically prepends ROLE_
                .build();
    }
}