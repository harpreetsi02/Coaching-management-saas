package com.backend.coaching_saas.service;

import com.backend.coaching_saas.dto.request.CourseRequest;
import com.backend.coaching_saas.dto.response.CourseResponse;
import com.backend.coaching_saas.entity.Course;
import com.backend.coaching_saas.mapper.CourseMapper;
import com.backend.coaching_saas.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    @Transactional
    public CourseResponse createCourse(CourseRequest request){

        Course course = CourseMapper.toEntity(request);

        Course savedCourse = courseRepository.save(course);

        return CourseMapper.toResponse(savedCourse);
    }

    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Course not found!"
                ));

        return CourseMapper.toResponse(course);
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses(){

        return courseRepository.findAll()
                .stream()
                .map(CourseMapper::toResponse)
                .toList();
    }

    @Transactional
    public CourseResponse updateCourse(Long id, CourseRequest request){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found!"));

        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());

        Course updateCourse = courseRepository.save(course);

        return CourseMapper.toResponse(updateCourse);
    }

    @Transactional
    public void deleteCourse(Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        courseRepository.deleteById(id);
    }
}
