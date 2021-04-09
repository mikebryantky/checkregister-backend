package com.mikebryant.checkregister.controller.api.error;

import java.util.ArrayList;
import java.util.List;

public class ApiError {
    private int status;
    private String message;
    private String developerMessage;
    private List<String> errors = new ArrayList<>();


    public ApiError(int status, String message, String developerMessage) {
        this.status = status;
        this.message = message;
        this.developerMessage = developerMessage;
    }

    public ApiError(int status, String message, String developerMessage, List<String> errors) {
        this.status = status;
        this.message = message;
        this.developerMessage = developerMessage;
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }
}
