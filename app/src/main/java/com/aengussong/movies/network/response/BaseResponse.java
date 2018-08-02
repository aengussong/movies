package com.aengussong.movies.network.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseResponse implements Serializable {
    @SerializedName("status_code")
    private String statusCode;
    @SerializedName("status_message")
    private String statusMessage;

    private boolean success;

    public BaseResponse(String statusCode, String statusMessage, boolean success) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.success = success;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public boolean isSuccess() {
        return success;
    }
}
