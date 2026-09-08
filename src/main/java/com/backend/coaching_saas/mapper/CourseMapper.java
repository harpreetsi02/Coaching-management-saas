package com.backend.coaching_saas.mapper;

import com.backend.coaching_saas.dto.request.CourseRequest;
import com.backend.coaching_saas.dto.response.CourseResponse;
import com.backend.coaching_saas.entity.Course;

public class CourseMapper {

    public static CourseResponse toResponse(Course course) {

        CourseResponse response = new CourseResponse();

        response.setId(course.getId());
        response.setName(course.getName());
        response.setDescription(course.getDescription());
        response.setPrice(course.getPrice());

        if (course.getTeacher() != null) {
            response.setTeacher(
                    UserMapper.toResponse(course.getTeacher())
            );
        }

        return response;
    }

    public static Course toEntity(CourseRequest request) {

        Course course = new Course();

        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());

        return course;
    }
}