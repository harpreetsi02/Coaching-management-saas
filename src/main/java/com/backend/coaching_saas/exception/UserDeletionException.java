package com.backend.coaching_saas.exception;

public class UserDeletionException extends RuntimeException{

    public UserDeletionException(String message){
        super(message);
    }
}
