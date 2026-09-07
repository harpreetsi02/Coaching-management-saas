package com.backend.coaching_saas.controller;

import com.backend.coaching_saas.dto.request.StudentRequest;
import com.backend.coaching_saas.dto.response.CourseResponse;
import com.backend.coaching_saas.dto.response.PageResponse;
import com.backend.coaching_saas.dto.response.StudentResponse;
import com.backend.coaching_saas.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<StudentResponse> createStudent(
            @Valid @RequestBody StudentRequest request
            ) {
        StudentResponse response = studentService.createStudent(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id){
        StudentResponse response = studentService.getStudentById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<StudentResponse>> getAllStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            Pageable pageable
    ) {
        Page<StudentResponse> page =
                studentService.getAllStudents(name, email, age, minAge, maxAge, pageable);

        return ResponseEntity.ok(
                new PageResponse<>(page)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest request
    ) {
        StudentResponse response = studentService.updateStudent(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id){

        studentService.deleteStudent(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<StudentResponse> enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId
    ) {
        StudentResponse response =
                studentService.enrollStudent(studentId, courseId);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<StudentResponse> unenrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId
    ) {
        StudentResponse response =
                studentService.unenrollStudent(studentId, courseId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<List<CourseResponse>> getEnrolledCourses(
            @PathVariable Long studentId
    ) {
        List<CourseResponse> courses =
                studentService.getEnrolledCourses(studentId);

        return ResponseEntity.ok(courses);
    }
}
