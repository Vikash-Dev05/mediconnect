package com.mediconnect.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mediconnect.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Login karte waqt hum username se user dhoondhenge
    Optional<User> findByUsername(String username);
}