package com.manage.library.response;

import org.springframework.http.HttpStatus;

public class ApiResponse {
    private int statusCode;
    private String message;
    private Object data;
    private String error;

    public ApiResponse(HttpStatus status, String message, Object data) {
        this.statusCode = status.value();
        this.message = message;
        this.data = data;
    }

    public ApiResponse(HttpStatus status, String message, String error) {
        this.statusCode = status.value();
        this.message = message;
        this.error = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}
