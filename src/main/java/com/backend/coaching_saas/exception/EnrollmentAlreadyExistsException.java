package com.backend.coaching_saas.exception;

public class EnrollmentAlreadyExistsException extends RuntimeException{

    public EnrollmentAlreadyExistsException(String message){
        super(message);
    }
}
