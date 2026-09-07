package com.backend.coaching_saas.controller;

import com.backend.coaching_saas.dto.request.CourseRequest;
import com.backend.coaching_saas.dto.response.CourseResponse;
import com.backend.coaching_saas.dto.response.PageResponse;
import com.backend.coaching_saas.dto.response.StudentResponse;
import com.backend.coaching_saas.service.CourseService;
import com.backend.coaching_saas.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final StudentService studentService;

    public CourseController(
            CourseService courseService,
            StudentService studentService
    ){
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(
            @Valid @RequestBody CourseRequest request) {
        CourseResponse response = courseService.createCourse(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<CourseResponse> getCourseById(
            @PathVariable Long id) {
        CourseResponse response = courseService.getCourseById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<CourseResponse>> getAllCourses(
            Pageable pageable
    ) {
        Page<CourseResponse> page =
                courseService.getAllCourses(pageable);

        return ResponseEntity.ok(
                new PageResponse<>(page)
        );
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseRequest request) {
        CourseResponse response = courseService.updateCourse(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<StudentResponse>> getEnrollmentStudents(
            @PathVariable Long courseId
    ) {
        List<StudentResponse> students =
                studentService.getEnrolledStudents(courseId);

        return ResponseEntity.ok(students);
    }
}
