package com.backend.coaching_saas.service;

import com.backend.coaching_saas.dto.requestDTO.CourseRequest;
import com.backend.coaching_saas.dto.responseDTO.CourseResponse;
import com.backend.coaching_saas.dto.responseDTO.PageResponse;
import com.backend.coaching_saas.entity.Course;
import com.backend.coaching_saas.exception.CourseHasStudentException;
import com.backend.coaching_saas.exception.CourseNotFoundException;
import com.backend.coaching_saas.exception.StudentNotFoundException;
import com.backend.coaching_saas.mapper.CourseMapper;
import com.backend.coaching_saas.repository.CourseRepository;
import com.backend.coaching_saas.specification.CourseSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public PageResponse<CourseResponse> getAllCourse(Pageable pageable){
        Page<Course> courses = courseRepository.findAll(pageable);

        Page<CourseResponse> coursePage = courses.map(CourseMapper::toResponse);

        return new PageResponse<>(coursePage);
    }

    @Transactional(readOnly = true)
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

        if (!course.getStudents().isEmpty()) {
            throw new CourseHasStudentException("Cannot delete course because student are enrolled in it!");
        }

        courseRepository.deleteById(id);

        return "Course deleted successfully!";
    }

    @Transactional(readOnly = true)
    public PageResponse<CourseResponse> searchCoursesWithSpecification(
            String name,
            Double price,
            Pageable pageable
    ) {
        Specification<Course> specification = null;

        if (name != null && !name.isBlank()){
            specification = CourseSpecification.hasName(name);
        }

        if (price != null){
            Specification<Course> priceSpecification = CourseSpecification.hasPrice(price);

            if (specification == null){
                specification = priceSpecification;
            } else {
                specification = specification.and(priceSpecification);
            }
        }

        Page<Course> courses;

        if (specification == null){
            courses = courseRepository.findAll(pageable);
        } else {
            courses = courseRepository.findAll(specification, pageable);
        }

        Page<CourseResponse> coursePage =
                courses.map(CourseMapper::toResponse);

        return new PageResponse<>(coursePage);
    }
}
