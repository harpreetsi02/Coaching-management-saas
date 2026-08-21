package com.backend.coaching_saas.mapper;

import com.backend.coaching_saas.dto.requestDTO.CourseRequest;
import com.backend.coaching_saas.dto.responseDTO.CourseResponse;
import com.backend.coaching_saas.dto.responseDTO.StudentSummaryResponse;
import com.backend.coaching_saas.entity.Course;

import java.util.List;

public class CourseMapper {

    public static Course toEntity(CourseRequest request){
        Course course = new Course();

        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());

        return course;
    }

    public static CourseResponse toResponse(Course course){

        List<StudentSummaryResponse> students = course.getStudents()
                .stream()
                .map(student -> new StudentSummaryResponse(
                        student.getId(),
                        student.getName(),
                        student.getEmail()
                ))
                .toList();

        return new CourseResponse(
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getPrice(),
                students
        );
    }
}
