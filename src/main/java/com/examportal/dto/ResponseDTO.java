package com.examportal.dto;

import java.io.Serializable;

public class ResponseDTO<T> implements Serializable {
    private boolean isSuccess;
    private String message;
    private T data;


    public ResponseDTO() {
    }

    public ResponseDTO(boolean isSuccess, String message, T data) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
