package com.erp.studenterp.service;

import com.erp.studenterp.dto.StudentRequest;
import com.erp.studenterp.dto.StudentResponse;
import com.erp.studenterp.entity.Student;
import com.erp.studenterp.entity.User;
import com.erp.studenterp.exception.BadRequestException;
import com.erp.studenterp.exception.ResourceNotFoundException;
import com.erp.studenterp.repository.StudentRepository;
import com.erp.studenterp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public StudentResponse createStudent(StudentRequest request) {
        // Validate unique fields
        if (studentRepository.existsByRollNumber(request.getRollNumber())) {
            throw new BadRequestException("Roll number already exists");
        }

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        // Create user account for student
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(User.Role.STUDENT);
        user.setActive(true);
        user = userRepository.save(user);

        // Create student
        Student student = new Student();
        student.setRollNumber(request.getRollNumber());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDepartment(request.getDepartment());
        student.setCourse(request.getCourse());
        student.setSemester(request.getSemester());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setEnrollmentDate(request.getEnrollmentDate() != null ?
                request.getEnrollmentDate() : LocalDate.now());
        student.setUser(user);
        student.setActive(true);

        student = studentRepository.save(student);

        return mapToResponse(student);
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public StudentResponse getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
        return mapToResponse(student);
    }

    public StudentResponse getStudentByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for user: " + username));

        return mapToResponse(student);
    }

    @Transactional
    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

        // Check if new roll number conflicts with existing (except current student)
        if (!student.getRollNumber().equals(request.getRollNumber()) &&
                studentRepository.existsByRollNumber(request.getRollNumber())) {
            throw new BadRequestException("Roll number already exists");
        }

        // Check if new email conflicts
        if (!student.getEmail().equals(request.getEmail()) &&
                studentRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }

        // Update student fields
        student.setRollNumber(request.getRollNumber());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDepartment(request.getDepartment());
        student.setCourse(request.getCourse());
        student.setSemester(request.getSemester());
        student.setDateOfBirth(request.getDateOfBirth());

        // Update user email if changed
        User user = student.getUser();
        user.setEmail(request.getEmail());
        userRepository.save(user);

        student = studentRepository.save(student);
        return mapToResponse(student);
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));

        // Delete associated user account
        if (student.getUser() != null) {
            userRepository.delete(student.getUser());
        }

        studentRepository.delete(student);
    }

    // Convert Entity to DTO
    private StudentResponse mapToResponse(Student student) {
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setRollNumber(student.getRollNumber());
        response.setFirstName(student.getFirstName());
        response.setLastName(student.getLastName());
        response.setEmail(student.getEmail());
        response.setPhone(student.getPhone());
        response.setDepartment(student.getDepartment());
        response.setCourse(student.getCourse());
        response.setSemester(student.getSemester());
        response.setDateOfBirth(student.getDateOfBirth());
        response.setEnrollmentDate(student.getEnrollmentDate());
        response.setUsername(student.getUser() != null ? student.getUser().getUsername() : null);
        response.setActive(student.getActive());
        return response;
    }
}