package com.example.backendweb.DTO;

// ErrorResponse.java (DTO for error responses)
public class ErrorResponseDTO {
    private String message; // More descriptive error message

    public ErrorResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
