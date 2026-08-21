package com.backend.coaching_saas.service;

import com.backend.coaching_saas.dto.requestDTO.CourseRequest;
import com.backend.coaching_saas.dto.responseDTO.CourseResponse;
import com.backend.coaching_saas.entity.Course;
import com.backend.coaching_saas.exception.CourseNotFoundException;
import com.backend.coaching_saas.exception.StudentNotFoundException;
import com.backend.coaching_saas.mapper.CourseMapper;
import com.backend.coaching_saas.repository.CourseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public CourseResponse createCourse(CourseRequest request){
        Course course = CourseMapper.toEntity(request);

        Course savedCourse = courseRepository.save(course);

        return CourseMapper.toResponse(savedCourse);
    }

    public List<CourseResponse> getAllCourse(){
        List<Course> courses = courseRepository.findAll();

        return courses.stream()
                .map(CourseMapper::toResponse)
                .toList();
    }

    public CourseResponse getCourseById(Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));

        return CourseMapper.toResponse(course);
    }

    public CourseResponse updateCourse(Long id, CourseRequest request){
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));



        existingCourse.setName(request.getName());
        existingCourse.setDescription(request.getDescription());
        existingCourse.setPrice(request.getPrice());

        Course updatedCourse = courseRepository.save(existingCourse);

        return CourseMapper.toResponse(updatedCourse);
    }

    public String deleteCourse(Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));

        courseRepository.deleteById(id);

        return "Course deleted successfully!";
    }
}
