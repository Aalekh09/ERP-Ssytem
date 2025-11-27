package com.erp.studenterp.repository;

import com.erp.studenterp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username - used during login
    Optional<User> findByUsername(String username);

    // Check if username already exists - used during registration
    Boolean existsByUsername(String username);

    // Check if email already exists
    Boolean existsByEmail(String email);
}