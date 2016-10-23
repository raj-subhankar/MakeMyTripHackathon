package com.yellowsoft.yellow.model.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by subhankar on 10/21/2016.
 */

public class Result {
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("success")
    private Boolean success;
    @SerializedName("message")
    private String message;
}
