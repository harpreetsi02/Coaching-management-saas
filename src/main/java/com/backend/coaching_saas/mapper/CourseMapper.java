package com.backend.coaching_saas.mapper;

import com.backend.coaching_saas.dto.requestDTO.CourseRequest;
import com.backend.coaching_saas.dto.responseDTO.CourseResponse;
import com.backend.coaching_saas.entity.Course;

public class CourseMapper {

    public static Course toEntity(CourseRequest request){
        Course course = new Course();

        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());

        return course;
    }

    public static CourseResponse toResponse(Course course){
        return new CourseResponse(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getPrice()
        );
    }
}
