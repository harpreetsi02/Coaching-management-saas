package com.backend.coaching_saas.mapper;

import com.backend.coaching_saas.dto.request.BatchRequest;
import com.backend.coaching_saas.dto.response.BatchResponse;
import com.backend.coaching_saas.entity.Batch;
import com.backend.coaching_saas.entity.Course;
import com.backend.coaching_saas.entity.User;

public class BatchMapper {

    public static BatchResponse toResponse(Batch batch){
        BatchResponse response = new BatchResponse();

        response.setId(batch.getId());
        response.setName(batch.getName());

        response.setCourseId(batch.getCourse().getId());
        response.setCourseName(batch.getCourse().getName());

        response.setTeacherId(batch.getTeacher().getId());
        response.setTeacherName(batch.getTeacher().getName());

        response.setStartDate(batch.getStartDate());
        response.setEndDate(batch.getEndDate());

        response.setStartTime(batch.getStartTime());
        response.setEndTime(batch.getEndTime());

        response.setCapacity(batch.getCapacity());

        return response;
    }

    public static Batch toEntity(
            BatchRequest request,
            Course course,
            User teacher
    ) {
        Batch batch = new Batch();

        batch.setName(request.getName());
        batch.setCourse(course);
        batch.setTeacher(teacher);
        batch.setStartDate(request.getStartDate());
        batch.setEndDate(request.getEndDate());
        batch.setStartTime(request.getStartTime());
        batch.setEndTime(request.getEndTime());
        batch.setCapacity(request.getCapacity());

        return batch;
    }
}
