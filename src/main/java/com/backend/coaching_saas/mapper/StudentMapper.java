package com.backend.coaching_saas.mapper;

import com.backend.coaching_saas.dto.request.StudentRequest;
import com.backend.coaching_saas.dto.response.StudentResponse;
import com.backend.coaching_saas.entity.Course;
import com.backend.coaching_saas.entity.Student;

import java.util.List;

public class StudentMapper {

    public static StudentResponse toResponse(Student student) {

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());
        response.setName(student.getName());
        response.setEmail(student.getEmail());
        response.setAge(student.getAge());

        List<Long> courseIds = student.getCourses()
                .stream()
                .map(Course::getId)
                .toList();

        response.setCourseIds(courseIds);

        return response;
    }

    public static Student toEntity(
            StudentRequest request,
            List<Course> courses
    ) {

        Student student = new Student();

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(request.getPassword());
        student.setAge(request.getAge());
        student.setCourses(courses);

        return student;
    }
}
