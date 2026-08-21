package com.backend.coaching_saas.mapper;

import com.backend.coaching_saas.dto.requestDTO.StudentRequest;
import com.backend.coaching_saas.dto.responseDTO.CourseSummaryResponse;
import com.backend.coaching_saas.dto.responseDTO.StudentResponse;
import com.backend.coaching_saas.entity.Student;

public class StudentMapper {

    public static Student toEntity(StudentRequest request){
        Student student = new Student();

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(request.getPassword());
        student.setAge(request.getAge());

        return student;
    }

    public static StudentResponse toResponse(Student student){

        CourseSummaryResponse courseResponse = new CourseSummaryResponse(
                student.getCourse().getId(),
                student.getCourse().getName()
        );

        return new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getAge(),
                courseResponse
        );
    }
}
