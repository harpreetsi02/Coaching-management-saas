package com.backend.coaching_saas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException exception
    ) {
            Map<String, String> errors = new HashMap<>();

            exception.getBindingResult()
                    .getFieldErrors()
                    .forEach(error ->
                            errors.put(error.getField(), error.getDefaultMessage())
                    );

            Map<String, Object> response = new HashMap<>();

            response.put("message", "Validation error");
            response.put("status", 400);
            response.put("errors", errors);

            return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleStudentNotFound(
            StudentNotFoundException exception
    ) {
        Map<String, Object> response = new HashMap<>();

        response.put("message", exception.getMessage());
        response.put("status", 404);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCourseNotFound(
            CourseNotFoundException exception
    ) {
        Map<String, Object> response = new HashMap<>();

        response.put("message", exception.getMessage());
        response.put("status", 404);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleEmailAlreadyException(
            EmailAlreadyExistsException exception
    ) {
        Map<String, Object> response = new HashMap<>();

        response.put("message", exception.getMessage());
        response.put("status", 409);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
