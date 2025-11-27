package com.erp.studenterp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {

    private Long id;
    private String rollNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String department;
    private String course;
    private Integer semester;
    private LocalDate dateOfBirth;
    private LocalDate enrollmentDate;
    private String username;
    private Boolean active;
}