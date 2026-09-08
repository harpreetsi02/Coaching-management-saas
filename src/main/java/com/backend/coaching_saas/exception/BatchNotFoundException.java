package com.backend.coaching_saas.exception;

public class BatchNotFoundException extends RuntimeException{

    public BatchNotFoundException(String message){
        super(message);
    }
}
