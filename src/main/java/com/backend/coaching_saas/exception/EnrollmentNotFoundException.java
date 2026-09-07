package com.backend.coaching_saas.exception;

public class EnrollmentNotFoundException extends RuntimeException{

    public EnrollmentNotFoundException(String message){
        super(message);
    }
}
