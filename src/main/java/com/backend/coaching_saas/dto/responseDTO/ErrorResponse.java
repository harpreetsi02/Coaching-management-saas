package com.backend.coaching_saas.dto.responseDTO;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private int status;
    private Map<String, String> errors;

    public ErrorResponse(LocalDateTime timestamp, String message, int status, Map<String, String> errors){
        this.timestamp = timestamp;
        this.message = message;
        this.status = status;
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
