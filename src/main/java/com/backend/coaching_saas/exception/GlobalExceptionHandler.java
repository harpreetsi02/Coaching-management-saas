package com.backend.coaching_saas.exception;

import com.backend.coaching_saas.dto.responseDTO.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception
    ) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
                );

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "Validation error",
                HttpStatus.BAD_REQUEST.value(),
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStudentNotFoundException(
            StudentNotFoundException exception
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "Validation error",
                HttpStatus.NOT_FOUND.value(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(
            EmailAlreadyExistsException exception
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                HttpStatus.CONFLICT.value(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCourseNotFoundException(
            CourseNotFoundException exception
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "Validation error",
                HttpStatus.NOT_FOUND.value(),
                null
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(CourseHasStudentException.class)
    public ResponseEntity<ErrorResponse> handleCourseHasStudentException(
            CourseHasStudentException exception
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                "Validation fail",
                HttpStatus.CONFLICT.value(),
                Map.of("message", exception.getMessage())
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }
}
