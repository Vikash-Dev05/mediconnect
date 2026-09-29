package com.mediconnect.service;

import com.mediconnect.entity.User;
import com.mediconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder; // Agar password encrypt karna ho
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private PasswordEncoder passwordEncoder; // Security config hone par ise uncomment karein

    // Naya User save karne ke liye
    public void saveUser(User user) {
        // Agar aapke paas PasswordEncoder hai, toh ye line use karein:
        // user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        userRepository.save(user);
    }

    // Email ya Phone se user dhundne ke liye
    public User findUserByEmailOrPhone(String identifier) {
        return userRepository.findByEmailOrPhoneNumber(identifier, identifier).orElse(null);
    }

    // Email se user dhundne ke liye
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}