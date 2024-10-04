package com.manage.library.response;

public enum ApiResponseEnum {
    SUCCESS("Success"),
    BAD_REQUEST("Invalid request"),
    NOT_FOUND("Resource not found"),
    CREATED("Resource created successfully"),
    UPDATED("Resource updated successfully"),
    DELETED("Resource deleted successfully"),
    INTERNAL_SERVER_ERROR("Internal server error");

    private final String message;

    ApiResponseEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}