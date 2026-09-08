package com.backend.coaching_saas.exception;

public class BatchCapacityExceededException extends RuntimeException{

    public BatchCapacityExceededException(String message){
        super(message);
    }
}
