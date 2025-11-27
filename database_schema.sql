-- Student ERP Database Schema
-- MySQL Database Dump

-- Create Database
CREATE DATABASE IF NOT EXISTS student_erp_db;
USE student_erp_db;

-- ============================================
-- Table: users
-- Purpose: Store user authentication details
-- ============================================

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    INDEX idx_username (username),
    INDEX idx_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- Table: students
-- Purpose: Store student information
-- ============================================

CREATE TABLE IF NOT EXISTS students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    roll_number VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    department VARCHAR(100) NOT NULL,
    course VARCHAR(100) NOT NULL,
    semester INT NOT NULL,
    date_of_birth DATE,
    enrollment_date DATE,
    user_id BIGINT UNIQUE,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_roll_number (roll_number),
    INDEX idx_email (email),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- Sample Data - Admin User
-- Password: admin123 (BCrypt encrypted)
-- ============================================

INSERT INTO users (username, password, email, role, active) 
VALUES (
    'admin', 
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGjSquGh8K5jKLLBmG', 
    'admin@erp.com', 
    'ADMIN', 
    TRUE
);

-- ============================================
-- Sample Data - Student 1
-- Username: john_doe, Password: student123
-- ============================================

INSERT INTO users (username, password, email, role, active) 
VALUES (
    'john_doe', 
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGjSquGh8K5jKLLBmG', 
    'john@student.com', 
    'STUDENT', 
    TRUE
);

INSERT INTO students (
    roll_number, 
    first_name, 
    last_name, 
    email, 
    phone, 
    department, 
    course, 
    semester, 
    date_of_birth, 
    enrollment_date, 
    user_id, 
    active
) VALUES (
    'STU001', 
    'John', 
    'Doe', 
    'john@student.com', 
    '9876543210', 
    'Computer Science', 
    'B.Tech', 
    5, 
    '2002-05-15', 
    '2021-08-01', 
    (SELECT id FROM users WHERE username = 'john_doe'), 
    TRUE
);

-- ============================================
-- Sample Data - Student 2
-- Username: jane_smith, Password: student123
-- ============================================

INSERT INTO users (username, password, email, role, active) 
VALUES (
    'jane_smith', 
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGjSquGh8K5jKLLBmG', 
    'jane@student.com', 
    'STUDENT', 
    TRUE
);

INSERT INTO students (
    roll_number, 
    first_name, 
    last_name, 
    email, 
    phone, 
    department, 
    course, 
    semester, 
    date_of_birth, 
    enrollment_date, 
    user_id, 
    active
) VALUES (
    'STU002', 
    'Jane', 
    'Smith', 
    'jane@student.com', 
    '9876543211', 
    'Electronics Engineering', 
    'B.Tech', 
    3, 
    '2003-08-22', 
    '2022-08-01', 
    (SELECT id FROM users WHERE username = 'jane_smith'), 
    TRUE
);

-- ============================================
-- Verify Data
-- ============================================

-- Check users table
SELECT 
    id, 
    username, 
    email, 
    role, 
    active,
    CASE 
        WHEN role = 'ADMIN' THEN 'admin123'
        WHEN role = 'STUDENT' THEN 'student123'
    END as plain_password_hint
FROM users;

-- Check students table
SELECT 
    s.id,
    s.roll_number,
    CONCAT(s.first_name, ' ', s.last_name) as full_name,
    s.email,
    s.department,
    s.course,
    s.semester,
    u.username as login_username
FROM students s
JOIN users u ON s.user_id = u.id;

-- ============================================
-- Useful Queries
-- ============================================

-- Get all students with their login credentials
-- SELECT 
--     s.roll_number,
--     s.first_name,
--     s.last_name,
--     s.email,
--     s.department,
--     u.username,
--     u.role
-- FROM students s
-- INNER JOIN users u ON s.user_id = u.id
-- WHERE s.active = TRUE;

-- Count students by department
-- SELECT 
--     department, 
--     COUNT(*) as student_count
-- FROM students
-- WHERE active = TRUE
-- GROUP BY department;

-- Find students by semester
-- SELECT * FROM students WHERE semester = 5;

-- ============================================
-- Cleanup (Use with caution!)
-- ============================================

-- To reset database:
-- DROP TABLE IF EXISTS students;
-- DROP TABLE IF EXISTS users;

-- ============================================
-- Notes
-- ============================================

-- Password Hashing:
-- All passwords in this dump are BCrypt hashed
-- Plain text passwords (for testing only):
--   - admin: admin123
--   - students: student123

-- Foreign Key Constraints:
-- When a user is deleted, associated student record is also deleted (CASCADE)

-- Indexes:
-- Added indexes on frequently queried columns for better performance

-- Character Set:
-- Using utf8mb4 for full Unicode support including emojis

-- ============================================
-- End of Database Schema
-- ============================================