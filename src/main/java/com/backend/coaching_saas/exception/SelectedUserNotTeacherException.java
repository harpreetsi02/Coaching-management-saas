package com.backend.coaching_saas.exception;

public class SelectedUserNotTeacherException extends RuntimeException{

    public SelectedUserNotTeacherException(String message){
        super(message);
    }
}
