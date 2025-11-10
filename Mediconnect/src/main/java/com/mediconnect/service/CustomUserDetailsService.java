package com.mediconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.mediconnect.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Database mein user dhoondo
        com.mediconnect.entity.User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // 2. Agar mil gaya, toh Spring Security wala 'User' object banakar return karo
        // Note: Password ke aage "{noop}" laga rahe hain kyunki humne encryption nahi ki hai
        return User.builder()
                .username(user.getUsername())
                .password("{noop}" + user.getPassword())
                .roles(user.getRole())
                .build();
    }
}