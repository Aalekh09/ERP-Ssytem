package com.erp.studenterp.controller;

import com.erp.studenterp.dto.StudentResponse;
import com.erp.studenterp.service.PdfService;
import com.erp.studenterp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "http://localhost:3000")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private PdfService pdfService;

    // Get own profile
    @GetMapping("/profile")
    public ResponseEntity<StudentResponse> getProfile(Authentication authentication) {
        String username = authentication.getName();
        StudentResponse student = studentService.getStudentByUsername(username);
        return ResponseEntity.ok(student);
    }

    // Download digital ID card as PDF
    @GetMapping("/id-card")
    public ResponseEntity<ByteArrayResource> downloadIdCard(Authentication authentication) {
        String username = authentication.getName();
        StudentResponse student = studentService.getStudentByUsername(username);

        byte[] pdfBytes = pdfService.generateIdCard(student);
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=student_id_" + student.getRollNumber() + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfBytes.length)
                .body(resource);
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Student API is working!");
        return ResponseEntity.ok(response);
    }
}