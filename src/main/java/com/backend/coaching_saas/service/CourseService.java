package com.backend.coaching_saas.service;

import com.backend.coaching_saas.dto.request.CourseRequest;
import com.backend.coaching_saas.dto.response.CourseResponse;
import com.backend.coaching_saas.entity.Course;
import com.backend.coaching_saas.entity.Role;
import com.backend.coaching_saas.entity.User;
import com.backend.coaching_saas.exception.CourseAccessDeniedException;
import com.backend.coaching_saas.exception.CourseNotFoundException;
import com.backend.coaching_saas.mapper.CourseMapper;
import com.backend.coaching_saas.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CurrentUserService currentUserService;

    public CourseService(
            CourseRepository courseRepository,
            CurrentUserService currentUserService
    ) {
        this.courseRepository = courseRepository;
        this.currentUserService = currentUserService;
    }

    @Transactional
    public CourseResponse createCourse(CourseRequest request){

        User user = currentUserService.getCurrentUser();

        Course course = CourseMapper.toEntity(request);

        course.setTeacher(user);

        Course savedCourse = courseRepository.save(course);

        return CourseMapper.toResponse(savedCourse);
    }

    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(
                        "Course not found!"
                ));

        return CourseMapper.toResponse(course);
    }

    @Transactional(readOnly = true)
    public Page<CourseResponse> getAllCourses(Pageable pageable){

        User currentUser = currentUserService.getCurrentUser();

        Page<Course> courses;

        if (currentUser.getRole() == Role.TEACHER){

            courses = courseRepository
                    .findByTeacherId(currentUser.getId(), pageable);
        } else {

            courses = courseRepository.findAll(pageable);
        }

        return courses.map(CourseMapper::toResponse);
    }

    @Transactional
    public CourseResponse updateCourse(Long id, CourseRequest request){

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found!"));

        User currentUser = currentUserService.getCurrentUser();

        if (!course.getTeacher().getId().equals(currentUser.getId())){
            throw new CourseAccessDeniedException(
                    "You can only update your own courses!"
            );
        }

        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());

        Course updateCourse = courseRepository.save(course);

        return CourseMapper.toResponse(updateCourse);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void deleteCourse(Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found"));

        courseRepository.deleteById(id);
    }
}
