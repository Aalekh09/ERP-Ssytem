package com.erp.studenterp.repository;

import com.erp.studenterp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Find student by roll number
    Optional<Student> findByRollNumber(String rollNumber);

    // Check if roll number exists
    Boolean existsByRollNumber(String rollNumber);

    // Find student by user ID (for profile viewing)
    Optional<Student> findByUserId(Long userId);

    // Check if email exists
    Boolean existsByEmail(String email);
}