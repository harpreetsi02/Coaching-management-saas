package com.backend.coaching_saas.controller;

import com.backend.coaching_saas.dto.requestDTO.CourseRequest;
import com.backend.coaching_saas.dto.responseDTO.CourseResponse;
import com.backend.coaching_saas.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CourseRequest request){
        CourseResponse response = courseService.createCourse(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourse(){
        List<CourseResponse> response = courseService.getAllCourse();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id){
        CourseResponse response = courseService.getCourseById(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable Long id, @RequestBody CourseRequest request){
        CourseResponse response = courseService.updateCourse(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id){
        String response = courseService.deleteCourse(id);

        return ResponseEntity.ok(response);
    }
}
