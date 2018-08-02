package com.aengussong.movies.network.response;

public class ResponseResult<T> {
    private T data;

    private String errorMessage;

    public ResponseResult(T data) {
        this.data = data;
    }

    public ResponseResult(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccessful() {
        return errorMessage == null;
    }
}
