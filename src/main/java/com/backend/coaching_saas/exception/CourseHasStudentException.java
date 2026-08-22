package com.backend.coaching_saas.exception;

import org.springframework.data.jpa.repository.JpaRepository;

public class CourseHasStudentException extends RuntimeException {
    public CourseHasStudentException(String message){
        super(message);
    }
}
