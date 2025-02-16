package com.example.backendweb.Entity.Exception;

public class CustomException extends RuntimeException {
    private String message;
    private int code;

    public CustomException(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
