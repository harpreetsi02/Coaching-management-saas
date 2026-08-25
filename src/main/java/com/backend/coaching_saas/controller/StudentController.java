package com.backend.coaching_saas.controller;

import com.backend.coaching_saas.dto.requestDTO.StudentRequest;
import com.backend.coaching_saas.dto.responseDTO.PageResponse;
import com.backend.coaching_saas.dto.responseDTO.StudentResponse;
import com.backend.coaching_saas.entity.Student;
import com.backend.coaching_saas.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request){
        StudentResponse response = studentService.createStudent(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<StudentResponse>> getAllStudents(Pageable pageable){
        PageResponse<StudentResponse> response = studentService.getAllStudents(pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id){
        StudentResponse student = studentService.getStudentById(id);

        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentRequest request){
        StudentResponse updateStudent = studentService.updateStudent(id, request);

        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        String response = studentService.deleteStudent(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/entity/{id}")
    public Student getStudentEntityById(@PathVariable Long id){
        return studentService.getStudentEntityById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<StudentResponse>> searchStudentByName(
            @RequestParam String name,
            Pageable pageable
    ) {
        PageResponse<StudentResponse> response =
                studentService.searchStudentByName(name, pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/specification")
    public ResponseEntity<PageResponse<StudentResponse>> searchWithSpecification(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            Pageable pageable
    ) {
        PageResponse<StudentResponse> response =
                studentService.searchStudentsWithSpecification(name, age, pageable);

        return ResponseEntity.ok(response);
    }
}
